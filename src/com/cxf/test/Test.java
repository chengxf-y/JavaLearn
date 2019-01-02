package com.cxf.test;

public class Test {
	public static void main(String[] args) {
		int i = 128;
		byte b = (byte) i;
		byte c = -21;
		int d = c >> 1;
		int y = c;
		System.out.println(y & 0x000000ff);
	}
}
