package com.cxf.test.qq.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class QQClientMain {
	public static void main(String[] args) {
		try {
			//Socket socket = new Socket("localhost", 8888);
			Socket socket = new Socket("192.168.160.21", 8888);
			//����������
			MessageSender sender = new MessageSender(socket);
			//��client UI
			QQClientChatsUI ui = new QQClientChatsUI(sender);
			ui.setVisible(true);
			//����client��serverͨ���߳�
			new ClientCommunicationThread(socket, ui).start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
