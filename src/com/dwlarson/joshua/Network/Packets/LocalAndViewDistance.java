package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class LocalAndViewDistance extends Packet {
	private String locale;
	private byte viewDistance;
	private byte chatFlags;
	private byte difficulty;
	
	public LocalAndViewDistance(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0xCC) return;
		
		this.locale = MinecraftServer.getStringFromBuffer(bb);
		this.viewDistance = bb.get();
		this.chatFlags = bb.get();
		this.difficulty = bb.get();
	}
	
	public LocalAndViewDistance(String locale, byte viewDistance, byte chatFlags, byte difficulty) {
		this.locale = locale;
		this.viewDistance = viewDistance;
		this.chatFlags = chatFlags;
		this.difficulty = difficulty;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 6 + locale.length();
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0xCC);
		MinecraftServer.putStringToBuffer(locale, bb);
		bb.put(viewDistance);
		bb.put(chatFlags);
		bb.put(difficulty);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public String getLocale() { return locale; }
	public byte getViewDistance() { return viewDistance; }
	public byte getChatFlags() { return chatFlags; }
	public byte getDifficulty() { return difficulty; }
}
