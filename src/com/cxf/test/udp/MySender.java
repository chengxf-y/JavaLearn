package com.cxf.test.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MySender {
	public static void main(String[] args) throws IOException, InterruptedException {
		DatagramSocket ds = new DatagramSocket(8888);
		int i = 0;
		while (true) {
			byte[] buf = ("hello world" +i).getBytes();
			DatagramPacket pack = new DatagramPacket(buf, buf.length);
			
			InetSocketAddress addr = new InetSocketAddress("192.168.160.255", 9999);
			pack.setSocketAddress(addr);
			ds.send(pack);
			i++;
			Thread.sleep(300);
		}
	}
}
