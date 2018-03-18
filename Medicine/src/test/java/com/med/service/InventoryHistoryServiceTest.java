package com.med.service;

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
		for (InventoryHistory inventoryHistory :
				inventoryHistoryService.findWithinLimitDay(365, 0)) {
			System.out.println(inventoryHistory.getMedName());
		}
	}
}
