package com.cxf.test.exception;

public class MyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MyException() {
		System.out.println("自定义异常2，serialVersionUID为：" + serialVersionUID);
	}
	
	public MyException(String message) {
		System.out.println("自定义异常，serialVersionUID为：" + serialVersionUID + "\n" + message);
	}
}

