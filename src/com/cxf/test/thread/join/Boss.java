package com.cxf.test.thread.join;

public class Boss {
	public static void main(String[] args) throws InterruptedException {
		Player p1 = new Player("1", 2000);
		Player p2 = new Player("2", 6000);
		Player p3 = new Player("3", 3000);
		Player p4 = new Player("4", 5000);
		
		p1.start();
		p2.start();
		p3.start();
		p4.start();
		
		p4.join();
		p1.join();
		p2.join();
		p3.join();
		System.out.println("end...");
	}
}
