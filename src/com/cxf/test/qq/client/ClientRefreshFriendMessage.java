package com.cxf.test.qq.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.cxf.test.qq.common.BaseMessage;
import com.cxf.test.util.Util;

/**
 * client发送给server的刷新好友列表消息
 *
 */
public class ClientRefreshFriendMessage extends BaseMessage{
	
	@Override
	public int getMessageType() {
		return CLIENT_TO_SERVER_REFRESH_FRIENT_LIST;
	}

	//对消息进行编码，以便于对消息进行网络传输
	@Override
	public byte[] encodeMessage() {
		byte[] bytes = new byte[1];
		bytes[0] = CLIENT_TO_SERVER_REFRESH_FRIENT_LIST;
		return bytes;
	}
}
