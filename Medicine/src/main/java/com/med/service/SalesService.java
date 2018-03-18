package com.med.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.med.dao.SalesDao;
import com.med.entity.Sales;

@Service
@Transactional
public class SalesService {

	@Autowired
	SalesDao salesDao;
	
	/**
	 * 查找所有符合条件的销售记录
	 * @param condition 条件
	 * @return 销售记录集合
	 */
	public List<Sales> findSales(String condition) {
		System.out.println(condition);
		return salesDao.finaAll(condition);
	}
	
	/**
	 * 根据起始索引和记录数查找符合条件的销售记录
	 * @param condition 条件
	 * @param startIndex 起始索引
	 * @param recordNum 记录数
	 * @return 销售记录集合
	 */
	public List<Sales> findSalesByPaging(String condition,
			int startIndex, int recordNum) {
		System.out.println(condition);
		return salesDao.findByPaging(condition, startIndex, recordNum);
	}
	
	/**
	 * 根据id查找对应的销售记录
	 * @param id
	 * @return 销售记录
	 */
	public Sales findOne(Integer id) {
		if (id == null) 
			return null;
		else
			return salesDao.findOne(id);
	}
	
	/**
	 * 更新销售记录信息
	 * @param supplier 销售对象
	 */
	public void save(Sales sales) {
		
		if (sales.getId() == null) {
			salesDao.insert(sales);
		} else {
			salesDao.update(sales);
		}
	}
	
	/**
	 * 删除销售记录
	 * @param id
	 * @return
	 */
	public boolean delete(Integer id) {
		
		if (id != null) {
			salesDao.delete(id);
			return true;
		}
		
		return false;
	}
	
	/**
	 * 查找某一年各月销售额
	 * @param year
	 * @return
	 */
	public List<Float> findMonthSalesMoneyByYear(int year) {
		List<Float> resultList 
			= new ArrayList<Float>();
		Iterator<?> iterator 
			= salesDao.findMonthSalesMoneyByYear(year).iterator();
		int month = 1;
		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			
			/* 将集合中不包含的月份 */
			while (Integer.valueOf(obj[0].toString()) != month++) {
				resultList.add(Float.valueOf(0));
			}
			
			// 装入各个月份的销售额
			resultList.add(Float.valueOf(obj[1].toString()));
		}
		
		// 将剩余月份数据封装
		while (month++ != 13) 
			resultList.add(Float.valueOf(0));
		
		return resultList;
	}
	
	/**
	 * 查找各年的营销额
	 * @return
	 */
	public List<Float> findYearSalesMoney() {
		List<Float> resultList 
			= new ArrayList<Float>();
		Iterator<?> iterator 
			= salesDao.findYearSalesMoney().iterator();
		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			resultList.add(Float.valueOf(obj[1].toString()));
		}
		
		return resultList;
	}
	
	/**
	 * 获取所有年份
	 * @return
	 */
	public List<String> findYears() {
		return salesDao.findYears();
	}
	
	/**
	 * 获取月销售状况数据
	 * @return
	 */
	public List getMonthDataSource() {
		List<Object> dataList = new ArrayList<Object>();
		
		for (String year : findYears()) {
			int index = 0;
			List<Float> monthData = findMonthSalesMoneyByYear(
					Integer.valueOf(year));
			dataList.add(monthData);
		}
		
		return dataList;
	}
	
	/**
	 * 获取年销售状态数据
	 * @return
	 */
	public List getYearDataSource() {
		
		// 数据项
		return findYearSalesMoney();
	}
}
