package com.cxf.test.exception;

public class MyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MyException() {
		System.out.println("�Զ����쳣2��serialVersionUIDΪ��" + serialVersionUID);
	}
	
	public MyException(String message) {
		System.out.println("�Զ����쳣��serialVersionUIDΪ��" + serialVersionUID + "\n" + message);
	}
}

