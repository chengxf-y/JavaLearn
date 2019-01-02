package com.cxf.test.qq.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.cxf.test.qq.common.BaseMessage;
import com.cxf.test.util.Util;

/**
 * client发送给server的群聊消息
 *
 */
public class ClientChatsMessage extends BaseMessage{
	
	//消息内容
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public int getMessageType() {
		return CLIENT_TO_SERVER_CHATS;
	}

	//对消息进行编码，以便于对消息进行网络传输
	@Override
	public byte[] encodeMessage() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			//1.写入消息类型
			baos.write(getMessageType());
			//2.写入消息长度
			byte[] mesBytes = message.getBytes();
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
