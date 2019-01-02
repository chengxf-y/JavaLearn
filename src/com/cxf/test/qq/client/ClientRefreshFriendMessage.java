package com.cxf.test.qq.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.cxf.test.qq.common.BaseMessage;
import com.cxf.test.util.Util;

/**
 * client���͸�server��ˢ�º����б���Ϣ
 *
 */
public class ClientRefreshFriendMessage extends BaseMessage{
	
	@Override
	public int getMessageType() {
		return CLIENT_TO_SERVER_REFRESH_FRIENT_LIST;
	}

	//����Ϣ���б��룬�Ա��ڶ���Ϣ�������紫��
	@Override
	public byte[] encodeMessage() {
		byte[] bytes = new byte[1];
		bytes[0] = CLIENT_TO_SERVER_REFRESH_FRIENT_LIST;
		return bytes;
	}
}
