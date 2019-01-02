package com.cxf.test.thread.daemon;

public class Consumer extends Thread{
	@Override
	public void run() {
		System.out.println("ing...");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("end...");
	}
}
