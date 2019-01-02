package com.cxf.test.qq.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.cxf.test.qq.common.BaseMessage;
import com.cxf.test.util.Util;

public class ClientChatMessage  extends BaseMessage{

	// ��Ϣ����
	private String message;
	
	// �����ߵ�ַ
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

	// ����Ϣ���б��룬�Ա��ڶ���Ϣ�������紫��
	@Override
	public byte[] encodeMessage() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			// 1.д����Ϣ���ͣ�1�ֽڣ�
			baos.write(getMessageType());
			// 2.д������ߵ�ַ���ȣ�1�ֽڣ�
			byte[] recvAddr = recvInfo.getBytes();
			baos.write(recvAddr.length);
			// 3.д������ߵ�ַ
			baos.write(recvAddr);
			// 4.д����Ϣ���ȣ�4�ֽڣ�
			byte[] mesBytes = message.getBytes();
			baos.write(Util.int2Bytes(mesBytes.length));
			// 5.д����Ϣ����
			baos.write(mesBytes);
			baos.close();
			return baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
