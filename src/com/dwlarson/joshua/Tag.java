package com.dwlarson.joshua;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Tag {
	
	private TagType type;
	private ByteBuffer data;
	private Object payload;
	private boolean noMoreData = false;
	
	public Tag(ByteBuffer data) {
		this.data = data;
	}
	
	public TagType getTag() {
		return type;
	}
	
	public void tagEnd() {
		if (type == TagType.COMPOUND) noMoreData = true;
	}
	
	public void tagByte() {
		payload = data.get();
	}
	
	public void tagShort() {
		payload = data.getShort();
	}
	
	public void tagInt() {
		payload = data.getInt();
	}
	
	public void tagLong() {
		payload = data.getLong();
	}
	
	public void tagFloat() {
		payload = data.getFloat();
	}
	
	public void tagDouble() {
		payload = data.getDouble();
	}
	
	public void tagByteArray() {
		
	}
	
	public void tagString() {
		
	}
	
	public void tagList() {
		
	}
	
	public void tagCompound() {
		type = TagType.COMPOUND;
		System.out.print("Found Tag Compound. [" + data.position() + ", " + data.remaining() + "]");
		String name = "";
		if (data.remaining() < 2) {
			System.out.println("");
			return;
		}
		data.order(ByteOrder.BIG_ENDIAN);
		int length = data.getShort();
		if (data.remaining() < length) {
			System.out.println(  "  Error: Data doesn't fit ( " + length + " )  [ 0x" + Integer.toHexString(length)+ " ]");
			return;
		}
		byte [] nameData = new byte[length];
		data.get(nameData);
		name = new String(nameData);
		System.out.println("  With the Name: " + name);
		NBTReader reader = new NBTReader(data);
		reader.read();
	}
}
