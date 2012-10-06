package com.dwlarson.joshua.NBT;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Vector;

public class TagReader {
	
	private TagType type;
	private ByteBuffer data;
	private Payload payload = new Payload();
	private boolean noMoreData = false;
	private String tagName = "";
	
	public TagReader(ByteBuffer data) {
		this.data = data;
	}
	
	void setName(String name) {
		tagName = name;
	}
	
	public Object [] getPayload() {
		return payload.getPayloadArray();
	}
	
	public TagReader getTag(String id) {
		if (this.type == TagType.COMPOUND || this.type == TagType.LIST) {
			TagReader [] payloadArray = (TagReader [])payload.getPayloadArray();
			for (int i= 0; i < payloadArray.length; i++) {
				if (payloadArray[i].getName().compareTo(id) == 0) {
					return payloadArray[i];
				}
			}
			return null;
		} else {
			return null;
		}
	}
	
	public String getName() {
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
		type = TagType.END;
	}
	
	public void tagByte() {
		payload.setPayload(data.get());
		type = TagType.BYTE;
	}
	
	public void tagShort() {
		payload.setPayload(data.getShort());
		type = TagType.SHORT;
	}
	
	public void tagInt() {
		payload.setPayload(data.getInt());
		type = TagType.INT;
	}
	
	public void tagLong() {
		payload.setPayload(data.getLong());
		type = TagType.LONG;
	}
	
	public void tagFloat() {
		payload.setPayload(data.getFloat());
		type = TagType.FLOAT;
	}
	
	public void tagDouble() {
		payload.setPayload(data.getDouble());
		type = TagType.DOUBLE;
	}
	
	public void tagByteArray() {
		int length = data.getInt();
		byte [] byteArray = new byte[length];
		data.get(byteArray);
		payload.setPayload(byteArray);
		type = TagType.BYTE_ARRAY;
	}
	
	public void tagString() {
		int length = data.getShort();
		byte [] strArray = new byte[length];
		data.get(strArray);
		payload.setPayload(new String(strArray));
		type = TagType.STRING;
	}
	
	public void tagList() {
		byte type = data.get();
		int length = data.getInt();
		Object [] payloadArray; // 
		payloadArray = new Object[length+1];
		payloadArray[0] = new Object();
		payloadArray[0] = type;
		for (int i = 0; i < length; i++) {
			TagReader t = new TagReader(data);
			byte tagType = data.get();
			NBTReader.readTag(t, tagType);
			payloadArray[i+1] = t;
		}
		payload.setPayload(payloadArray);
		this.type = TagType.LIST;
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
		setName(name);
		//System.out.println("  With the Name: " + name);
		
		
		boolean endTag = false;
		Vector <Object> payArray = new Vector<Object>();
		while (!endTag) {
			TagReader t = new TagReader(data);
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
		payload.setPayload(payArray.toArray());
		type = TagType.COMPOUND;
	}
}
