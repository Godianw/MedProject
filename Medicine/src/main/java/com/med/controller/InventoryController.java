package com.med.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.med.entity.DTRequestParam;
import com.med.service.InventoryService;
import com.med.util.DataEncapUtil;

@Controller
@RequestMapping("inventory")
public class InventoryController {

	@Autowired
	InventoryService inventoryService;
	
	/**
	 * 进入库存信息管理界面
	 * @return 视图名
	 */
	@RequestMapping("/info.do")
	public String inventoryInfo() {
		return "inventory";
	}
	
	/**
	 * 获取库存信息
	 * @param dtParams:封装了DataTables请求的内容
	 * @param request:servlet请求参数
	 * @return 封装了返回数据的map
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/data_source.do")
	@ResponseBody
	public Object inventoryDataSource(
			@ModelAttribute DTRequestParam dtParams, 
			HttpServletRequest request) {
		
		List list = inventoryService.findInventorysByPaging(
				dtParams.getConditionSql(), 
				dtParams.getCurPageStartIndex(),
				dtParams.getLength());
		int recordsTotal = inventoryService.findInventorys(
				dtParams.getConditionSql()).size();
		int pageTotal = DataEncapUtil.getPageTotal(
				recordsTotal, dtParams.getLength());
	
		// 生成返回表格的集合
		Map result = DataEncapUtil.createDTResponse(
				dtParams, recordsTotal, pageTotal, list);

		return result;
	}
	
	// 查找库存信息
	@RequestMapping("/find.do")
	@ResponseBody
	public Object find(Integer id) {
		
		return inventoryService.findOne(id);
	}
	
	// 更新库存
	@SuppressWarnings("unchecked")
	@RequestMapping("/update_quantity.do") 
	@ResponseBody
	public Object updateQuantity(Integer id, Integer quantity, Integer type) {
		
		if (inventoryService.updateQuantity(id, quantity, type)) {
			Map resultMap = new HashMap<String, String>();
			resultMap.put("resultCode", "200");
			return resultMap;
		}
		
		return null;
	}
	
	// 更新库存预警值
	@SuppressWarnings("unchecked")
	@RequestMapping("/update_warning.do")
	@ResponseBody
	public Object updateWarning(Integer id, Integer warningQuantity) {
		
		if (inventoryService.updateWarningQuantity(id, warningQuantity)) {
			Map resultMap = new HashMap<String, String>();
			resultMap.put("resultCode", "200");
			return resultMap;
		}
		
		return null;
	}
	
	@RequestMapping("find_quantity.do")
	@ResponseBody
	public Object findQuantity(Integer id) {
		
		return inventoryService.findQuantity(id);
	}
}
