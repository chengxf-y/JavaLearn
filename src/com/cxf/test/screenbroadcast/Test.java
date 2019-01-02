package com.cxf.test.screenbroadcast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.cxf.test.util.Util;

public class Test {
	public static void main(String[] args) {
		
//		byte[] bs = {0,-16,0,1};
		byte[] bs = new byte[1025];
		byte[] zbs = zipOneScreen(bs);
		byte[] uzip = unzipOneScreen(zbs);
		for (byte b : uzip) {
			//System.out.println(b);
		}
	}
	
	public static byte[] zipOneScreen(byte[] bytes) {
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
	
	public static byte[] unzipOneScreen(byte[] screenBytes) {
		ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(screenBytes));
		try {
			ZipEntry enyty = zis.getNextEntry();
			System.out.println(enyty.getName());
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int len = -1;
			byte[] buf = new byte[1024];
			while ((len = zis.read(buf)) != -1) {
				System.out.println(len);
				baos.write(buf);
			}
			return baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
