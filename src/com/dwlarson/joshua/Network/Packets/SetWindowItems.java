package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class Setwindowitems extends Packet {
	private byte windowId;
	private short count;
	private array of slots slotData;
	
	public Setwindowitems(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x68) return;
		
		this.windowId = bb.get();
		this.count = bb.getShort();
		this.slotData = ERROR;
	}
	
	public Setwindowitems(byte windowId, short count, array of slots slotData) {
		this.windowId = windowId;
		this.count = count;
		this.slotData = slotData;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 4;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x68);
		bb.put((byte)windowId);
		bb.putShort((short)count);
		ERRORslotData);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public byte getWindowId() { return windowId; }
	public short getCount() { return count; }
	public array of slots getSlotData() { return slotData; }
}
