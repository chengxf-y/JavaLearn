package com.cxf.test.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Util {
	public static byte[] int2Bytes(int i) {
		byte[] bytes = new byte[4];
		bytes[0] = (byte) (i >> 0);
		bytes[1] = (byte) (i >> 8);
		bytes[2] = (byte) (i >> 16);
		bytes[3] = (byte) (i >> 24);
		return bytes;
	}
	
	public static byte[] long2Bytes(long i) {
		byte[] bytes = new byte[8];
		bytes[0] = (byte) (i >> 0);
		bytes[1] = (byte) (i >> 8);
		bytes[2] = (byte) (i >> 16);
		bytes[3] = (byte) (i >> 24);
		bytes[4] = (byte) (i >> 32);
		bytes[5] = (byte) (i >> 40);
		bytes[6] = (byte) (i >> 48);
		bytes[7] = (byte) (i >> 56);
		return bytes;
	}
	
	public static int bytes2int(byte[] bytes){
		return ((bytes[0] & 0xff) << 0)
				| ((bytes[1] & 0xff) << 8)
				| ((bytes[2] & 0xff) << 16)
				| ((bytes[3] & 0xff) << 24);
	}
	
	public static int bytes2int(byte[] bytes,int offset){
		return ((bytes[0 + offset] & 0xff) << 0)
				| ((bytes[1 + offset] & 0xff) << 8)
				| ((bytes[2 + offset] & 0xff) << 16)
				| ((bytes[3 + offset] & 0xff) << 24);
	}
	
	public static long bytes2long(byte[] bytes){
		return ((bytes[0] & 0xffL) << 0)
				| ((bytes[1] & 0xffL) << 8)
				| ((bytes[2] & 0xffL) << 16)
				| ((bytes[3] & 0xffL) << 24)
				| ((bytes[4] & 0xffL) << 32)
				| ((bytes[5] & 0xffL) << 40)
				| ((bytes[6] & 0xffL) << 48)
				| ((bytes[7] & 0xffL) << 56);
	}
	
	/**
	 * 通过套接字Socet获取远程的地址
	 * @param socket 套接字
	 * @return String 地址，形式为ip：port
	 */
	public static String getSocketAddr(Socket socket){
		String ip = socket.getInetAddress().getHostAddress();
		int port = socket.getPort();
		return ip + ":" + port;
	}
	
	/**
	 * 串行化对象
	 */
	public static byte[] serializeObiect(Object obj){
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			oos.close();
			baos.close();
			return baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 反串行化对象
	 */
	public static Object deserializeObiect(byte[] bytes){
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			Object obj = ois.readObject();
			ois.close();
			bais.close();
			return obj;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
