package com.cxf.test.qq.client;

import java.net.Socket;

import com.cxf.test.qq.common.BaseMessage;
import com.cxf.test.qq.common.MessageFactory;
import com.cxf.test.qq.server.ServerChatMessage;
import com.cxf.test.qq.server.ServerChatsMessage;
import com.cxf.test.qq.server.ServerRefreshFriendMessage;

/**
 * 
 * Client和Server通信线程，主要负责接收消息
 * @author cxf
 * 
 */
public class ClientCommunicationThread extends Thread{
	
	private Socket sock;
	private QQClientChatsUI ui;

	public ClientCommunicationThread(Socket sock) {
		this.sock = sock;
	}
	
	public ClientCommunicationThread(Socket sock, QQClientChatsUI ui) {
		this.sock = sock;
		this.ui = ui;
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				BaseMessage msg = MessageFactory.parseMessage(sock);
				switch (msg.getMessageType()) {
				case BaseMessage.SERVER_TO_CLIENT_CHATS:{
					ServerChatsMessage chatsMessage = (ServerChatsMessage) msg;
					String[] ss = new String[2];
					ss[0] = chatsMessage.getSenderAddr();
					ss[1] = new String(chatsMessage.getRawMessage());
					ui.updateHistory(ss);
					break;
				}
				case BaseMessage.SERVER_TO_CLIENT_CHAT:{
					ServerChatMessage chatsMessage = (ServerChatMessage) msg;
					String senderAddr = chatsMessage.getSenderAddr();
					QQClientChatsSingleUI chatui = ui.recvMessageUI(senderAddr);
					String[] ss = new String[2];
					ss[0] = chatsMessage.getSenderAddr();
					ss[1] = new String(chatsMessage.getRawMessage());
					chatui.updateHistory(ss);
					break;
				}
				//服务器刷新好友列表
				case BaseMessage.SERVER_TO_CLIENT_REFRESH_FRIENT_LIST:
					ServerRefreshFriendMessage refreshFriendMessage = (ServerRefreshFriendMessage) msg;
					ui.refreshFriendList(refreshFriendMessage.getFriends());
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
