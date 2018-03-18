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
import com.med.entity.Medicine;
import com.med.service.MedicineService;
import com.med.util.DataEncapUtil;
import com.med.util.ExcelUtil;
import com.med.util.ExcelUtil.ExcelSuffix;
import com.med.util.PdfUtil;

@Controller
@RequestMapping("medicine")
public class MedicineController {

	@Autowired
	MedicineService medicineService;
	
	/**
	 * 进入药品信息管理页面
	 * @return 视图名
	 */
	@RequestMapping("/info.do")
	public String medicine() {
		return "medicine";
	}

	/**
	 * 获取药品表数据
	 * @param dtParams:封装了DataTables请求的内容
	 * @param request:servlet请求参数
	 * @return 封装了返回数据的map
	 */
	@RequestMapping("/data_source.do")
	@ResponseBody
	public Object medicineDataSource(
			@ModelAttribute DTRequestParam dtParams,
			HttpServletRequest request
			) {

		List list = medicineService.findMedicinesByPaging(
				dtParams.getConditionSql(), 
				dtParams.getCurPageStartIndex(),
				dtParams.getLength());
		int recordsTotal = medicineService.findMedicines(
				dtParams.getConditionSql()).size();
		int pageTotal = DataEncapUtil.getPageTotal(
				recordsTotal, dtParams.getLength());
		
		// 生成返回表格的集合
		Map result = DataEncapUtil.createDTResponse(
				dtParams, recordsTotal, pageTotal, list);

		return result;
	}

	/**
	 * 保存供应商信息
	 * @param medicine
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/save.do")
	@ResponseBody
	public Object save(Medicine medicine) {

		medicineService.save(medicine);
		Map resultMap = new HashMap<String, String>();
		resultMap.put("resultCode", "200");

		return resultMap;
	}

	// 查找供应商
	@RequestMapping("/find.do")
	@ResponseBody
	public Object find(Integer id) {

		return medicineService.findOne(id);
	}

	// 删除供应商
	@RequestMapping("/delete.do")
	@ResponseBody
	public Object delete(Integer id) {

		medicineService.delete(id);
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
				"编号", "药品名称", "供应商", "进价", "单价"};
		List<List> dataList = new ArrayList<List>();
		List<Medicine> medicineList = medicineService.findMedicines(
				"ORDER BY id DESC");
		// 将药品集合装入数组集合中
		for (Medicine medicine : medicineList) {
			List<Object> singleData = new ArrayList<Object>();
			singleData.add(medicine.getId());
			singleData.add(medicine.getName());
			singleData.add(medicine.getSupplier().getName());
			singleData.add(medicine.getPurchasePrice());
			singleData.add(medicine.getSinglePrice());
			dataList.add(singleData);
		}

		// 将数据封装成pdf并写入response中
		PdfUtil pdfUtil = new PdfUtil(fileName, "药品信息表", 
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
				"编号", "药品名称", "供应商", "进价", "单价" };
		List<List> dataList = new ArrayList<List>();
		List<Medicine> medicineList = medicineService.findMedicines(
				"ORDER BY id DESC");
		// 将药品集合装入数组集合中
		for (Medicine medicine : medicineList) {
			List<Object> singleData = new ArrayList<Object>();
			singleData.add(medicine.getId());
			singleData.add(medicine.getName());
			singleData.add(medicine.getSupplier().getName());
			singleData.add(medicine.getPurchasePrice());
			singleData.add(medicine.getSinglePrice());
			dataList.add(singleData);
		}

		ExcelSuffix excelSuffix = ExcelSuffix.XLS;
		if (suffix.equals("xlsx"))
			excelSuffix = ExcelSuffix.XLSX;
		// 将数据封装成excel并写入response中
		ExcelUtil excelUtil = new ExcelUtil(fileName, "药品信息表", 
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
		resultMap.put("data", medicineService.findMedicineNameList());
		
		return resultMap;
	}
}
