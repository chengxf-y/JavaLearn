package com.cxf.test.qq.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import com.cxf.test.qq.common.BaseMessage;
import com.cxf.test.util.Util;

/**
 * client发送给server的刷新好友列表消息
 *
 */
public class ServerRefreshFriendMessage extends BaseMessage{
	
	//消息内容
	private List<String> friends;
	
	

	public List<String> getFriends() {
		return friends;
	}

	public void setFriends(List<String> friends) {
		this.friends = friends;
	}

	@Override
	public int getMessageType() {
		return SERVER_TO_CLIENT_REFRESH_FRIENT_LIST;
	}

	//对消息进行编码，以便于对消息进行网络传输
	@Override
	public byte[] encodeMessage() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			//1.写入消息类型
			baos.write(getMessageType());
			//2.写入消息长度
			byte[] mesBytes = Util.serializeObiect(friends);
			baos.write(Util.int2Bytes(mesBytes.length));
			//写入消息内容
			baos.write(mesBytes);
			baos.close();
			return baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
