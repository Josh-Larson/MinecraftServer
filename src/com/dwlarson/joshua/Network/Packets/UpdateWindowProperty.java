package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class UpdateWindowProperty extends Packet {
	private byte windowId;
	private short property;
	private short value;
	
	public UpdateWindowProperty(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x69) return;
		
		this.windowId = bb.get();
		this.property = bb.getShort();
		this.value = bb.getShort();
	}
	
	public UpdateWindowProperty(byte windowId, short property, short value) {
		this.windowId = windowId;
		this.property = property;
		this.value = value;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 6;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x69);
		bb.put((byte)windowId);
		bb.putShort((short)property);
		bb.putShort((short)value);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public byte getWindowId() { return windowId; }
	public short getProperty() { return property; }
	public short getValue() { return value; }
}
