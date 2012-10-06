package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SetWindowItems extends Packet {
	private byte windowId;
	private short count;
	private Slot [] slotData;
	
	public SetWindowItems(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x68) return;
		
		this.windowId = bb.get();
		this.count = bb.getShort();
		this.slotData = new Slot[count];
		for (int i = 0; i < count; i++) {
			this.slotData[i] = new Slot(bb);
		}
	}
	
	public SetWindowItems(byte windowId, short count, Slot [] slotData) {
		this.windowId = windowId;
		this.count = count;
		this.slotData = slotData;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 4;
		for (int i = 0; i < count; i++) {
			packetLength += slotData[i].getLength();
		}
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x68);
		bb.put((byte)windowId);
		bb.putShort((short)count);
		for (int i = 0; i < count; i++) {
			bb.put(slotData[i].getByteData());
		}
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public byte getWindowId() { return windowId; }
	public short getCount() { return count; }
	public Slot [] getSlotData() { return slotData; }
}
