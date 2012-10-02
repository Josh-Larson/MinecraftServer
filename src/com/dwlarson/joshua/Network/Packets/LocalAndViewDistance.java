package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class Localandviewdistance extends Packet {
	private String locale;
	private 0 byte;
	private 8 byte;
	private 0 byte;
	
	public Localandviewdistance(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0xCC) return;
		
		this.locale = MinecraftServer.getStringFromBuffer(bb);
		this.byte = ERROR;
		this.byte = ERROR;
		this.byte = ERROR;
	}
	
	public Localandviewdistance(String locale, 0 byte, 8 byte, 0 byte) {
		this.locale = locale;
		this.byte = byte;
		this.byte = byte;
		this.byte = byte;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 3 + locale.length();
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0xCC);
		MinecraftServer.putStringToBuffer(locale, bb);
		ERRORbyte);
		ERRORbyte);
		ERRORbyte);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public String getLocale() { return locale; }
	public 0 getByte() { return byte; }
	public 8 getByte() { return byte; }
	public 0 getByte() { return byte; }
}
