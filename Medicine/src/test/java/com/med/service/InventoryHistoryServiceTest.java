package com.med.service;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.med.config.RootConfig;
import com.med.entity.InventoryHistory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
public class InventoryHistoryServiceTest {

	@Autowired
	InventoryHistoryService inventoryHistoryService;
	
	@Test
	public void findWithinWeek() {
		List list = inventoryHistoryService.findWithinLimitDay(
				7, false, false);
		System.out.println(list.size());
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			System.out.println("name = " + obj[0].toString());
			System.out.println("quantity = " 
					+ Integer.valueOf(obj[1].toString()));
		}
	}
}
