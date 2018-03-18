package com.med.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.med.entity.Sales;

/**
 * 销售Dao类
 * @author Runtime
 * @date 2018/2/6
 * @version v1.0
 */
@Repository
public class SalesDao extends BaseDao {

	/**
	 * 查找所有符合条件的记录
	 * @param condition 条件
	 * @return 记录集合
	 */
	@SuppressWarnings("unchecked")
	public List<Sales> finaAll(String condition) {
		return currentSession().createQuery("FROM Sales "
				+ (condition == null ? "" : condition)).list();
	}
	
	/**
	 * 根据Id查找单个记录
	 * @param id
	 * @return 单个记录
	 */
	public Sales findOne(int id) {
		return currentSession().get(Sales.class, id);
	}
	
	/**
	 * 插入新记录
	 * @param sales 销售对象
	 * @return 成功插入后的id值
	 */
	public Integer insert(Sales sales) {
		return (Integer) currentSession().save(sales);
	}
	
	/**
	 * 更新记录
	 * @param sales 销售对象
	 */
	public void update(Sales sales) {
		// 取出session中的对象进行更新
		Sales tmp = findOne(sales.getId());
		
		tmp.setStaff(sales.getStaff());
		tmp.setMedicine(sales.getMedicine());
		tmp.setCount(sales.getCount());
		tmp.setDatetime(sales.getDatetime());
		tmp.setMoney(sales.getMoney());
		
		currentSession().update(tmp);
	}
	
	/**
	 * 删除一条记录
	 * @param id 
	 */
	public void delete(int id) {
		Sales sales = findOne(id);
		
		currentSession().delete(sales);
	}
	
	/**
	 * 查找符合条件的分页记录
	 * @param condition 条件
	 * @param startIndex 该页的起始索引
	 * @param pageSize 一页的记录数
	 * @return 记录集合
	 */
	@SuppressWarnings("unchecked")
	public List<Sales> findByPaging(String condition, 
			int startIndex, int recordNum) {
		return currentSession().createQuery("FROM Sales " + 
				(condition == null ? "" : condition))
				.setFirstResult(startIndex)
				.setMaxResults(startIndex + recordNum).list();
	}
	
	/**
	 * 查找某一年各月销售额
	 * @param year
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List findMonthSalesMoneyByYear(int year) {
		return currentSession().createQuery("SELECT substring(datetime, 6, 2)"
				+ " , SUM(money) FROM Sales WHERE datetime LIKE "
				+ "'" + year +"/%'"
				+ " GROUP BY substring(datetime, 6, 2)"
				+ " ORDER BY substring(datetime, 6, 2) ASC").list();
	}
	
	/**
	 * 查找各年的营销额
	 * @return
	 */
	public List findYearSalesMoney() {
		return currentSession().createQuery("SELECT substring(datetime, 1, 4)"
				+ " , SUM(money) FROM Sales"
				+ " GROUP BY substring(datetime, 1, 4)"
				+ " ORDER BY substring(datetime, 1, 4) ASC").list();
	}
	
	/**
	 * 查找年份
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> findYears() {
		return currentSession().createQuery("SELECT substring(datetime, 1, 4)"
				+ " FROM Sales"
				+ " GROUP BY substring(datetime, 1, 4)"
				+ " ORDER BY substring(datetime, 1, 4) ASC").list();
	}
}
