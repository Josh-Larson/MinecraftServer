package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class Exchantitem extends Packet {
	private byte windowId;
	private byte enchantment;
	
	public Exchantitem(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x6C) return;
		
		this.windowId = bb.get();
		this.enchantment = bb.get();
	}
	
	public Exchantitem(byte windowId, byte enchantment) {
		this.windowId = windowId;
		this.enchantment = enchantment;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 3;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x6C);
		bb.put((byte)windowId);
		bb.put((byte)enchantment);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public byte getWindowId() { return windowId; }
	public byte getEnchantment() { return enchantment; }
}
