package com.cxf.test.qq.common;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.List;

import com.cxf.test.qq.server.QQServer;
import com.cxf.test.qq.server.ServerChatMessage;
import com.cxf.test.qq.server.ServerChatsMessage;
import com.cxf.test.qq.server.ServerRefreshFriendMessage;
import com.cxf.test.util.Util;
public class MessageFactory {
	
	public static BaseMessage parseMessage(Socket socket){
		try {
			InputStream in = socket.getInputStream();
			//��ȡ��Ϣ����
			int mesType = in.read();
			switch (mesType) {
			//server�˽���
			case BaseMessage.CLIENT_TO_SERVER_CHATS:{
				
				ServerChatsMessage msg = new ServerChatsMessage();
				//��ȡ�����ߵ�ַ
				msg.setSenderAddr(Util.getSocketAddr(socket));
				//��ȡ��Ϣ����
				byte[] msgLengthBytes = new byte[4];
				in.read(msgLengthBytes);
				//��ȡ��Ϣ����
				byte[] rawMessage = new byte[Util.bytes2int(msgLengthBytes)];
				in.read(rawMessage);
				msg.setRawMessage(rawMessage);
				return msg;
			}
			case BaseMessage.CLIENT_TO_SERVER_CHAT:{
				ServerChatMessage msg = new ServerChatMessage();
				// ��ȡ�����ߵ�ַ����
				msg.setSenderAddr(Util.getSocketAddr(socket));
				// ��ȡ�����ߵ�ַ����
				int senderAddrLength = in.read();
				// ��ȡ�����ߵ�ַ
				byte[] senderAddr = new byte[senderAddrLength];
				in.read(senderAddr);
				msg.setRecvInfo(new String(senderAddr));
				// ��ȡ��Ϣ����
				byte[] msgLengthBytes = new byte[4];
				in.read(msgLengthBytes);
				// ��ȡ��Ϣ����
				byte[] rawMessage = new byte[Util.bytes2int(msgLengthBytes)];
				in.read(rawMessage);
				msg.setRawMessage(rawMessage);
				return msg;
			}
			case BaseMessage.CLIENT_TO_SERVER_REFRESH_FRIENT_LIST:{
				ServerRefreshFriendMessage msg = new ServerRefreshFriendMessage();
				msg.setFriends(QQServer.getInstance().getFriendList());
				return msg;
			}
			//client�˽���
			case BaseMessage.SERVER_TO_CLIENT_CHATS:{
				ServerChatsMessage msg = new ServerChatsMessage();
				//��ȡ�����ߵ�ַ���ȣ���ȡ������
				int senderAddrLength = in.read();
				byte[] senderAddr = new byte[senderAddrLength];
				in.read(senderAddr);
				msg.setSenderAddr(new String(senderAddr));
				//��ȡ��Ϣ����
				byte[] msgLengthBytes = new byte[4];
				in.read(msgLengthBytes);
				//��ȡ��Ϣ����
				byte[] rawMessage = new byte[Util.bytes2int(msgLengthBytes)];
				in.read(rawMessage);
				msg.setRawMessage(rawMessage);
				return msg;
			}
			case BaseMessage.SERVER_TO_CLIENT_CHAT:{
				ServerChatMessage msg = new ServerChatMessage();
				//��ȡ�����ߵ�ַ���ȣ���ȡ������
				int senderAddrLength = in.read();
				byte[] senderAddr = new byte[senderAddrLength];
				in.read(senderAddr);
				msg.setSenderAddr(new String(senderAddr));
				//��ȡ��Ϣ����
				byte[] msgLengthBytes = new byte[4];
				in.read(msgLengthBytes);
				//��ȡ��Ϣ����
				byte[] rawMessage = new byte[Util.bytes2int(msgLengthBytes)];
				in.read(rawMessage);
				msg.setRawMessage(rawMessage);
				return msg;
			}
			//ˢ�º����б�
			case BaseMessage.SERVER_TO_CLIENT_REFRESH_FRIENT_LIST:{
				ServerRefreshFriendMessage msg = new ServerRefreshFriendMessage();
				//��ȡ�����б���
				byte[] friendListLengthBytes = new byte[4];
				in.read(friendListLengthBytes);
				//��ȡ�����б�list
				byte[] friendListBytes = new byte[Util.bytes2int(friendListLengthBytes)];
				in.read(friendListBytes);
				msg.setFriends((List<String>)Util.deserializeObiect(friendListBytes));
				return msg;
			}
			default:
				break;
			}
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
