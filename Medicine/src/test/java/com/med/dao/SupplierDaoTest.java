package com.med.dao;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.med.config.RootConfig;
import com.med.entity.Supplier;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootConfig.class)
@javax.transaction.Transactional
public class SupplierDaoTest {

	@Autowired
	public SupplierDao supplierDao;
	
	@Test
	public void findAll() {
		for (Supplier supplier : supplierDao.findAll("ORDER BY id DESC"))
			System.out.println(supplier.getName());
	}
	
	@SuppressWarnings("rawtypes")
	@Test 
	public void findALLName() {
		List list = supplierDao.findAllName();
		Iterator iterator = list.iterator();
		 while(iterator.hasNext()) {
			 Object[] supplier = (Object[]) iterator.next();
			 System.out.println(supplier[1].toString());
		 }
		
	}
	
	@Test
	public void findOne() {
		System.out.println(supplierDao.findOne(5).getName());
	}
	
	@Test
//	@Rollback(value = false)	// spring test默认会事务回滚, 使用该属性阻止事务回滚
	public void insert() {
		System.out.println("before insert, the count of records is "
				+ supplierDao.findAll(null).size());
		Supplier supplier = new Supplier();
		supplier.setName("name4");
		supplier.setContacts("contact4");
		supplier.setContactPhone("18818429874");
		supplier.setCity("ZhaoQing");
		supplierDao.insert(supplier);
		System.out.println("after insert, the count of records is "
				+ supplierDao.findAll(null).size()
				+ ", and the new ID is " + supplier.getId());
	}
	
	@Test
//	@Rollback(false)
	public void update() {
		System.out.println("before update, the count of records is "
				+ supplierDao.findAll(null).size());
		Supplier supplier = new Supplier();
		supplier.setId(6);
		supplier.setName("name1");
		supplier.setContacts("contact1");
		supplier.setContactPhone("18818429870");
		supplier.setCity("ZhaoQing");
		supplierDao.update(supplier);
		System.out.println("after insert, the count of records is "
				+ supplierDao.findAll(null).size()
				+ ", and the ID is " + supplier.getId());
	}
	
	@Test
	public void delete() {
		supplierDao.delete(6);
	}
	
	@Test
	public void findByPaging() {
		for (Supplier supplier : supplierDao.findByPaging("ORDER BY id DESC", 0, 3))
			System.out.println(supplier.getName());
	}
}
