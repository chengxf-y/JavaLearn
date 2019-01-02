package com.cxf.test.thread.sale;

public class Saler extends Thread {
	
	private static Object locak =  new Object();
	private static int count = 100;
	private String name;
	
	public Saler(String name) {
		this.name = name;
	}
	
	@Override
	public void run() {
		while (count >0) {
			//同步代码块
			synchronized (locak) {
				System.out.println(name + "  正在售卖     ==>  " + count);
				count--;
			}
			yield();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
