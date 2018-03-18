package com.med.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.med.entity.Medicine;

/**
 * 药品Dao类
 * @author Runtime
 * @date 2018/2/6
 * @version v1.0
 */
@Repository
public class MedicineDao extends BaseDao {

	/**
	 * 查找所有符合条件的记录
	 * @param condition 条件
	 * @return 记录集合
	 */
	@SuppressWarnings("unchecked")
	public List<Medicine> finaAll(String condition) {
		return currentSession().createQuery("FROM Medicine "
				+ (condition == null ? "" : condition)).list();
	}
	
	/**
	 * 查找药品名称集合
	 * @return
	 */
	public List findAllName() {
		return currentSession()
				.createQuery("SELECT id,name FROM Medicine")
				.list();
	}
	
	/**
	 * 根据Id查找单个记录
	 * @param id
	 * @return 单个记录
	 */
	public Medicine findOne(int id) {
		return currentSession().get(Medicine.class, id);
	}
	
	/**
	 * 插入新记录
	 * @param medicine 药品对象
	 * @return 成功插入后的id值
	 */
	public Integer insert(Medicine medicine) {
		return (Integer) currentSession().save(medicine);
	}
	
	/**
	 * 更新记录
	 * @param medicine 药品对象
	 */
	public void update(Medicine medicine) {
		// 取出session中的对象进行更新
		Medicine tmp = findOne(medicine.getId());
		
		tmp.setName(medicine.getName());
		tmp.setSupplier(medicine.getSupplier());
		tmp.setBatchNum(medicine.getBatchNum());
		tmp.setProductDate(medicine.getProductDate());
		tmp.setStorageTime(medicine.getStorageTime());
		tmp.setPurchasePrice(medicine.getPurchasePrice());
		tmp.setSinglePrice(medicine.getSinglePrice());
		
		currentSession().update(tmp);
	}
	
	/**
	 * 删除一条记录
	 * @param id 
	 */
	public void delete(int id) {
		Medicine medicine = findOne(id);
		
		currentSession().delete(medicine);
	}
	
	/**
	 * 查找符合条件的分页记录
	 * @param condition 条件
	 * @param startIndex 该页的起始索引
	 * @param pageSize 一页的记录数
	 * @return 记录集合
	 */
	@SuppressWarnings("unchecked")
	public List<Medicine> findByPaging(String condition, 
			int startIndex, int recordNum) {
		return currentSession().createQuery("FROM Medicine " + 
				(condition == null ? "" : condition))
				.setFirstResult(startIndex)
				.setMaxResults(startIndex + recordNum).list();
	}
}
