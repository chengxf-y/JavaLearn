package com.cxf.test.screenbroadcast;

public class StudentMain {
	public static void main(String[] args) {
		StudentUI su = new StudentUI();
		new ReceiveDataThread(su).start();
	}
}
