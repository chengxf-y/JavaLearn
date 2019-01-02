package com.cxf.test.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MyReceiver {
	public static void main(String[] args) throws IOException {
		DatagramSocket ds = new DatagramSocket(9999);
		
		byte[] buf = new byte[64 * 1024];
		DatagramPacket pack = new DatagramPacket(buf, buf.length);
		while (true) {
			ds.receive(pack);
			int len = pack.getLength();
			System.out.println(new String(buf, 0, len));			
		}
		
	}

}
