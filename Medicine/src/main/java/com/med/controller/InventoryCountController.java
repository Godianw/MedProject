package com.med.controller;

import java.util.HashMap;
import java.util.Map;

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
			boolean optype, boolean type) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("dataSource", 
				inventoryHistoryService.findWithinLimitDay(
				limitDay, optype, type));
		return map;
	}
}
