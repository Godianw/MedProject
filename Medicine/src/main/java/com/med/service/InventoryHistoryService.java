package com.med.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;







import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.med.dao.InventoryHistoryDao;
import com.med.entity.InventoryHistory;

@Service
@Transactional
public class InventoryHistoryService {

	@Autowired
	InventoryHistoryDao inventoryHistoryDao;
	
	/**
	 * 查找所有符合条件的库存操作记录
	 * @param condition 条件
	 * @return 操作记录集合
	 */
	public List<InventoryHistory> findInventoryHistories(String condition) {
		return inventoryHistoryDao.findAll(condition);
	}
	
	/**
	 * 根据起始索引和记录数查找符合条件的药品记录
	 * @param condition 条件
	 * @param startIndex 起始索引
	 * @param recordNum 记录数
	 * @return 药品集合
	 */
	public List<InventoryHistory> findInventoryHistoriesByPaging(
			String condition, int startIndex, int recordNum) {
		return inventoryHistoryDao.findAllByPaging(condition, startIndex, recordNum);
	}
	
	/**
	 * 获取限制时间内的数据
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List findWithinLimitDay(
			int limitDay, boolean optype, boolean type) {
		String limitDate = LocalDate.now().minusDays(limitDay)
				.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		String selectType = type ? "COUNT(*)" : "SUM(quantity)";
		String hql = new StringBuilder("SELECT medName, ")
			.append(selectType)
			.append(" FROM InventoryHistory WHERE time >= '")
			.append(limitDate).append("' AND optype = ")
			.append(optype).append(" GROUP BY medName ORDER BY ")
			.append(selectType).toString();
		return fillEmptyData(
				inventoryHistoryDao.excuteHqlByPaging(hql, 0, 5));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List fillEmptyData(List list) {
		if (list.size() < 5) {
			List<String> nameList = new ArrayList<String>();
			Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				nameList.add(obj[0].toString());
			}
			
			String hql = new StringBuilder("SELECT m.name")
				.append(" FROM Medicine m")
				.append(nameList.size() == 0 ? "" : 
					" WHERE m.name NOT IN (:names)").toString();
			
			int length = 5 - list.size();
			Query query = inventoryHistoryDao.excuteHqlByParamAndPaging(
					hql, 0, length);
			
			List emptyList = 
					nameList.size() == 0 ? query.list() :
					query.setParameterList("names", nameList).list();
			
			for (int i = 0; i < length; i ++) {
				Object[] emptyData = new Object[2];
				emptyData[0] = emptyList.get(i).toString();
				emptyData[1] = new Integer(0);
				list.add(0, emptyData);
			}
		}
		
		return list;
	}
	
	@SuppressWarnings("rawtypes")
	public List<List> getDataList() {
		List<List> dataList = new ArrayList<List>();
		List<InventoryHistory> inventoryHistoriesList = 
				findInventoryHistories("ORDER BY id DESC");
		// 将操作历史记录装入集合数组中
		for (InventoryHistory inventoryHistory : inventoryHistoriesList) {
			List<Object> singleData = new ArrayList<Object>();
			singleData.add(inventoryHistory.getId());
			singleData.add(inventoryHistory.getMedName());
			singleData.add(inventoryHistory.getQuantity());
			singleData.add(inventoryHistory.getTime());
			singleData.add(
					(inventoryHistory.getOptype() ? "出库" : "入库"));
			dataList.add(singleData);
		}
		
		return dataList;
	}
}
