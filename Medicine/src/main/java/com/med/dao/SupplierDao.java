package com.med.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.med.entity.Supplier;

/**
 * 供应商Dao类
 * @author Runtime
 * @date 2018/2/6
 * @version v1.0
 */
@Repository
public class SupplierDao extends BaseDao {
	
	/**
	 * 查找所有符合条件的记录
	 * @param condition 条件
	 * @return 记录集合
	 */
	@SuppressWarnings("unchecked")
	public List<Supplier> findAll(String condition) {
		// TODO 自动生成的方法存根
		return currentSession().createQuery("FROM Supplier " 
				+ (condition == null ? "" : condition)).list();
	}
	
	/**
	 * 查找供应商名称集合
	 * @return
	 */
	public List findAllName() {
		return currentSession()
				.createQuery("select id,name FROM Supplier")
				.list();
	}

	/**
	 * 根据Id查找单个记录
	 * @param id
	 * @return 单个记录
	 */
	public Supplier findOne(int id) {
		// TODO 自动生成的方法存根
		return currentSession().get(Supplier.class, id);
	}
	
	/**
	 * 插入新记录
	 * @param supplier 供应商对象
	 * @return 成功插入后的id值
	 */
	public Integer insert(Supplier supplier) {
		return (Integer) currentSession().save(supplier);
	}
	
	/**
	 * 更新记录
	 * @param supplier 供应商对象
	 */
	public void update(Supplier supplier) {
		// 取出session中的对象进行更新
		Supplier tmp = findOne(supplier.getId());
		
		tmp.setName(supplier.getName());
		tmp.setContacts(supplier.getContacts());
		tmp.setContactPhone(supplier.getContactPhone());
		tmp.setCity(supplier.getCity());
		
		currentSession().update(tmp);
	}
	
	/**
	 * 删除一条记录
	 * @param id 
	 */
	public void delete(int id) {
		Supplier tmp = findOne(id);
		
		currentSession().delete(tmp);
	}
	
	/**
	 * 查找符合条件的分页记录
	 * @param condition 条件
	 * @param startIndex 该页的起始索引
	 * @param pageSize 一页的记录数
	 * @return 记录集合
	 */
	@SuppressWarnings("unchecked")
	public List<Supplier> findByPaging(String condition, 
			int startIndex, int recordNum) {
		return currentSession().createQuery("FROM Supplier " + 
				(condition == null ? "" : condition))
				.setFirstResult(startIndex)
				.setMaxResults(recordNum).list();
	}
	
	public Integer findIdByName(String name) {
		Object obj = currentSession().createQuery(
				"select id FROM Supplier WHERE name = :name")
				.setParameter("name", name).uniqueResult();
		if (obj != null) 
			return Integer.valueOf(obj.toString());
		
		return 0;
	}
}
