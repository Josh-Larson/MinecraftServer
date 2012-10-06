package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class CreativeInventoryAction extends Packet {
	private short slot;
	private Slot clickedItem;
	
	public CreativeInventoryAction(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x6B) return;
		
		this.slot = bb.getShort();
		this.clickedItem = new Slot(bb);
	}
	
	public CreativeInventoryAction(short slot, Slot clickedItem) {
		this.slot = slot;
		this.clickedItem = clickedItem;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 3;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x6B);
		bb.putShort((short)slot);
		bb.put(clickedItem.getByteData());
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public short getSlot() { return slot; }
	public Slot getClickedItem() { return clickedItem; }
}
