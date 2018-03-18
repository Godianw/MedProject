package com.med.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.med.config.RootConfig;
import com.med.entity.Supplier;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootConfig.class)
public class SupplierServiceTest {
	
	@Autowired
	SupplierService supplierService;
	
	@Test
	public void findSuppliers() {
		for (Supplier supplier : supplierService.findSuppliers(null))
			System.out.println(supplier.getName());
		System.out.println(supplierService.findSuppliers(null).size());
	}
	
	@Test
	public void findSupplierByPaging() {
		for (Supplier supplier : supplierService.findSuppliersByPaging(
				"WHERE id >= 10 OR name LIKE '%name%' ", 0, 12))
			System.out.println(supplier.getName());
	}
	
	@Test
	public void delete() {
		System.out.println(supplierService.delete(9));
	}
}
