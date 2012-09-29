package com.dwlarson.joshua;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Vector;

public class Tag {
	
	private TagType type;
	private ByteBuffer data;
	private Object payload;
	private Object [] payloadArray;
	private boolean noMoreData = false;
	private String tagName = "";
	
	public Tag(ByteBuffer data) {
		this.data = data;
	}
	
	void setName(String name) {
		tagName = name;
	}
	
	String getName() {
		return tagName;
	}
	
	public TagType getTag() {
		return type;
	}
	
	public ByteBuffer getBuffer() {
		return data;
	}
	
	public void tagEnd() {
		noMoreData = true;
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
		int length = data.getInt();
		byte [] byteArray = new byte[length];
		data.get(byteArray);
		payload = byteArray;
	}
	
	public void tagString() {
		int length = data.getShort();
		byte [] strArray = new byte[length];
		data.get(strArray);
		payload = new String(strArray);
	}
	
	public void tagList() {
		byte type = data.get();
		int length = data.getInt();
		payloadArray = new Object[length+1];
		payloadArray[0] = new Object();
		payloadArray[0] = type;
		for (int i = 0; i < length; i++) {
			Tag t = new Tag(data);
			byte tagType = data.get();
			NBTReader.readTag(t, tagType);
			payloadArray[i+1] = t;
		}
	}
	
	public void tagCompound() {
		type = TagType.COMPOUND;
		//System.out.print("Found Tag Compound. [" + data.position() + ", " + data.remaining() + "]");
		String name = "";
		if (data.remaining() < 2) {
			//System.out.println("");
			return;
		}
		data.order(ByteOrder.BIG_ENDIAN);
		int length = data.getShort();
		if (data.remaining() < length) {
			//System.out.println(  "  Error: Data doesn't fit ( " + length + " )  [ 0x" + Integer.toHexString(length)+ " ]");
			return;
		}
		byte [] nameData = new byte[length];
		data.get(nameData);
		name = new String(nameData);
		//System.out.println("  With the Name: " + name);
		
		
		boolean endTag = false;
		Vector <Object> payArray = new Vector<Object>();
		while (!endTag) {
			Tag t = new Tag(data);
			data.order(ByteOrder.BIG_ENDIAN);
			byte type = data.get();
			if (type > 10 || type < 0) {
				byte [] strType = new byte[1];
				strType[0] = type;
				System.out.println("Invalid Type? " + type + "  [" + (new String(strType)) + "]");
				continue;
			}
			
			if (type != 0 && type != 10) {
				int strLen = data.getShort();
				byte [] strData = new byte[strLen];
				//System.out.println("New Tag Length: [" + type + "] " + strData.length);
				data.get(strData);
				String tagName = new String(strData);
				t.setName(tagName);
			}
			
			try {
				NBTReader.readTag(t, type);
			} catch (BufferUnderflowException e) {
				int start = data.position() - 10;
				if (start < 0) start = 0;
				System.out.print("Faulty Data: ");
				for (int i = start; i < start+30 && i < data.array().length; i++) {
					System.out.print(data.array()[i] + ", ");
					if (i + 1 == data.array().length) {
						System.out.print("||");
					}
				}
				System.out.println("");
				endTag = true;
			}
			if (t.noMoreData) endTag = true;
			else payArray.add(t);
		}
		//System.out.println("Ended Compound.");
		payloadArray = payArray.toArray();
	}
}
