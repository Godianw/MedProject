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
import com.med.entity.Supplier;
import com.med.service.SupplierService;
import com.med.util.DataEncapUtil;
import com.med.util.ExcelUtil;
import com.med.util.ExcelUtil.ExcelSuffix;
import com.med.util.PdfUtil;

@Controller
@RequestMapping("supplier")
public class SupplierController {

	@Autowired
	SupplierService supplierService;
	
	/**
	 * 进入供应商管理页面
	 * @return 视图名
	 */
	@RequestMapping("/info.do")
	public String supplier() {
		return "supplier";
	}
	
	/**
	 * 获取供应商表数据
	 * @param dtParams:DataTables请求的内容
	 * @return 封装了返回数据的map
	 */
	@RequestMapping("/data_source.do")
	@ResponseBody
	public Object supplierDataSource(
			@ModelAttribute DTRequestParam dtParams,
			HttpServletRequest request
			) {
		
		// 获取供应商数据列表
		List list = supplierService.findSuppliersByPaging(
				dtParams.getConditionSql(), 
				dtParams.getCurPageStartIndex(), 
				dtParams.getLength());
		int recordsTotal = supplierService.findSuppliers(
				dtParams.getConditionSql()).size(); 
		int pageTotal = DataEncapUtil.getPageTotal(
				recordsTotal, dtParams.getLength());
		
		// 生成返回表格的集合
		Map result = DataEncapUtil.createDTResponse(
				dtParams, recordsTotal, pageTotal, list);

		return result;
	}
	
	// 添加供应商
	@RequestMapping("/save.do")
	@ResponseBody
	public Object save(Supplier supplier) {
		
		supplierService.save(supplier);
		Map resultMap = new HashMap<String, String>();
		resultMap.put("resultCode", "200");
		
		return resultMap;
	}
	
	// 查找供应商
	@RequestMapping("/find.do")
	@ResponseBody
	public Object find(Integer id) {

		return supplierService.findOne(id);
	}
	
	// 删除供应商
	@RequestMapping("/delete.do")
	@ResponseBody
	public Object delete(Integer id) {
		System.out.println("id" + id);
		
		supplierService.delete(id);
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
		.append(fileName)
		.append(".pdf").toString());  
		response.setContentType("application/vnd.ms-excel;charset=gb2312");
		OutputStream out = new BufferedOutputStream(response.getOutputStream());
		
		// 填充数据
		String[] columnName = new String[] {
				"编号", "供应商", "联系人", "联系电话", "城市"};
		List<List> dataList = new ArrayList<List>();
		List<Supplier> supplierList = supplierService.findSuppliers(
				"ORDER BY id DESC");
		// 将供应商集合装入数组集合中
		for (Supplier supplier : supplierList) {
			List<Object> singleData = new ArrayList<Object>();
			singleData.add(supplier.getId());
			singleData.add(supplier.getName());
			singleData.add(supplier.getContacts());
			singleData.add(supplier.getContactPhone());
			singleData.add(supplier.getCity());
			dataList.add(singleData);
		}
		
		// 将数据封装成pdf并写入response中
		PdfUtil pdfUtil = new PdfUtil(fileName, "供应商表", 
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
		.append(fileName)
		.append(".").append(suffix).toString());  
		response.setContentType("application/vnd.ms-excel;charset=gb2312");
		OutputStream out = new BufferedOutputStream(response.getOutputStream());
		
		// 填充数据
		String[] columnName = new String[] {
				"编号", "供应商", "联系人", "联系电话", "城市"};
		List<List> dataList = new ArrayList<List>();
		List<Supplier> supplierList = supplierService.findSuppliers(
				"ORDER BY id DESC");
		// 将供应商集合装入数组集合中
		for (Supplier supplier : supplierList) {
			List<Object> singleData = new ArrayList<Object>();
			singleData.add(supplier.getId());
			singleData.add(supplier.getName());
			singleData.add(supplier.getContacts());
			singleData.add(supplier.getContactPhone());
			singleData.add(supplier.getCity());
			dataList.add(singleData);
		}
		
		ExcelSuffix excelSuffix = suffix.equals("xlsx") ? 
				ExcelSuffix.XLSX : ExcelSuffix.XLS;
		// 将数据封装成excel并写入response中
		ExcelUtil excelUtil = new ExcelUtil(fileName, "供应商表", 
				columnName, dataList, excelSuffix);
		excelUtil.writeOutExcel(out);

		if (out != null) {
			out.close();
		}
	}
	
	@RequestMapping("/find_name.do")
	@ResponseBody
	public Object findAllName() {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("resultCode", "200");
		resultMap.put("data", supplierService.findSupplierNameList());
		
		return resultMap;
	}
}
