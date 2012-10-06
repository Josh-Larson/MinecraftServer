package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class SetSlot extends Packet {
	private byte windowId;
	private short slot;
	private Slot slotData;
	
	public SetSlot(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x67) return;
		
		this.windowId = bb.get();
		this.slot = bb.getShort();
		this.slotData = new Slot(bb);
	}
	
	public SetSlot(byte windowId, short slot, Slot slotData) {
		this.windowId = windowId;
		this.slot = slot;
		this.slotData = slotData;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 4 + slot;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x67);
		bb.put((byte)windowId);
		bb.putShort((short)slot);
		bb.put(slotData.getByteData());
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public byte getWindowId() { return windowId; }
	public short getSlot() { return slot; }
	public Slot getSlotData() { return slotData; }
}
