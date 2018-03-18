package com.med.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.transaction.Transactional;

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
	public List<InventoryHistory> findWithinLimitDay(
			int limitDay, boolean optype) {
		String limitDate = LocalDate.now().minusDays(limitDay)
				.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		String condition = new StringBuilder("WHERE time >= '")
			.append(limitDate).append("' AND optype = ")
			.append(optype).toString();
		return inventoryHistoryDao.findAll(condition);
	}
}
