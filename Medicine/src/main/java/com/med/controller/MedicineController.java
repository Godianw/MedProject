package com.med.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.med.entity.DTRequestParam;
import com.med.entity.Medicine;
import com.med.exception.DataInvalidException;
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
	@SuppressWarnings("rawtypes")
	@RequestMapping("/data_source.do")
	@ResponseBody
	public Object medicineDataSource(
			@ModelAttribute DTRequestParam dtParams,
			HttpServletRequest request
			) {

		int recordsTotal = medicineService.findMedicines(
				dtParams.getConditionSql()).size();
		int curIndex = dtParams.getCurPageStartIndex();
		List list = medicineService.findMedicinesByPaging(
				dtParams.getConditionSql(), 
				(curIndex >= recordsTotal ? 0 : curIndex),
				dtParams.getLength());
		int pageTotal = DataEncapUtil.getPageTotal(
				recordsTotal, dtParams.getLength());
		
		// 生成返回表格的集合
		Map result = DataEncapUtil.createDTResponse(
				dtParams, recordsTotal, pageTotal, list);

		return result;
	}

	/**
	 * 保存药品信息
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

	// 查找药品
	@RequestMapping("/find.do")
	@ResponseBody
	public Object find(Integer id) {

		return medicineService.findOne(id);
	}

	// 删除药品
	@RequestMapping("/delete.do")
	@ResponseBody
	public Object delete(Integer id) {

		medicineService.delete(id);
		HashMap<String, Object> resultMap = 
				new HashMap<String, Object>();
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
				"编号", "药品名称", "供应商", "生产批号", "生产日期", 
				"保质期", "单位", "进价", "售价" };
		List<List> dataList = medicineService.getDataList();

		// 将数据封装成pdf并写入response中
		new PdfUtil().writeOutPdf("药品信息表", 
				columnName, dataList, out);

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
				"编号", "药品名称", "供应商", "生产批号", "生产日期", 
				"保质期", "单位", "进价", "售价" };
		List<List> dataList = medicineService.getDataList();

		ExcelSuffix excelSuffix = ExcelSuffix.XLS;
		if (suffix.equals("xlsx"))
			excelSuffix = ExcelSuffix.XLSX;
		// 将数据封装成excel并写入response中
		new ExcelUtil().writeOutExcel("药品信息表", 
				columnName, dataList, excelSuffix,out);

		if (out != null) {
			out.close();
		}
	}
	
	@RequestMapping("/downloadTemplate.do")
	public void downloadTemplate(HttpServletRequest request,
			HttpServletResponse response) 
			throws IOException, EncryptedDocumentException, 
			InvalidFormatException {
		// 修改响应头以弹出文件保存框
		String fileName = new String(
				"药品信息表导入模板.xlsx".getBytes("gb2312"), "iso8859-1");
		response.addHeader("Content-Disposition", 
			"attachment;filename=" + fileName);  
		response.setContentType(
				"application/vnd.ms-excel;charset=gb2312");
		OutputStream out = new BufferedOutputStream(
				response.getOutputStream());
		
		// 获取本地路径并写入输入流
		InputStream input = MedicineController.class.getClassLoader()
				.getResourceAsStream("serverfile/药品信息表导入模板.xlsx");
		
		Workbook workbook = WorkbookFactory.create(input);
		workbook.write(out);
		out.flush();
		
		if (workbook != null) {
			workbook.close();
		}
	}
	
	@RequestMapping("/import.do")
	@ResponseBody
	public Object importFromExcel(
			@RequestParam("file") MultipartFile file) 
					throws EncryptedDocumentException, 
					InvalidFormatException, IOException {
		
		// 读取excel中的数据
		List<List<Object>> list = 
				new ExcelUtil().readFromExcel(file.getInputStream());
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			if (medicineService.batchInsert(list)) {
				resultMap.put("code", "200");
			}
		} catch (DataInvalidException ex) {
			resultMap.put("code", "500");
			resultMap.put("message", ex.getMessage() 
					+ "， 导入失败");
		}
		
		return resultMap;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/find_name.do")
	@ResponseBody
	public Object findAllName() {
		
		Map resultMap = new HashMap<String, String>();
		resultMap.put("resultCode", "200");
		resultMap.put("data", medicineService.findMedicineNameList());
		
		return resultMap;
	}
}
