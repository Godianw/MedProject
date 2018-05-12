package com.med.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.med.config.RootConfig;
import com.med.util.ExcelUtil;
import com.med.util.ExcelUtil.ExcelSuffix;
import com.med.util.PdfUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=RootConfig.class)
public class ExcelOperatorTest {
		
	@SuppressWarnings("rawtypes")
	public List<List> dataList = new ArrayList<List>();
	
	ExcelUtil excelUtil = new ExcelUtil();
	
	PdfUtil pdfUtil = new PdfUtil();
	
	@Test
	public void test() throws IOException {
		for (int i = 1; i <= 15; ++ i) {
			List<Object> singleData = new ArrayList<Object>();
			singleData.add(Integer.valueOf(i));
			singleData.add("供应商" + String.valueOf(i));
			singleData.add("联系人" + String.valueOf(i));
			singleData.add("1881842987" + i);
			singleData.add("北京");
			dataList.add(singleData);
		}
		
		File pdf = new File("E:/test.pdf");
		FileOutputStream pdfOut = new FileOutputStream(pdf);
	//	pdfUtil.writeOutPdf(pdfOut);
		
	/*	File excel = new File("E:/test.xlsx");
		FileOutputStream excelOut = new FileOutputStream(excel);
		excelUtil.writeOutExcel(excelOut);*/
	}
}
