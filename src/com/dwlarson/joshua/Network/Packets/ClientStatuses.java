package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class Clientstatuses extends Packet {
	private byte payload;
	
	public Clientstatuses(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0xCD) return;
		
		this.payload = bb.get();
	}
	
	public Clientstatuses(byte payload) {
		this.payload = payload;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 2;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0xCD);
		bb.put((byte)payload);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public byte getPayload() { return payload; }
}
