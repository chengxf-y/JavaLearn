package com.cxf.test.qq.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.cxf.test.qq.common.BaseMessage;
import com.cxf.test.util.Util;

/**
 * server���͸�client��˽����Ϣ
 */
public class ServerChatMessage extends BaseMessage{

	// �����ߵ�ַ
	private String senderAddr;
	// ���͵�ԭʼ��Ϣ
	private byte[] rawMessage;
	
	// �����ߵ�ַ
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
	 * ������Ϣ
	 */
	@Override
	public byte[] encodeMessage() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			//1.д����Ϣ����
			baos.write(getMessageType());
			//2.д�뷢���ߵ�ַ����
			byte[] senderAddrBytes = senderAddr.getBytes();
			baos.write(senderAddrBytes.length);
			//3.д�뷢���ߵ�ַ
			baos.write(senderAddrBytes);
			//4.д����Ϣ����
			baos.write(Util.int2Bytes(rawMessage.length));
			//5.д����Ϣ����
			baos.write(rawMessage);
			return baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
