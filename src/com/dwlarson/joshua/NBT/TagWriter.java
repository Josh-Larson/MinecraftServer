package com.dwlarson.joshua.NBT;

public class TagWriter {
	
	private TagType type;
	private Payload payload = new Payload();
	private String name = "";
	
	public int getLength() {
		int length = 0;
		if (type == TagType.BYTE) length += 1;
		if (type == TagType.SHORT) length += 2;
		if (type == TagType.INT) length += 4;
		if (type == TagType.LONG)length += 8;
		if (type == TagType.FLOAT) length += 4;
		if (type == TagType.DOUBLE) length += 8;
		if (type == TagType.BYTE_ARRAY) length += 4 + payload.getPayloadArray().length;
		if (type == TagType.STRING) length += 2 + ((String)(payload.getPayload())).length();
		if (type == TagType.LIST) length += 5 + payload.getPayloadArray().length;
		return length;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setEnd() {
		type = TagType.END;
	}
	
	public void setByte(byte b) {
		type = TagType.BYTE;
		payload.setPayload(b);
	}
	
	public void setShort(short s) {
		type = TagType.SHORT;
		payload.setPayload(s);
	}
	
	public void setInt(int i) {
		type = TagType.INT;
		payload.setPayload(i);
	}
	
	public void setLong(long l) {
		type = TagType.LONG;
		payload.setPayload(l);
	}
	
	public void setFloat(float f) {
		type = TagType.FLOAT;
		payload.setPayload(f);
	}
	
	public void setDouble(double d) {
		type = TagType.DOUBLE;
		payload.setPayload(d);
	}
}
