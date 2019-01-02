package com.cxf.test.thread.daemon;

import java.util.Date;

public class Waiter extends Thread{
	@Override
	public void run() {
		while (true) {
			System.out.println(new Date());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
