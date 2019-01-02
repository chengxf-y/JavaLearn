package com.cxf.test.qq.common;

public abstract class BaseMessage {
	
	//client���͸�server����Ϣ
	public static final int CLIENT_TO_SERVER_CHATS = 1;
	public static final int CLIENT_TO_SERVER_CHAT = 2;
	public static final int CLIENT_TO_SERVER_REFRESH_FRIENT_LIST = 3;
	//server���͸�client����Ϣ
	public static final int SERVER_TO_CLIENT_CHATS = 4;
	public static final int SERVER_TO_CLIENT_CHAT = 5;
	public static final int SERVER_TO_CLIENT_REFRESH_FRIENT_LIST = 6;
	
	public abstract int getMessageType();
	
	public abstract byte[] encodeMessage();
	
}
