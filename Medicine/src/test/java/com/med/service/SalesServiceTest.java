package com.med.service;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.med.config.RootConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
public class SalesServiceTest {

	@Autowired
	private SalesService salesService;
	
	@Test
	public void findSalesMoneyByMonth() {
		List ds = salesService.getMonthDataSource();
		System.out.println(ds);
	}
}
