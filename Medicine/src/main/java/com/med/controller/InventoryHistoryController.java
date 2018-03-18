package com.med.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.med.entity.DTRequestParam;
import com.med.entity.InventoryHistory;
import com.med.service.InventoryHistoryService;
import com.med.util.DataEncapUtil;
import com.med.util.ExcelUtil;
import com.med.util.PdfUtil;
import com.med.util.ExcelUtil.ExcelSuffix;

@Controller
@RequestMapping("inventory_history")
public class InventoryHistoryController {

	@Autowired
	InventoryHistoryService inventoryHistoryService;
	
	@RequestMapping("/info.do")
	public String inventoryHistoryInfo() {
		return "inventory_history";
	}
	
	/**
	 * 获取药品表数据
	 * @param dtParams:封装了DataTables请求的内容
	 * @param request:servlet请求参数
	 * @return 封装了返回数据的map
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/data_source.do")
	@ResponseBody
	public Object inventoryHistoryDataSource(
			@ModelAttribute DTRequestParam dtParams, 
			HttpServletRequest request) {
		
		List list = inventoryHistoryService.findInventoryHistoriesByPaging(
				dtParams.getConditionSql(),
				dtParams.getCurPageStartIndex(),
				dtParams.getLength());
		int recordsTotal = inventoryHistoryService.findInventoryHistories(
				dtParams.getConditionSql()).size();
		int pageTotal = DataEncapUtil.getPageTotal(
				recordsTotal, dtParams.getLength());
		
		// 生成返回表格的集合
		Map result = DataEncapUtil.createDTResponse(
				dtParams, recordsTotal, pageTotal, list);

		return result;
	}
	
	/**
	 * 导出到pdf中
	 * @param fileName
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/toPdf.do")
	public void exportToPdf(String fileName, 
			HttpServletRequest request, HttpServletResponse response) 
					throws IOException {
		// 修改响应头以弹出文件保存框
		fileName = new String(fileName.getBytes("gb2312"), "iso8859-1");
		response.addHeader("Content-Disposition", 
				new StringBuffer("attachment;filename=") 
				.append(fileName).append(".pdf").toString());  
		response.setContentType("application/vnd.ms-excel;charset=gb2312");
		OutputStream out = new BufferedOutputStream(response.getOutputStream());
		
		// 填充数据
		String[] columnName = new String[] {
				"编号", "药品名称", "数量", "操作日期", "操作类型" };
		List<List> dataList = new ArrayList<List>();
		List<InventoryHistory> inventoryHistoriesList = 
				inventoryHistoryService.findInventoryHistories("ORDER BY id DESC");
		// 将操作历史记录装入集合数组中
		for (InventoryHistory inventoryHistory : inventoryHistoriesList) {
			List<Object> singleData = new ArrayList<Object>();
			singleData.add(inventoryHistory.getId());
			singleData.add(inventoryHistory.getMedName());
			singleData.add(inventoryHistory.getQuantity());
			singleData.add(inventoryHistory.getTime());
			singleData.add(
					(inventoryHistory.getOptype() ? "出库" : "入库"));
			dataList.add(singleData);
		}
		
		// 将数据封装成pdf并写入response中
		PdfUtil pdfUtil = new PdfUtil(fileName, "库存操作历史记录表", 
				columnName, dataList);
		pdfUtil.writeOutPdf(out);

		if (out != null) {
			out.close();
		}
	}
	
	/**
	 * 导出到excel中
	 * @param fileName
	 * @param suffix
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/toExcel.do")
	public void exportToExcel(String fileName, String suffix, 
			HttpServletRequest request, HttpServletResponse response) 
					throws IOException {
		// 修改响应头以弹出文件保存框
		fileName = new String(fileName.getBytes("gb2312"), "iso8859-1");
		response.addHeader("Content-Disposition", 
				new StringBuffer("attachment;filename=") 
				.append(fileName).append(".").append(suffix).toString());  
		response.setContentType("application/vnd.ms-excel;charset=gb2312");
		OutputStream out = new BufferedOutputStream(response.getOutputStream());

		// 填充数据
		String[] columnName = new String[] { 
				"编号", "药品名称", "数量", "操作日期", "操作类型" };
		List<List> dataList = new ArrayList<List>();
		List<InventoryHistory> inventoryHistoriesList = inventoryHistoryService
				.findInventoryHistories("ORDER BY id DESC");
		// 将操作历史记录装入集合数组中
		for (InventoryHistory inventoryHistory : inventoryHistoriesList) {
			List<Object> singleData = new ArrayList<Object>();
			singleData.add(inventoryHistory.getId());
			singleData.add(inventoryHistory.getMedName());
			singleData.add(inventoryHistory.getQuantity());
			singleData.add(inventoryHistory.getTime());
			singleData.add((inventoryHistory.getOptype() ? "出库" : "入库"));
			dataList.add(singleData);
		}
		
		ExcelSuffix excelSuffix = ExcelSuffix.XLS;
		if (suffix.equals("xlsx"))
			excelSuffix = ExcelSuffix.XLSX;
		// 将数据封装成excel并写入response中
		ExcelUtil excelUtil = new ExcelUtil(fileName, "库存操作历史记录表", 
				columnName, dataList, excelSuffix);
		excelUtil.writeOutExcel(out);

		if (out != null) {
			out.close();
		}
	}
}
