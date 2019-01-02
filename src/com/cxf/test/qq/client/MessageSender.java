package com.cxf.test.qq.client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 
 *client发送消息给server
 */
public class MessageSender {
	
	private Socket socket;
	
	public MessageSender(Socket socket) {
		this.socket = socket;
	}
	
	public Socket getSocket() {
		return socket;
	}

	/**
	 * 发送消息
	 */
	public void sendMessage(byte[] bytes){
		try {
			OutputStream out = socket.getOutputStream();
			out.write(bytes);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
