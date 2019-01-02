package com.cxf.test.thread.sale;

public class TicketPool {
	private int count = 100;
	
	public int getTicket(){
		synchronized (this) {
			if (count > 0) {
				int tmp = count;
				count--;
				return tmp;
			}
			return 0;
		}
	}
}
