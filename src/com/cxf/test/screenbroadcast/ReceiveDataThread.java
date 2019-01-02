package com.cxf.test.screenbroadcast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipInputStream;

import com.cxf.test.util.Util;

public class ReceiveDataThread extends Thread{
	
	private StudentUI su;
	private Map<Integer, FrameUnit> unitMap = new HashMap<Integer, FrameUnit>();
	
	public ReceiveDataThread(StudentUI su) {
		this.su = su;
	}
	
	@Override
	public void run() {
		try {
			System.out.println("receive data");
			DatagramSocket sock = new DatagramSocket(9999);
			System.out.println("=====");
			byte[] buf = new byte[14 + 60 * 1024];
			DatagramPacket pack = new DatagramPacket(buf, buf.length);
			while (true) {
				sock.receive(pack);
				FrameUnit unit = parseFrameUnit(buf);
				if (unitMap.isEmpty()) {
					unitMap.put(unit.getIndex(), unit);
				}else {
					long oldTs = unitMap.values().iterator().next().getTimestamp();
					long newTs = unit.getTimestamp();
					if (newTs < oldTs) {
						continue;
					}else if (newTs == oldTs) {
						unitMap.put(unit.getIndex(), unit);
					}else {
						unitMap.clear();
						unitMap.put(unit.getIndex(), unit);
					}
				}
				processOneScreen();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void processOneScreen() {
		int count = unitMap.values().iterator().next().getCount();
		int size = unitMap.size();
		if (count == size) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			for (int i = 0; i < count; i++) {
				FrameUnit unit = unitMap.get(i);
				try {
					baos.write(unit.getUnitData());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			byte[] screenBytes = baos.toByteArray();
			//½âÑ¹Ëõ
			screenBytes = unzipOneScreen(screenBytes);
			unitMap.clear();
			su.updataIcon(screenBytes);
		}
	}

	private byte[] unzipOneScreen(byte[] screenBytes) {
		ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(screenBytes));
		try {
			zis.getNextEntry();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int len = -1;
			int leng = 0;
			byte[] buf = new byte[1024];
			System.out.println("%%%%%%%%%%%%%%%%%");
			while (true) {
				try{
					len = zis.read(buf);
					leng = len + leng;
					System.out.println("len == " + leng);
				}catch (EOFException ex){
					
				}
				if (len != -1) {
					baos.write(buf, 0, len);
				}else {
					break;
				}
			}
//			int i = 0;
//			while ((len = zis.read(buf)) != -1) {
//				baos.write(buf, 0, len);
//				System.out.println(i);
//				i++;
//			}
			return baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private FrameUnit parseFrameUnit(byte[] buf) {
		FrameUnit unit = new FrameUnit();
		long timestamp = Util.bytes2long(buf);
		unit.setTimestamp(timestamp);
		System.out.println("buf size = " + buf.length);
		System.out.println("index  ==> " + buf[8]);
		unit.setIndex(buf[8]);
		unit.setCount(buf[9]);
		System.out.println("@@@@");
		System.out.println(buf[10]);
		int len = Util.bytes2int(buf, 10);
		System.out.println("len   ==>"  + len);
		unit.setLength(len);
		byte[] data = new byte[len];
		System.arraycopy(buf, 14, data, 0, len);
		unit.setUnitData(data);
		return unit;
	}
}
