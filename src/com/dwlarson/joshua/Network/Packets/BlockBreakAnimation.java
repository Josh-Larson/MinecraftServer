package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class Blockbreakanimation extends Packet {
	private int eid?;
	private int x;
	private int y;
	private  int;
	private how far destroyed this block is. 1;
	
	public Blockbreakanimation(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x37) return;
		
		this.eid? = bb.getInt();
		this.x = bb.getInt();
		this.y = bb.getInt();
		this.int = ERROR;
		this.1 = ERROR;
	}
	
	public Blockbreakanimation(int eid?, int x, int y,  int, how far destroyed this block is. 1) {
		this.eid? = eid?;
		this.x = x;
		this.y = y;
		this.int = int;
		this.1 = 1;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 13;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x37);
		bb.putInt((int)eid?);
		bb.putInt((int)x);
		bb.putInt((int)y);
		ERRORint);
		ERROR1);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public int getEid?() { return eid?; }
	public int getX() { return x; }
	public int getY() { return y; }
	public  getInt() { return int; }
	public how far destroyed this block is. get1() { return 1; }
}
