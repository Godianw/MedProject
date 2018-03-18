package com.med.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 封装了DataTables服务器模式发送的参数
 * @author Runtime
 * @date 2018/1/1
 * @version V1.0
 */
public class DTRequestParam {
	
	private Integer draw;   // 绘制计数器
	private Integer start;  // 是指数据的当前列表的起始位置，从0开始
    private Integer length; // 当前页展现的数据行数 
    private Integer pageNum;		// 当前页数
    private String condition; 		// 查询条件
    private Map<Search, String> search = new HashMap<Search, String>();  
    private List<Map<Order, String>> order = new ArrayList<Map<Order, String>>();  
    private List<Map<Column, String>> columns = new ArrayList<Map<Column, String>>();  
    
	public enum Search {  
        value,  
        regex  
    }  
  
    public enum Order {  
        column,  
        dir  
    }  
  
    public enum Column {  
        data,  
        name,  
        searchable,  
        orderable,  
        searchValue,  
        searchRegex  
    }
    
    public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public Map<Search, String> getSearch() {
		return search;
	}

	public void setSearch(Map<Search, String> search) {
		this.search = search;
	}

	public List<Map<Order, String>> getOrder() {
		return order;
	}

	public void setOrder(List<Map<Order, String>> order) {
		this.order = order;
	}

	public List<Map<Column, String>> getColumns() {
		return columns;
	}

	public void setColumns(List<Map<Column, String>> columns) {
		this.columns = columns;
	}  
	
	/**
	 * 返回当前页的首个记录索引
	 * @return
	 */
	public int getCurPageStartIndex() {
		return (this.getPageNum() - 1) * this.getLength();
	}
	
	/**
	 * 获取筛选部分和排序部分的sql（ORDER BY开头）
	 * @return sql语句
	 */
    public String getConditionSql() {
    	
    	StringBuilder conditionSql = new StringBuilder("");
    	
    	// 若筛选条件不为空则添加筛选条件
    	if (condition != null && !condition.isEmpty()) {
    		conditionSql.append(condition + " ");
    	}
    	
    	// 获取所有进行排序的列
    	int orderLength = getOrder().size();
    	if (orderLength > 0) {		// 至少有一列进行了排序
    		conditionSql.append("ORDER BY");
    		for (int i = 0; i < orderLength; ++ i) {
				if (i > 0)
					conditionSql.append(",");
				// 获取列的索引
				Integer index = Integer.valueOf(
						getOrder().get(i).get(Order.column));
				// 获取列名
				conditionSql.append(" " 
						+ getColumns().get(index).get(Column.data));
				// 获取排序方式（ASC或DESC）
				conditionSql.append(" " 
						+ getOrder().get(i).get(Order.dir));
    		}
    	}
    	
    	return conditionSql.toString();
    }
}
