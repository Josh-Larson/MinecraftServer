package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class BlockBreakAnimation extends Packet {
	private int eid;
	private int x;
	private int y;
	
	public BlockBreakAnimation(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x37) return;
		
		this.eid = bb.getInt();
		this.x = bb.getInt();
		this.y = bb.getInt();
	}
	
	public BlockBreakAnimation(int eid, int x, int y) {
		this.eid = eid;
		this.x = x;
		this.y = y;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 13;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x37);
		bb.putInt((int)eid);
		bb.putInt((int)x);
		bb.putInt((int)y);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public int getEid() { return eid; }
	public int getX() { return x; }
	public int getY() { return y; }
}
