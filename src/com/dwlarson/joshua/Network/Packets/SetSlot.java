package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class Setslot extends Packet {
	private byte windowId;
	private short slot;
	private slot slotData;
	
	public Setslot(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x67) return;
		
		this.windowId = bb.get();
		this.slot = bb.getShort();
		this.slotData = ERROR;
	}
	
	public Setslot(byte windowId, short slot, slot slotData) {
		this.windowId = windowId;
		this.slot = slot;
		this.slotData = slotData;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 4;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x67);
		bb.put((byte)windowId);
		bb.putShort((short)slot);
		ERRORslotData);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public byte getWindowId() { return windowId; }
	public short getSlot() { return slot; }
	public slot getSlotData() { return slotData; }
}
