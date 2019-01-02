package com.cxf.test.thread.sale;

public class Saler2 extends Thread {
	
	private TicketPool pool;
	private String name;
	private int numTicket;
	
	public Saler2(String name, TicketPool pool) {
		this.name = name;
		this.pool = pool;
	}
	@Override
	public void run() {
		while ((numTicket = pool.getTicket()) != 0) {
			System.out.println(name + "  ÕıÔÚÊÛÂô ==>  " + numTicket);
		}
	}
}
