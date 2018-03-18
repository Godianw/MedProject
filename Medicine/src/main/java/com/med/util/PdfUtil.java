package com.med.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.med.web.DataInvalidException;

/**
 * pdf工具类
 * @author Runtime
 * @date 2018/1/17
 * @version v1.0
 */
public class PdfUtil {

	// 文件名
	private String fileName;
	// 表格标题
	private String tableTitle;
	// 列名
	private String[] columnName;
	// 表格数据
	private List<List> dataList = new ArrayList<List>();
	
	/**
	 * 构造器
	 * @param fileName
	 * @param tableTitle
	 * @param columnName
	 * @param dataList
	 */
	public PdfUtil(String fileName, String tableTitle, 
			String[] columnName, List<List> dataList) {
		this.fileName = fileName;
		this.tableTitle = tableTitle;
		this.columnName = columnName;
		this.dataList = dataList;
	}
	
	/**
	 * 将pdf写入到输出流中
	 * @param out
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void writeOutPdf(OutputStream out) throws IOException {
		
		// 导出pdf之前先检查表格数据的有效性
		checkDataBrforeExport();
		
		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(out));
		// 构建文档对象
		Document doc = new Document(pdfDoc, PageSize.A3);	
		// 中文字体
		PdfFont font = PdfFontFactory.createFont(
				"c://windows//fonts//simsun.ttc,1",
				PdfEncodings.IDENTITY_H, false);
		
		// 创建表格
		Table table = new Table(columnName.length)
			.setWidth(UnitValue.createPercentValue(100));
		
		// 创建表格标题并居中
		doc.add(new Paragraph(tableTitle).setFont(font)
				.setFontSize(14).setBold()
				.setTextAlignment(TextAlignment.CENTER));
		
		// 创建表头
		for (int i = 0; i < columnName.length; ++ i) {
			table.addCell(new Cell().add(
							new Paragraph(columnName[i]).setFont(font)
							.setFontSize(12).setBold()
							.setTextAlignment(TextAlignment.CENTER))
						);
		}
		
		// 添加表格内容
		for (List<Object> singleData : dataList) {
			for (Object obj : singleData) {
				if (obj == null)
					obj = "";
				table.addCell(new Cell().add(
						new Paragraph(obj.toString()).setFont(font)
						.setFontSize(12)
						.setTextAlignment(TextAlignment.CENTER))
					);
			}
		}
		
		// 将表格添加到文档中
		doc.add(table.setHorizontalAlignment(HorizontalAlignment.CENTER));
		
		if (doc != null) 
			doc.close();
		if (out != null) 
			out.close();
	}
	
	/**
	 * 在导出pdf之前检查数据是否有效
	 */
	protected void checkDataBrforeExport() {

		if (columnName.length <= 0)
			throw new DataInvalidException("columnName is not filled");
		if (dataList.size() <= 0)
			throw new DataInvalidException("dataList is not filled");
	}
}
