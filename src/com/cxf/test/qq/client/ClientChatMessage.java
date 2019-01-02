package com.cxf.test.qq.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.cxf.test.qq.common.BaseMessage;
import com.cxf.test.util.Util;

public class ClientChatMessage  extends BaseMessage{

	// 消息内容
	private String message;
	
	// 接受者地址
	private String recvInfo;

	public String getRecvInfo() {
		return recvInfo;
	}

	public void setRecvInfo(String recvInfo) {
		this.recvInfo = recvInfo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public int getMessageType() {
		return CLIENT_TO_SERVER_CHAT;
	}

	// 对消息进行编码，以便于对消息进行网络传输
	@Override
	public byte[] encodeMessage() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			// 1.写入消息类型（1字节）
			baos.write(getMessageType());
			// 2.写入接受者地址长度（1字节）
			byte[] recvAddr = recvInfo.getBytes();
			baos.write(recvAddr.length);
			// 3.写入接受者地址
			baos.write(recvAddr);
			// 4.写入信息长度（4字节）
			byte[] mesBytes = message.getBytes();
			baos.write(Util.int2Bytes(mesBytes.length));
			// 5.写入信息内容
			baos.write(mesBytes);
			baos.close();
			return baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
