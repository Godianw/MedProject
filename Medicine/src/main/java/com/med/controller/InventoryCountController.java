package com.med.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.med.service.InventoryHistoryService;

@Controller
@RequestMapping("inventoryCount")
public class InventoryCountController {
	
	@Autowired
	InventoryHistoryService inventoryHistoryService;
	
	@RequestMapping("/info.do")
	public String info() {
		return "inventory_count";
	}
	
	@RequestMapping("/getIhDataSource.do")
	@ResponseBody
	public Object getIhDataSource(int limitDay, 
			boolean optype) {
		
		return inventoryHistoryService.findWithinLimitDay(
				limitDay, optype);
	}
}
