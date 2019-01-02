package com.cxf.test.thread;

public class MyThread extends Thread{
	
	private String name;
	
	public MyThread(String name) {
		this.name = name;
	}
	
	/**
	 * 定义线程
	 * */
	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.println(name + "==>" + i);
			//放弃当前CPU的执行权
			yield();
		}
	}
}
