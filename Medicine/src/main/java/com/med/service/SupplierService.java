package com.med.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.med.dao.SupplierDao;
import com.med.entity.Supplier;

/**
 * 供应商服务类
 * @author Runtime
 * @date 2018/2/7
 * @version v1.0
 */
@Service
@Transactional
public class SupplierService {

	@Autowired
	SupplierDao supplierDao;
	
	/**
	 * 查找所有符合条件的供应商
	 * @param condition 条件
	 * @return 供应商集合
	 */
	public List<Supplier> findSuppliers(String condition) {
		System.out.println(condition);
		return supplierDao.findAll(condition);
	}
	
	/**
	 * 根据起始索引和记录数查找符合条件的供应商记录
	 * @param condition 条件
	 * @param startIndex 起始索引
	 * @param recordNum 记录数
	 * @return 供应商集合
	 */
	public List<Supplier> findSuppliersByPaging(String condition, 
			int startIndex, int recordNum) {
		System.out.println(condition);
		return supplierDao.findByPaging(condition, startIndex, recordNum);
	}
	
	/**
	 * 根据id查找对应的供应商
	 * @param id
	 * @return 供应商
	 */
	public Supplier findOne(Integer id) {
		if (id == null)
			return null;
		return supplierDao.findOne(id);
	}
	
	/**
	 * 更新供应商信息
	 * @param supplier 供应商对象
	 */
	public void save(Supplier supplier) {
	
		// id为空则添加新供应商，否则更新供应商记录
		if (supplier.getId() == null) {
			supplierDao.insert(supplier);
		} else {
			supplierDao.update(supplier);
		}
	}
	
	/**
	 * 删除供应商
	 * @param id
	 * @return
	 */
	public boolean delete(Integer id) {
		
		if (id != null) {
			supplierDao.delete(id);
			return true;
		} 
		return false;
	}
	
	/**
	 * 查找供应商名称集合
	 * @return
	 */
	public List<Supplier> findSupplierNameList() {
		
		List<Supplier> resultList = new ArrayList<Supplier>();
		Iterator<?> iterator = supplierDao.findAllName().iterator();
		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			Supplier supplier = new Supplier();
			supplier.setId(Integer.valueOf(obj[0].toString()));
			supplier.setName(obj[1].toString());
			resultList.add(supplier);
		}
		
		return resultList;
	}
	
	@SuppressWarnings("rawtypes")
	public List<List> getDataList() {
		List<List> dataList = new ArrayList<List>();
		List<Supplier> supplierList = findSuppliers(
				"ORDER BY id DESC");
		// 将供应商集合装入数组集合中
		for (Supplier supplier : supplierList) {
			List<Object> singleData = new ArrayList<Object>();
			singleData.add(supplier.getId());
			singleData.add(supplier.getName());
			singleData.add(supplier.getContacts());
			singleData.add(supplier.getContactPhone());
			singleData.add(supplier.getCity());
			dataList.add(singleData);
		}
		
		return dataList;
	}
}
