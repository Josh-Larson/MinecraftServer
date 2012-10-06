package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class TabComplete extends Packet {
	private String text;
	
	public TabComplete(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0xCB) return;
		
		this.text = MinecraftServer.getStringFromBuffer(bb);
	}
	
	public TabComplete(String text) {
		this.text = text;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 3 + text.length();
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0xCB);
		MinecraftServer.putStringToBuffer(text, bb);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public String getText() { return text; }
}
