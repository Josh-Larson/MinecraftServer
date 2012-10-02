package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class Entityrelativemove extends Packet {
	private int eid;
	private byte dx;
	private byte dy;
	private byte dz;
	
	public Entityrelativemove(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x1F) return;
		
		this.eid = bb.getInt();
		this.dx = bb.get();
		this.dy = bb.get();
		this.dz = bb.get();
	}
	
	public Entityrelativemove(int eid, byte dx, byte dy, byte dz) {
		this.eid = eid;
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 8;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x1F);
		bb.putInt((int)eid);
		bb.put((byte)dx);
		bb.put((byte)dy);
		bb.put((byte)dz);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public int getEid() { return eid; }
	public byte getDx() { return dx; }
	public byte getDy() { return dy; }
	public byte getDz() { return dz; }
}
