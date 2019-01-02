package com.cxf.test.qq.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import com.cxf.test.qq.common.BaseMessage;
import com.cxf.test.util.Util;

/**
 * client���͸�server��ˢ�º����б���Ϣ
 *
 */
public class ServerRefreshFriendMessage extends BaseMessage{
	
	//��Ϣ����
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

	//����Ϣ���б��룬�Ա��ڶ���Ϣ�������紫��
	@Override
	public byte[] encodeMessage() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			//1.д����Ϣ����
			baos.write(getMessageType());
			//2.д����Ϣ����
			byte[] mesBytes = Util.serializeObiect(friends);
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
