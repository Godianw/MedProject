package com.med.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.med.service.SalesService;

@Controller
@RequestMapping("salesCount")
public class salesCountController {

	@Autowired
	SalesService salesService;
	
	@RequestMapping("/info.do")
	public String salesCount() {
		return "sales_count";
	}
	
	@RequestMapping("/monthInfo.do")
	@ResponseBody
	public Object monthInfo() {
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		/* 标题项 */
		List<String> dimension = new ArrayList<String>();
		for (int i = 1; i < 13; ++ i) {
			dimension.add(i + "月");
		}
		
		List<String> years = salesService.findYears();
		for (int i = 0; i < years.size(); ++ i) {
			years.set(i, years.get(i) + "年销售额");
		}
		
		// 维度
		dataMap.put("dimension", dimension);
		// 图例
		dataMap.put("legend", years);
		// 数据源
		dataMap.put("dataSource", salesService.getMonthDataSource());
		
		return dataMap;
	}
	
	@RequestMapping("/yearInfo.do")
	@ResponseBody
	public Object yearInfo() {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// 维度
		dataMap.put("dimension", salesService.findYears());
		// 图例
		dataMap.put("legend", "年销售额");
		// 数据源
		dataMap.put("dataSource", salesService.getYearDataSource());
		
		return dataMap;
	}
}
