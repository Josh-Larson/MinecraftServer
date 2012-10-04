package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class Thunderbolt extends Packet {
	private int entityId;
	private boolean unknown;
	private int x;
	private int y;
	private int z;
	
	public Thunderbolt(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x47) return;
		
		this.entityId = bb.getInt();
		this.unknown = (bb.get() == (byte)1) ? true : false;
		this.x = bb.getInt();
		this.y = bb.getInt();
		this.z = bb.getInt();
	}
	
	public Thunderbolt(int entityId, boolean unknown, int x, int y, int z) {
		this.entityId = entityId;
		this.unknown = unknown;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 18;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x47);
		bb.putInt((int)entityId);
		(bb.put((byte)unknown? (byte)1 : (byte)0);
		bb.putInt((int)x);
		bb.putInt((int)y);
		bb.putInt((int)z);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public int getEntityId() { return entityId; }
	public boolean getUnknown() { return unknown; }
	public int getX() { return x; }
	public int getY() { return y; }
	public int getZ() { return z; }
}
