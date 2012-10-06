package com.dwlarson.joshua.Network.Packets;

import java.nio.ByteBuffer;

public class Record {
	
	private byte [] x;
	private byte [] y;
	private byte [] z;
	private int length;
	
	public Record(ByteBuffer bb, int length) {
		this.length = length;
		this.x = new byte[length];
		this.y = new byte[length];
		this.z = new byte[length];
		for (int i = 0; i < length; i++) {
			this.x[i] = bb.get();
			this.y[i] = bb.get();
			this.z[i] = bb.get();
		}
	}
	
	public int getLength() {
		return this.length * 3;
	}
	
	public byte [] getByteData() {
		byte [] ret = new byte[getLength()];
		for (int i = 0; i < this.length; i++) {
			ret[i*3+0] = x[i];
			ret[i*3+1] = y[i];
			ret[i*3+2] = z[i];
		}
		return ret;
	}
}
