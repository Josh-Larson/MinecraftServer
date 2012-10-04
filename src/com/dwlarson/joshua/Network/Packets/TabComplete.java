package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class Tabcomplete extends Packet {
	private String text;
	
	public Tabcomplete(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0xCB) return;
		
		this.text = MinecraftServer.getStringFromBuffer(bb);
	}
	
	public Tabcomplete(String text) {
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
