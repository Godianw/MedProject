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
import com.med.entity.Staff;
import com.med.service.StaffService;
import com.med.util.DataEncapUtil;
import com.med.util.ExcelUtil;
import com.med.util.PdfUtil;
import com.med.util.ExcelUtil.ExcelSuffix;

@Controller
@RequestMapping("/staff")
public class StaffController {

	@Autowired
	StaffService staffService;
	
	/**
	 * 进入药品信息管理页面
	 * @return 视图名
	 */
	@RequestMapping("/info.do")
	public String staffInfo() {
		return "staff";
	}
	
	/**
	 * 获取药品表数据
	 * @param dtParams:DataTables请求的内容
	 * @return 封装了返回数据的map
	 */
	@RequestMapping("/data_source.do")
	@ResponseBody
	public Object staffDataSource(
			@ModelAttribute DTRequestParam dtParams,
			HttpServletRequest request
			) {
		List list = staffService.fingStaffsByPaging(
				dtParams.getConditionSql(), 
				dtParams.getCurPageStartIndex(),
				dtParams.getLength());
		int recordsTotal = staffService.findStaffs(
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
	public Object save(Staff staff) {
		
		staffService.save(staff);
		Map resultMap = new HashMap<String, String>();
		resultMap.put("resultCode", "200");

		return resultMap;
	}
	
	// 查找员工
	@RequestMapping("/find.do")
	@ResponseBody
	public Object find(Integer id) {
		
		return staffService.findOne(id);
	}
	
	// 删除员工
	@RequestMapping("/delete.do")
	@ResponseBody
	public Object delete(Integer id) {
		
		staffService.delete(id);
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
		response.addHeader("Content-Disposition", new StringBuffer(
				"attachment;filename=").append(fileName).append(".pdf")
				.toString());
		response.setContentType("application/vnd.ms-excel;charset=gb2312");
		OutputStream out = new BufferedOutputStream(response.getOutputStream());

		// 填充数据
		String[] columnName = new String[] {
				"编号", "姓名", "联系电话", "职位", "当前状态"};
		List<List> dataList = new ArrayList<List>();
		List<Staff> staffList = staffService.findStaffs(
				"ORDER BY id DESC");
		
		// 将药品集合装入数组集合中
		for (Staff staff : staffList) {
			List<Object> singleData = new ArrayList<Object>();
			singleData.add(staff.getId());
			singleData.add(staff.getName());
			singleData.add(staff.getPhone());
			singleData.add(staff.getPost());
			singleData.add(
					(staff.getState() ? "在职" : "离职"));
			dataList.add(singleData);
		}
		
		// 将数据封装成pdf并写入response中
		PdfUtil pdfUtil = new PdfUtil(fileName, "员工信息表", columnName, dataList);
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
					.append(fileName).append(".")
					.append(suffix).toString());
		response.setContentType("application/vnd.ms-excel;charset=gb2312");
		OutputStream out = new BufferedOutputStream(response.getOutputStream());

		// 填充数据
		String[] columnName = new String[] { 
				"编号", "姓名", "联系电话", "职位", "当前状态" };
		List<List> dataList = new ArrayList<List>();
		List<Staff> staffList = staffService.findStaffs(
				"ORDER BY id DESC");

		// 将药品集合装入数组集合中
		for (Staff staff : staffList) {
			List<Object> singleData = new ArrayList<Object>();
			singleData.add(staff.getId());
			singleData.add(staff.getName());
			singleData.add(staff.getPhone());
			singleData.add(staff.getPost());
			singleData.add(
					(staff.getState() ? "在职" : "离职"));
			dataList.add(singleData);
		}

		ExcelSuffix excelSuffix = ExcelSuffix.XLS;
		if (suffix.equals("xlsx"))
			excelSuffix = ExcelSuffix.XLSX;
		// 将数据封装成excel并写入response中
		ExcelUtil excelUtil = new ExcelUtil(fileName, "员工信息表", 
				columnName, dataList, excelSuffix);
		excelUtil.writeOutExcel(out);

		if (out != null) {
			out.close();
		}
				
	}
	
	@RequestMapping("/find_name.do")
	@ResponseBody
	public Object findAllName() {
		
		Map resultMap = new HashMap<String, String>();
		resultMap.put("resultCode", "200");
		resultMap.put("data", staffService.findStaffNameList());
		
		return resultMap;
	}
}
