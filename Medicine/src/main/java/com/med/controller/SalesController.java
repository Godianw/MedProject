package com.med.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.med.entity.Sales;
import com.med.service.SalesService;
import com.med.util.DataEncapUtil;
import com.med.util.ExcelUtil;
import com.med.util.PdfUtil;
import com.med.util.ExcelUtil.ExcelSuffix;

@Controller
@RequestMapping("sales")
public class SalesController {

	@Autowired
	SalesService salesService;
	
	/**
	 * 进入销售信息管理页面
	 * @return 视图名
	 */
	@RequestMapping("/info.do")
	public String salesInfo() {
		return "sales";
	}
	
	/**
	 * 获取销售表数据
	 * @param dtParams:DataTables请求的内容
	 * @return 封装了返回数据的map
	 */
	@RequestMapping("/data_source.do")
	@ResponseBody
	public Object salesDataSource(
			@ModelAttribute DTRequestParam dtParams,
			HttpServletRequest request
			) {
		List list = salesService.findSalesByPaging(
				dtParams.getConditionSql(), 
				dtParams.getCurPageStartIndex(),
				dtParams.getLength());
		int recordsTotal = salesService.findSales(
				dtParams.getConditionSql()).size();
		int pageTotal = DataEncapUtil.getPageTotal(
				recordsTotal, dtParams.getLength());
		
		// 生成返回表格的集合
		Map result = DataEncapUtil.createDTResponse(
				dtParams, recordsTotal, pageTotal, list);

		return result;
	}
	
	// 更新销售记录
	@RequestMapping("/save.do")
	@ResponseBody
	public Object save(Sales sales) {
		
		salesService.save(sales);
		Map resultMap = new HashMap<String, String>();
		resultMap.put("resultCode", "200");

		return resultMap;
	}
	
	// 删除销售记录
	@RequestMapping("/delete.do")
	@ResponseBody
	public Object delete(Integer id) {
		
		salesService.delete(id);
		Map resultMap = new HashMap<String, String>();
		resultMap.put("resultCode", "200");

		return resultMap;
	}
	

	/**
	 * 导出到pdf中
	 * @param fileName
	 * @param request
	 * @param response
	 * @throws IOException
	 */
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
				"编号", "操作人员", "药品名", "数量", "日期", "总金额"};
		List<List> dataList = new ArrayList<List>();
		List<Sales> salesList = salesService.findSales(
				"ORDER BY id DESC");
		// 将销售集合装入数组集合中
		for (Sales sales : salesList) {
			List<Object> singleData = new ArrayList<Object>();
			singleData.add(sales.getId());
			singleData.add(sales.getUserName());
			singleData.add(sales.getMedicine());
			singleData.add(sales.getCount());
			singleData.add(sales.getDatetime());
			singleData.add(sales.getMoney());
			dataList.add(singleData);
		}
		
		// 将数据封装成pdf并写入response中
		PdfUtil pdfUtil = new PdfUtil(fileName, "销售信息表", 
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
				"编号", "操作人员", "药品名", "数量", "日期", "总金额"};
		List<List> dataList = new ArrayList<List>();
		List<Sales> salesList = salesService.findSales(
				"ORDER BY id DESC");
		// 将销售集合装入数组集合中
		for (Sales sales : salesList) {
			List<Object> singleData = new ArrayList<Object>();
			singleData.add(sales.getId());
			singleData.add(sales.getUserName());
			singleData.add(sales.getMedicine());
			singleData.add(sales.getCount());
			singleData.add(sales.getDatetime());
			singleData.add(sales.getMoney());
			dataList.add(singleData);
		}
		
		ExcelSuffix excelSuffix = ExcelSuffix.XLS;
		if (suffix.equals("xlsx"))
			excelSuffix = ExcelSuffix.XLSX;
		// 将数据封装成excel并写入response中
		ExcelUtil excelUtil = new ExcelUtil(fileName, "销售信息表", 
				columnName, dataList, excelSuffix);
		excelUtil.writeOutExcel(out);

		if (out != null) {
			out.close();
		}
	}
}
