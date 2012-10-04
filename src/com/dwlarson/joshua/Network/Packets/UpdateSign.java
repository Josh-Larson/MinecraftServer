package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class Updatesign extends Packet {
	private int x;
	private short y;
	private int z;
	private String text1;
	private String text2;
	private String text3;
	private String text4;
	
	public Updatesign(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x82) return;
		
		this.x = bb.getInt();
		this.y = bb.getShort();
		this.z = bb.getInt();
		this.text1 = MinecraftServer.getStringFromBuffer(bb);
		this.text2 = MinecraftServer.getStringFromBuffer(bb);
		this.text3 = MinecraftServer.getStringFromBuffer(bb);
		this.text4 = MinecraftServer.getStringFromBuffer(bb);
	}
	
	public Updatesign(int x, short y, int z, String text1, String text2, String text3, String text4) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.text1 = text1;
		this.text2 = text2;
		this.text3 = text3;
		this.text4 = text4;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 19 + text1.length() + text2.length() + text3.length() + text4.length();
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x82);
		bb.putInt((int)x);
		bb.putShort((short)y);
		bb.putInt((int)z);
		MinecraftServer.putStringToBuffer(text1, bb);
		MinecraftServer.putStringToBuffer(text2, bb);
		MinecraftServer.putStringToBuffer(text3, bb);
		MinecraftServer.putStringToBuffer(text4, bb);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public int getX() { return x; }
	public short getY() { return y; }
	public int getZ() { return z; }
	public String getText1() { return text1; }
	public String getText2() { return text2; }
	public String getText3() { return text3; }
	public String getText4() { return text4; }
}
