package com.med.exception;

/**
 * 自定义数据无效异常
 * @author Administrator
 * @version 2018/1/17
 */
public class DataInvalidException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6357335267307175033L;

	/**
	 * 构造一个基本异常
	 */
	public DataInvalidException() {
		super("Data is not Invalid in application.");
	}
	
	/**
	 * 构造一个基本异常
	 * @param message 信息描述
	 */
	public DataInvalidException(String message) {
		super(message);
	}
	
	/**
	 * 构造一个基本异常
	 * @param message 错误编码
	 * @param cause 根异常类
	 */
	public DataInvalidException(String message, Throwable cause) {
		super(message, cause);
	}
}
