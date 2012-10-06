package com.dwlarson.joshua.NBT;

public class Payload {

	public enum PayloadType {
		NORMAL,
		ARRAY
	}
	private Object payload = null;
	private Object [] payloadArray = null;
	private PayloadType type = null;
	
	public void setPayload(Object o) {
		payload = o;
		type = PayloadType.NORMAL;
	}
	
	public void setPayload(Object [] o) {
		payloadArray = o;
		type = PayloadType.ARRAY;
	}
	
	public Object getPayload() {
		if (type == PayloadType.NORMAL) {
			return payload;
		} else {
			return null;
		}
	}
	
	public Object [] getPayloadArray() {
		if (type == PayloadType.NORMAL) {
			Object [] ret = new Object[1];
			ret[0] = payload;
			return ret;
		} else {
			return payloadArray;
		}
	}
	
	public PayloadType getType() {
		return type;
	}
}
