package com.cxf.test.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class MyServerTest {
	public static void main(String[] args) throws IOException {
		ServerSocket ss = new ServerSocket();
		ss.bind(new InetSocketAddress("127.0.0.1", 8888));
		new Thread(){
			public void run() {
				while (true) {					
					InputStream in;
					OutputStream out;
					try {
						Socket socket = ss.accept();
						in = socket.getInputStream();
						out = socket.getOutputStream();
						BufferedReader br = new BufferedReader(new InputStreamReader(in));
						
						String line = null;
						while ((line = br.readLine()) != null) {
							System.out.println(line);
							out.write(("ack ==>" + line + "\r\n").getBytes());
							out.flush();
						}			
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			};
		}.start();
	}
}
