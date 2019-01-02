package com.cxf.test.thread.join;

public class Player extends Thread{
	
	private String name;
	private int time;
	
	public Player(String name, int time) {
		this.name = name;
		this.time = time;
	}
	
	@Override
	public void run() {
		System.out.println(name + "==>");
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(name + "need ==>" + time);
	}
}
