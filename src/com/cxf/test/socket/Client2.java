package com.cxf.test.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client2 {
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		Socket socket = new Socket("localhost", 8888);
		new Thread(){
			public void run() {
				while (true) {					
					InputStream in;
					try {
						in = socket.getInputStream();
						BufferedReader br = new BufferedReader(new InputStreamReader(in));
						
						String line = null;
						while ((line = br.readLine()) != null) {
							System.out.println(line);			
						}			
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			};
		}.start();
		OutputStream os = socket.getOutputStream();
		int i = 0;
		while (true) {
			os.write((i + "haha\r\n").getBytes());
			i++;
			Thread.sleep(500);
		}
	}
}
