package com.cxf.test.thread.sale;

public class App {
	public static void main(String[] args) {
		TicketPool pool = new TicketPool();
		Saler2 s1 = new Saler2("1", pool);
		Saler2 s2 = new Saler2("2", pool);
		s1.start();
		s2.start();
	}
}
