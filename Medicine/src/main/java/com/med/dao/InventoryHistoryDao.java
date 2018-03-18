package com.med.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.med.entity.InventoryHistory;

@Repository
public class InventoryHistoryDao extends BaseDao {

	/**
	 * 查找所有符合条件的记录
	 * @param condition 条件
	 * @return 记录集合
	 */
	@SuppressWarnings("unchecked")
	public List<InventoryHistory> findAll(String condition) {
		return currentSession().createQuery("FROM InventoryHistory "
				+ (condition == null ? "" : condition)).list();
	}
	
	/**
	 * 查找符合条件的分页记录
	 * @param condition 条件
	 * @param startIndex 该页的起始索引
	 * @param pageSize 一页的记录数
	 * @return 记录集合
	 */
	@SuppressWarnings("unchecked")
	public List<InventoryHistory> findAllByPaging(String condition,
			int startIndex, int recordNum) {
		return currentSession().createQuery("FROM InventoryHistory "
				+ (condition == null ? "" : condition))
				.setFirstResult(startIndex)
				.setMaxResults(startIndex + recordNum).list();
	}
	
	
	/**
	 * 插入一条记录
	 * @param inventoryHistory
	 * @return
	 */
	public Integer insert(InventoryHistory inventoryHistory) {
		return (Integer) currentSession().save(inventoryHistory);
	}
}
