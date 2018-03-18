package com.med.util;

import java.util.HashMap;
import java.util.Map;

import com.med.entity.DTRequestParam;

/**
 * 封装数据的工具类
 * @author Runtime
 * @date 2018/1/18
 * @version v1.0
 */
public class DataEncapUtil {

	/**
	 * 生成封装返回DataTables表格数据的集合
	 * @param param 表格请求参数
	 * @param recordsTotal 总记录数
	 * @param pageTotal 总页数
	 * @param data 该页的数据
	 * @return 封装结果
	 */
	public static Map<String, Object> createDTResponse (
			DTRequestParam param, Integer recordsTotal,
			Integer pageTotal, Object data) {
		Integer draw = param.getDraw();
		Integer recordsFiltered = param.getLength();
		Integer pageNum = param.getPageNum();
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("draw", draw);
		dataMap.put("recordsTotal", recordsTotal);
		dataMap.put("recordsFiltered", recordsFiltered);
		dataMap.put("pageNum", pageNum);
		dataMap.put("pageTotal", pageTotal);	
		dataMap.put("data", data);
		
		return dataMap;
	}
	
	/**
	 * 计算总页数
	 * @param recordsTotal 总记录数
	 * @param pageSize 页面尺寸
	 * @return
	 */
	public static int getPageTotal(int recordsTotal, int pageSize) {
		return (recordsTotal / pageSize) + 
				((recordsTotal % pageSize) == 0 ? 0 : 1);
	}
}
