package com.cxf.test.qq.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cxf.test.util.Util;

public class QQServer {
	//好友列表
	private  Map<String, Socket> clients = new HashMap<String, Socket>();
	private static QQServer instance = new QQServer();
	
	
	public static QQServer getInstance() {
		return instance;
	}

	public void start() throws IOException {
		ServerSocket ss = new ServerSocket(8888);
		//接受客户端连接
		while (true) {
			Socket sock = ss.accept();
			clients.put(Util.getSocketAddr(sock), sock);
			broadcastFriendList();
			new ServerCommunicationThread(sock).start();
			
		}
	}
	
	public Map<String, Socket> getClients() {
		return clients;
	}

	public void broadcastFriendList() {
		ServerRefreshFriendMessage msg = new ServerRefreshFriendMessage();
		List<String> list = new ArrayList<String>();
		for ( String key : clients.keySet()) {
			list.add(key);
		}
		//msg.setFriends((List<String>) clients.keySet());
		msg.setFriends(list);
		broadcastMessage(msg.encodeMessage());
	}

	/**
	 * 群发消息
	 */
	public  void broadcastMessage(byte[] msgBytes) {
			try {
				for (Socket socket : clients.values()) {
					OutputStream out = socket.getOutputStream();
					out.write(msgBytes);
					out.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public void removeClient(String key){
		clients.remove(key);
	}
	
	public List<String> getFriendList(){
		return new ArrayList<String>(clients.keySet());
	}
	
	public  void sendMessageToclient(Socket socket, byte[] msgBytes) {
		try {
			OutputStream out = socket.getOutputStream();
			out.write(msgBytes);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
