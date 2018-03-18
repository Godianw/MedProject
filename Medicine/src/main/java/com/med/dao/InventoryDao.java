package com.med.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.med.entity.Medicine;

@Repository
public class InventoryDao extends BaseDao {

	/**
	 * 查找库存信息记录
	 * @param condition
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List findInventorys(String condition) {
		
		return currentSession().createQuery(
				"SELECT id,name,quantity,warningQuantity FROM Medicine "
				+ (condition == null ? "" : condition))
				.list();
	}
	
	/**
	 * 分页查找库存信息记录
	 * @param condition
	 * @param startIndex
	 * @param recordNum
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List findInventorys(String condition, 
			int startIndex, int recordNum) {
		return currentSession().createQuery(
				"SELECT id,name,quantity,warningQuantity FROM Medicine "
				+ (condition == null ? "" : condition))
				.setFirstResult(startIndex)
				.setMaxResults(startIndex + recordNum).list();
	}
	
	/**
	 * 获取单条记录
	 * @param id
	 * @return
	 */
	public Map<String, Object> findOne(int id) {
		Object[] obj = (Object[]) currentSession().createQuery(
				"SELECT id,name,quantity,warningQuantity FROM Medicine WHERE id = :id")
				.setParameter("id", id).uniqueResult();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", obj[0]);
		map.put("name", obj[1]);
		map.put("quantity", obj[2]);
		map.put("warningQuantity", obj[3]);
		
		return map;
	}
	
	/**
	 * 更新库存数量
	 * @param id
	 * @param quantity
	 * @return 修改结果（true=成功，false=失败）
	 */
	public boolean updateQuantity(int id, int addQuantity) {
		
		Medicine medicine = currentSession().get(Medicine.class, id);
		int preQuantity = medicine.getQuantity();
		int newQuantity = preQuantity + addQuantity;
		
		if (newQuantity < 0)
			return false;
		
		medicine.setQuantity(newQuantity);
		currentSession().update(medicine);
		return true;
	}
	
	/**
	 * 更新库存预警值
	 * @param id
	 * @param warningQuantity
	 */
	public void updateWarningQuantity(int id, int warningQuantity) {
		
		Medicine medicine = currentSession().get(Medicine.class, id);
		medicine.setWarningQuantity(warningQuantity);
		currentSession().update(medicine);
	}
	
	/**
	 * 根据id获取库存
	 * @param id
	 * @return
	 */
	public Integer findQuantity(int id) {
		
		Medicine medicine = currentSession().get(Medicine.class, id);
		return medicine.getQuantity();
	}
}
