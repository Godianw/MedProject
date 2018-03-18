package com.med.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.med.web.DataInvalidException;

/**
 * Excel工具类
 * @author Runtime
 * @date 2018/1/17
 * @version v1.0
 */
public class ExcelUtil {
	
	// 文件名
	private String fileName;	
	// 表格标题
	private String tableTitle;
	// 列名
	private String[] columnName;
	// 表格数据
	private List<List> dataList = new ArrayList<List>();
	// 文件后缀类型
	private ExcelSuffix suffix;
	
	public enum ExcelSuffix {
		XLS,	// HSSF
		XLSX	// XSSF
	}
	
	/**
	 * 构造器
	 * @param fileName
	 * @param tableTitle
	 * @param columnName
	 * @param dataList
	 * @param suffix
	 */
	@SuppressWarnings("rawtypes")
	public ExcelUtil(String fileName, String tableTitle, 
			String[] columnName, List<List> dataList, 
			ExcelSuffix suffix) {
		this.fileName = fileName;
		this.tableTitle = tableTitle;
		this.columnName = columnName;
		this.dataList = dataList;
		this.suffix = suffix;
	}
	
	/**
	 * 将Excel写入输出流对象中
	 * @param out 输出流对象
	 * @throws IOException
	 */
	public void writeOutExcel(OutputStream out) throws IOException {
		
		// 导出excel之前先检查表格数据的有效性
		checkDataBrforeExport();
		
		// 创建工作簿
		Workbook workbook = getNewWorkbook();
		// 创建工作表
		String safeName = WorkbookUtil.createSafeSheetName(tableTitle);
		Sheet sheet = workbook.createSheet(safeName);	
		
		/* 表格名称  */
		// 创建表格名称行
		Row tableNameRow = sheet.createRow(0);
		Cell tableName = tableNameRow.createCell(0);
		tableName.setCellValue(tableTitle);
		// 标题样式
		CellStyle tableNameStyle = getTableNameCellStyle(workbook);
		tableName.setCellStyle(tableNameStyle);	
		// 合并单元格
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, columnName.length - 1));
		
		/* 表头 */
		// 创建表头
		Row headerRow = sheet.createRow(2);
		CellStyle headerStyle = getHeaderCellStyle(workbook);
		for (int i = 0; i < columnName.length; ++ i) {
			// 填充表头数据
			Cell header = headerRow.createCell(i);
			header.setCellValue(columnName[i]);
			// 表头样式
			header.setCellStyle(headerStyle);
		}
		
		/* 数据 */
		// 填充表格数据
		int rowbegin = 3;
		CellStyle dataCellStyle = getDataCellStyle(workbook);
		for (List singleData : dataList) {
			Row dataRow = sheet.createRow(rowbegin ++); 
			int cellbegin = 0;
			for (Object obj : singleData) {
				Cell dataCell = dataRow.createCell(cellbegin ++);
				// 设置值
				setCellValueByAutoClass(dataCell, obj);	
				// 设置样式
				dataCell.setCellStyle(dataCellStyle);	
			}
		}
		
		// 列自适应
		for (int i = 0; i < columnName.length; ++ i)
			sheet.autoSizeColumn(i);
		
		workbook.write(out);
		out.flush();
		
		if (workbook != null) {
			workbook.close();
		}
	}	
	
	/**
	 * 获取新的工作簿
	 * @return 工作簿对象
	 */
	protected Workbook getNewWorkbook() {
		
		if (ExcelSuffix.XLS == suffix) 
			return new HSSFWorkbook();
		else
			return new XSSFWorkbook();
	}
	
	/**
	 * 在导出excel之前检查数据是否有效
	 */
	protected void checkDataBrforeExport() {

		if (columnName.length <= 0)
			throw new DataInvalidException("columnName is not filled");
		if (dataList.size() <= 0)
			throw new DataInvalidException("dataList is not filled");
	}
	
	/**
	 * 表名样式
	 * @param workbook 工作簿
	 * @return 单元格样式
	 */
	protected CellStyle getTableNameCellStyle(Workbook workbook) {
		
		CellStyle style = workbook.createCellStyle();
		
		/* 数字 */
		
		/* 对齐 */
		// 文本对齐
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		// 取消自动换行
		style.setWrapText(false);
		
		/* 字体 */
		Font font = workbook.createFont();
		font.setFontName("微软雅黑");
		font.setFontHeightInPoints((short) 14);
		font.setBold(true);
		font.setColor(IndexedColors.BLACK.getIndex());
		style.setFont(font);
		
		/* 边框 */
		
		/* 填充 */
		
		return style;
	}
	
	/**
	 * 表头样式
	 * @param workbook 工作簿
	 * @return 单元格样式
	 */
	protected CellStyle getHeaderCellStyle(Workbook workbook) {

		CellStyle style = workbook.createCellStyle();

		/* 数字 */

		/* 对齐 */
		// 文本对齐
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		// 取消自动换行
		style.setWrapText(false);

		/* 字体 */
		Font font = workbook.createFont();
		font.setFontName("微软雅黑");
		font.setFontHeightInPoints((short) 12);
		font.setBold(true);
		font.setColor(IndexedColors.BLACK.getIndex());
		style.setFont(font);

		/* 边框 */
		// 边框使用细线
		BorderStyle borderStyle = BorderStyle.THIN;		
		// 边框颜色为黑色
		short borderColor = IndexedColors.BLACK.getIndex();
		style.setBorderTop(borderStyle);
		style.setTopBorderColor(borderColor);
		style.setBorderRight(borderStyle);
		style.setRightBorderColor(borderColor);
		style.setBorderBottom(borderStyle);
		style.setBottomBorderColor(borderColor);
		style.setBorderLeft(borderStyle);
		style.setLeftBorderColor(borderColor);

		/* 填充 */

		return style;
	}

	/**
	 * 数据单元格样式
	 * @param workbook 工作簿
	 * @return 单元格样式
	 */
	protected CellStyle getDataCellStyle(Workbook workbook) {

		CellStyle style = workbook.createCellStyle();

		/* 数字 */

		/* 对齐 */
		// 文本对齐
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		// 取消文本自动换行
		style.setWrapText(false);

		/* 字体 */
		Font font = workbook.createFont();
		font.setFontName("微软雅黑");
		font.setFontHeightInPoints((short) 12);
		font.setColor(IndexedColors.BLACK.getIndex());
		style.setFont(font);

		/* 边框 */
		// 边框使用细线
		BorderStyle borderStyle = BorderStyle.THIN;		
		// 边框颜色为黑色
		short borderColor = IndexedColors.BLACK.getIndex();
		style.setBorderTop(borderStyle);
		style.setTopBorderColor(borderColor);
		style.setBorderRight(borderStyle);
		style.setRightBorderColor(borderColor);
		style.setBorderBottom(borderStyle);
		style.setBottomBorderColor(borderColor);
		style.setBorderLeft(borderStyle);
		style.setLeftBorderColor(borderColor);

		/* 填充 */

		return style;
	}

	/**
	 * 自动判断类型并为单元格添加值，不支持日期格式
	 * @param cell 单元格对象
	 * @param value 单元格的值
	 */
	public void setCellValueByAutoClass(Cell cell, Object value) {
		
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Double) {
			cell.setCellValue((Double) value);
		} else if (value instanceof Float) {
			cell.setCellValue((Float) value);
		} else if (value instanceof Long) {
			cell.setCellValue((Long) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else {
			cell.setCellValue((String) value);
		}
	}
}
