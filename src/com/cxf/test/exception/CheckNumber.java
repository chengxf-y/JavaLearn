package com.cxf.test.exception;

public class CheckNumber {
	
	public void check(int num) throws MyException{
		if (num >= 10){
			System.out.println("���ֳ���10���׳��쳣��");
			throw new MyException("�������׳��Զ����쳣");
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