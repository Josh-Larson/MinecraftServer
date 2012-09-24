package com.dwlarson.joshua;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Tag {
	
	private TagType type;
	private ByteBuffer data;
	private Object payload;
	
	public Tag(ByteBuffer data) {
		this.data = data;
	}
	
	public TagType getTag() {
		return type;
	}
	
	public void tagEnd() {
		
	}
	
	public void tagByte() {
		
	}
	
	public void tagShort() {
		
	}
	
	public void tagInt() {
		
	}
	
	public void tagLong() {
		
	}
	
	public void tagFloat() {
		
	}
	
	public void tagDouble() {
		
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
		
	}
}
