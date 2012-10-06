package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SpawnExperienceOrb extends Packet {
	private int entityId;
	private int x;
	private int y;
	private int z;
	private short count;
	
	public SpawnExperienceOrb(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x1A) return;
		
		this.entityId = bb.getInt();
		this.x = bb.getInt();
		this.y = bb.getInt();
		this.z = bb.getInt();
		this.count = bb.getShort();
	}
	
	public SpawnExperienceOrb(int entityId, int x, int y, int z, short count) {
		this.entityId = entityId;
		this.x = x;
		this.y = y;
		this.z = z;
		this.count = count;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 19;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x1A);
		bb.putInt((int)entityId);
		bb.putInt((int)x);
		bb.putInt((int)y);
		bb.putInt((int)z);
		bb.putShort((short)count);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public int getEntityId() { return entityId; }
	public int getX() { return x; }
	public int getY() { return y; }
	public int getZ() { return z; }
	public short getCount() { return count; }
}
