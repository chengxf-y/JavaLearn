package com.cxf.test.qq.server;

import java.net.Socket;
import java.util.Map;

import com.cxf.test.qq.common.BaseMessage;
import com.cxf.test.qq.common.MessageFactory;
import com.cxf.test.util.Util;

/**
 * 
 * Server��clientͨ���߳���
 * @author cxf
 * 
 */
public class ServerCommunicationThread extends Thread{
	
	private Socket sock;

	public ServerCommunicationThread(Socket sock) {
		this.sock = sock;
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				BaseMessage msg = MessageFactory.parseMessage(sock);
				switch (msg.getMessageType()) {
				case BaseMessage.SERVER_TO_CLIENT_CHATS:{
					ServerChatsMessage chatsMessage = (ServerChatsMessage) msg;
					QQServer.getInstance().broadcastMessage(chatsMessage.encodeMessage());
					break;
				}
				case BaseMessage.SERVER_TO_CLIENT_REFRESH_FRIENT_LIST:{
					ServerRefreshFriendMessage refreshMessage = (ServerRefreshFriendMessage) msg;
					QQServer.getInstance().sendMessageToclient(sock, refreshMessage.encodeMessage());
					break;
				}
				case BaseMessage.SERVER_TO_CLIENT_CHAT:{
					ServerChatMessage chatMessage = (ServerChatMessage) msg;
					Map<String, Socket> clients = QQServer.getInstance().getClients();
					String recvInfo = chatMessage.getRecvInfo();
					Socket recvSocket = clients.get(recvInfo);
					QQServer.getInstance().sendMessageToclient(recvSocket, chatMessage.encodeMessage());
					break;
				}
				default:
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			//socketͨ�ų����⣬�Ƴ���ǰsocket
			QQServer.getInstance().removeClient(Util.getSocketAddr(sock));
			//�Զ�ˢ������client�ĺ����б�
			QQServer.getInstance().broadcastFriendList();
		}
		
	}
}
