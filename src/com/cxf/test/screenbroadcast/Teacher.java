package com.cxf.test.screenbroadcast;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

import com.cxf.test.thread.join.Boss;

public class Teacher {
	
	private DatagramSocket sock;
	private Robot robot = null;
	private Rectangle rect = new Rectangle(0, 0, 1600, 900);
	
	public void start() {
		try {
			sock = new DatagramSocket(8888);
			robot = new Robot();
			while (true) {
				broadcastOneScreen();
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	public void broadcastOneScreen() {
		//1.抓屏
		BufferedImage img = robot.createScreenCapture(rect);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(img, "jpg", baos);
			byte[] bytes = baos.toByteArray();
		//压缩数据
			bytes = zipOneScreen(bytes);
		//2.切分屏幕
			List<FrameUnit> units = splitScreen(bytes);
		//3.广播数据包
			doBroadcastOneScreen(units);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private byte[] zipOneScreen(byte[] bytes) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(baos);
		try {
			zos.putNextEntry(new ZipEntry("1"));
			zos.write(bytes);
			zos.close();
			return baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void doBroadcastOneScreen(List<FrameUnit> units) {
		for (FrameUnit unit : units) {
			broadcastOneUnit(unit);
		}
	}

	public void broadcastOneUnit(FrameUnit unit) {
		byte[] data = unit.toBytes();
		DatagramPacket pack = new DatagramPacket(data, data.length);
		InetSocketAddress addr = new InetSocketAddress("192.168.160.255", 9999);
		pack.setSocketAddress(addr);
		try {
			
			sock.send(pack);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<FrameUnit> splitScreen(byte[] bytes) {
		List<FrameUnit> list = new ArrayList<FrameUnit>();
		int count = 0;
		int unitLen = 60 * 1024;
		if (bytes.length % unitLen == 0) {
			count = bytes.length / unitLen;
		}else {
			count = bytes.length / unitLen + 1;
		}

		long timestamp = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			FrameUnit unit = new FrameUnit();
			unit.setTimestamp(timestamp);
			unit.setIndex(i);
			unit.setCount(count);
			
			//35:20
//			if (i == count -1) {
//				unitLen = bytes.length - i * unitLen;
//				System.out.println(unitLen);
//			}
//			unit.setLength(unitLen);
//			byte[] unitData = new byte[unitLen];
//			System.out.println(unitLen);
//			System.out.println(bytes.length);
//			System.out.println(count);
//			System.arraycopy(bytes, i * unitLen, unitData, 0, unitLen);
//			unit.setUnitData(unitData);
			if(i != (count - 1)){
				byte[] unitData = new byte[unitLen];
				System.arraycopy(bytes,i * unitLen, unitData, 0, unitLen);
				unit.setLength(unitLen);
				unit.setUnitData(unitData);
			}
			else{
				int remain = bytes.length % unitLen ;
				if (remain == 0){
					remain = unitLen;
				}
				byte[] unitData = new byte[remain];
				System.arraycopy(bytes, i * unitLen, unitData, 0, remain);
				unit.setLength(remain);
				unit.setUnitData(unitData);
			}
			list.add(unit);
		}
		return list;
	}

	public static void main(String[] args) throws AWTException, IOException {
		BufferedImage img = Screen.screenCatch();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(img, "jpg", bos);
		byte[] buf = bos.toByteArray();
		System.out.println(buf.length);
	}
	
}
