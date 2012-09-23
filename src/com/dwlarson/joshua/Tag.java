package com.dwlarson.joshua;

import java.nio.ByteBuffer;

public class Tag {
	
	private TagType type;
	private ByteBuffer data;
	
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
		
	}
}
