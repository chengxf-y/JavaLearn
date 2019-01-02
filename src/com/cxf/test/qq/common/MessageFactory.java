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
			//读取消息类型
			int mesType = in.read();
			switch (mesType) {
			//server端解析
			case BaseMessage.CLIENT_TO_SERVER_CHATS:{
				
				ServerChatsMessage msg = new ServerChatsMessage();
				//获取发送者地址
				msg.setSenderAddr(Util.getSocketAddr(socket));
				//读取消息长度
				byte[] msgLengthBytes = new byte[4];
				in.read(msgLengthBytes);
				//读取消息内容
				byte[] rawMessage = new byte[Util.bytes2int(msgLengthBytes)];
				in.read(rawMessage);
				msg.setRawMessage(rawMessage);
				return msg;
			}
			case BaseMessage.CLIENT_TO_SERVER_CHAT:{
				ServerChatMessage msg = new ServerChatMessage();
				// 获取发送者地址长度
				msg.setSenderAddr(Util.getSocketAddr(socket));
				// 获取接受者地址长度
				int senderAddrLength = in.read();
				// 获取接受者地址
				byte[] senderAddr = new byte[senderAddrLength];
				in.read(senderAddr);
				msg.setRecvInfo(new String(senderAddr));
				// 获取消息长度
				byte[] msgLengthBytes = new byte[4];
				in.read(msgLengthBytes);
				// 获取消息长度
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
			//client端解析
			case BaseMessage.SERVER_TO_CLIENT_CHATS:{
				ServerChatsMessage msg = new ServerChatsMessage();
				//获取发送者地址长度，读取发送者
				int senderAddrLength = in.read();
				byte[] senderAddr = new byte[senderAddrLength];
				in.read(senderAddr);
				msg.setSenderAddr(new String(senderAddr));
				//获取消息长度
				byte[] msgLengthBytes = new byte[4];
				in.read(msgLengthBytes);
				//读取消息内容
				byte[] rawMessage = new byte[Util.bytes2int(msgLengthBytes)];
				in.read(rawMessage);
				msg.setRawMessage(rawMessage);
				return msg;
			}
			case BaseMessage.SERVER_TO_CLIENT_CHAT:{
				ServerChatMessage msg = new ServerChatMessage();
				//获取发送者地址长度，读取发送者
				int senderAddrLength = in.read();
				byte[] senderAddr = new byte[senderAddrLength];
				in.read(senderAddr);
				msg.setSenderAddr(new String(senderAddr));
				//获取消息长度
				byte[] msgLengthBytes = new byte[4];
				in.read(msgLengthBytes);
				//读取消息内容
				byte[] rawMessage = new byte[Util.bytes2int(msgLengthBytes)];
				in.read(rawMessage);
				msg.setRawMessage(rawMessage);
				return msg;
			}
			//刷新好友列表
			case BaseMessage.SERVER_TO_CLIENT_REFRESH_FRIENT_LIST:{
				ServerRefreshFriendMessage msg = new ServerRefreshFriendMessage();
				//获取好友列表长度
				byte[] friendListLengthBytes = new byte[4];
				in.read(friendListLengthBytes);
				//获取好友列表list
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
