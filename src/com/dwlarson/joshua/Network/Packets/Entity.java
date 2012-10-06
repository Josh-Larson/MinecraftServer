package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Entity extends Packet {
	private int eid;
	
	public Entity(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x1E) return;
		
		this.eid = bb.getInt();
	}
	
	public Entity(int eid) {
		this.eid = eid;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 5;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x1E);
		bb.putInt((int)eid);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public int getEid() { return eid; }
}
