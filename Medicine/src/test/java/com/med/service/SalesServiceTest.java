package com.med.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.med.config.RootConfig;
import com.med.dao.SalesDao;
import com.med.entity.Sales;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
@Transactional
public class SalesServiceTest {

	@Autowired
	private SalesDao salesDao;
	
	@Autowired
	private SalesService salesService;
	
	@Test
	public void findSalesMoneyByMonth() {
		List ds = salesService.getMonthDataSource();
		System.out.println(ds);
	}
	
	@Test
	public void findAll() {
		for (Sales sales : salesDao.findByPaging(null, 10, 10)) {
			System.out.println(sales.getId());
		}
	}
}
