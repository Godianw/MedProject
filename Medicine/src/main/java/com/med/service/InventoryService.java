package com.med.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.med.dao.InventoryDao;
import com.med.dao.InventoryHistoryDao;
import com.med.dao.MedicineDao;
import com.med.entity.InventoryHistory;
import com.med.entity.Medicine;

@Service
@Transactional
public class InventoryService {

	@Autowired
	InventoryDao inventoryDao;
	
	@Autowired
	MedicineDao medicineDao;
	
	@Autowired
	InventoryHistoryDao inventoryHistoryDao;
	
	/**
	 * 从list中提取出mapList
	 * @param mapList
	 * @return 结果集合
	 */
	private List<Map<String, Object>> getInventoryList(List list) {
		
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		Iterator<?> iterator = list.iterator();
		
		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("id", Integer.valueOf(obj[0].toString()));
			resultMap.put("name", obj[1].toString());
			resultMap.put("quantity", Integer.valueOf(obj[2].toString()));
			resultMap.put("warningQuantity", 
					Integer.valueOf(obj[3].toString()));
			resultList.add(resultMap);
		}
		
		return resultList;
	}
	
	/**
	 * 查找所有符合条件的库存记录
	 * @param condition 条件
	 * @return 库存记录集合
	 */
	public List<Map<String, Object>> findInventorys(String condition) {
		
		return getInventoryList(inventoryDao.findInventorys(condition));
		
	}
	
	/**
	 * 根据起始索引和记录数查找符合条件的库存记录
	 * @param condition 条件
	 * @param startIndex 起始索引
	 * @param recordNum 记录数
	 * @return 库存记录集合
	 */
	public List<Map<String, Object>> findInventorysByPaging(String condition,
			int startIndex, int recordNum) {
		
		return getInventoryList(inventoryDao.findInventorys(
				condition, startIndex, recordNum));
	}
	
	/**
	 * 根据id查找对应的库存记录
	 * @param id 药品编号
	 * @return 库存记录
	 */
	public Map findOne(Integer id) {
		
		if (id == null) 
			return null;
		else 
			return inventoryDao.findOne(id);
	}
	
	/**
	 * 更新库存
	 * @param id 药品编号
	 * @param quantity 库存变化数
	 * @param type 变化类型（1=入库，2=出库）
	 */
	public boolean updateQuantity(Integer id, Integer quantity, Integer type) {
		
		if (id == null || quantity == null 
				|| (type != 0 && type != 1))

			return false;
		
		/* 计算库存变化量 */
		int rate = (type == 0 ? 1 : -1);
		int addQuantity = quantity.intValue() * rate;
		
		/* 添加库存操作历史记录 */
		InventoryHistory inventoryHistory = new InventoryHistory();
		Medicine medicine = medicineDao.findOne(id);
		if (medicine == null)
			return false;
		
		inventoryHistory.setMedName(medicine.getName());
		inventoryHistory.setQuantity(quantity);
		inventoryHistory.setTime(
				new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
				.format(System.currentTimeMillis()));
		inventoryHistory.setOptype((type == 0  ? false : true));
		
		return inventoryDao.updateQuantity(id, addQuantity) 
				&& (inventoryHistoryDao.insert(inventoryHistory) != null);
	}
	
	/**
	 * 更新库存预警值
	 * @param id 药品编号
	 * @param warningQuantity 库存预警值
	 * @return 修改结果（true=成功，false=失败）
	 */
	public boolean updateWarningQuantity(Integer id, Integer warningQuantity) {
		
		if (id == null || warningQuantity == null 
				|| warningQuantity.intValue() < 0)
			return false;
		
		inventoryDao.updateWarningQuantity(id, warningQuantity);
		
		return true;
	}
	
	/**
	 * 根据id查找库存
	 * @param id
	 * @return
	 */
	public Integer findQuantity(Integer id) {
		
		if (id == null)
			return null;
		else
			return inventoryDao.findQuantity(id);
	}
}
