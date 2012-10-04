package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class Creativeinventoryaction extends Packet {
	private short slot;
	private slot clickedItem;
	
	public Creativeinventoryaction(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x6B) return;
		
		this.slot = bb.getShort();
		this.clickedItem = ERROR;
	}
	
	public Creativeinventoryaction(short slot, slot clickedItem) {
		this.slot = slot;
		this.clickedItem = clickedItem;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 3;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x6B);
		bb.putShort((short)slot);
		ERRORclickedItem);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public short getSlot() { return slot; }
	public slot getClickedItem() { return clickedItem; }
}
