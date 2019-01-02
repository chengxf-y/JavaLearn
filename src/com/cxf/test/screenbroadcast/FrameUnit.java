package com.cxf.test.screenbroadcast;

import com.cxf.test.util.Util;

public class FrameUnit {
	private long timestamp;
	private int index;
	private int count;
	private int length;
	private byte[] unitData;
	
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public byte[] getUnitData() {
		return unitData;
	}
	public void setUnitData(byte[] unitData) {
		this.unitData = unitData;
	}
	
	public byte[] toBytes() {
		byte[] bytes = new byte[14 + length];
		System.arraycopy(Util.long2Bytes(timestamp), 0, bytes, 0, 8);
		bytes[8] = (byte) index;
		bytes[9] = (byte) count;
		System.arraycopy(Util.long2Bytes(length), 0, bytes, 10, 4);
		System.arraycopy(unitData, 0, bytes, 14, length);
		return bytes;
	}
	
}
