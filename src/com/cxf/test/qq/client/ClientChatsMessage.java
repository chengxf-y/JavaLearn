package com.cxf.test.qq.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.cxf.test.qq.common.BaseMessage;
import com.cxf.test.util.Util;

/**
 * client���͸�server��Ⱥ����Ϣ
 *
 */
public class ClientChatsMessage extends BaseMessage{
	
	//��Ϣ����
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

	//����Ϣ���б��룬�Ա��ڶ���Ϣ�������紫��
	@Override
	public byte[] encodeMessage() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			//1.д����Ϣ����
			baos.write(getMessageType());
			//2.д����Ϣ����
			byte[] mesBytes = message.getBytes();
			baos.write(Util.int2Bytes(mesBytes.length));
			//д����Ϣ����
			baos.write(mesBytes);
			baos.close();
			return baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
