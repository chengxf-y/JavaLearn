package com.cxf.test.thread.daemon;

public class App {
	public static void main(String[] args) {
		Consumer t1 = new Consumer();
		Waiter w = new Waiter();
		w.setDaemon(true);
		t1.start();
		w.start();
	}
}
