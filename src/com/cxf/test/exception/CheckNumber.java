package com.cxf.test.exception;

public class CheckNumber {
	
	public void check(int num) throws MyException{
		if (num >= 10){
			System.out.println("数字超过10，抛出异常！");
			throw new MyException("函数内抛出自定义异常");
		}
	}
	
	public static void main(String[] args) {
		int num = 12;
		CheckNumber ck = new CheckNumber();
		try {
			ck.check(num);
		} catch (MyException e) {
			e.printStackTrace();
		}
	}
}