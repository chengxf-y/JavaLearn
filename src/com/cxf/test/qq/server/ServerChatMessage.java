package com.cxf.test.qq.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.cxf.test.qq.common.BaseMessage;
import com.cxf.test.util.Util;

/**
 * server发送给client的私聊消息
 */
public class ServerChatMessage extends BaseMessage{

	// 发送者地址
	private String senderAddr;
	// 发送的原始消息
	private byte[] rawMessage;
	
	// 接受者地址
	private String recvInfo;
	
	public String getRecvInfo() {
		return recvInfo;
	}

	public void setRecvInfo(String recvInfo) {
		this.recvInfo = recvInfo;
	}

	public String getSenderAddr() {
		return senderAddr;
	}

	public void setSenderAddr(String senderAddr) {
		this.senderAddr = senderAddr;
	}

	public byte[] getRawMessage() {
		return rawMessage;
	}

	public void setRawMessage(byte[] rawMessage) {
		this.rawMessage = rawMessage;
	}

	@Override
	public int getMessageType() {
		return SERVER_TO_CLIENT_CHAT;
	}

	/**
	 * 编码消息
	 */
	@Override
	public byte[] encodeMessage() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			//1.写入消息类型
			baos.write(getMessageType());
			//2.写入发送者地址长度
			byte[] senderAddrBytes = senderAddr.getBytes();
			baos.write(senderAddrBytes.length);
			//3.写入发送者地址
			baos.write(senderAddrBytes);
			//4.写入消息长度
			baos.write(Util.int2Bytes(rawMessage.length));
			//5.写入消息内容
			baos.write(rawMessage);
			return baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
