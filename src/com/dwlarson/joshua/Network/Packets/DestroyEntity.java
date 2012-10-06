package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class DestroyEntity extends Packet {
	private byte entityCount;
	private int [] entityIds;
	
	public DestroyEntity(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x1D) return;
		
		this.entityCount = bb.get();
		this.entityIds = new int[this.entityCount];
		for (int i = 0; i < entityCount; i++) {
			this.entityIds[i] = bb.getInt();
		}
	}
	
	public DestroyEntity(byte entityCount, int [] entityIds) {
		this.entityCount = entityCount;
		this.entityIds = entityIds;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 2 + (entityIds.length * 4);
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x1D);
		bb.put((byte)entityCount);
		for (int i = 0; i < this.entityIds.length; i++) {
			bb.putInt(entityIds[i]);
		}
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public byte getEntityCount() { return entityCount; }
	public int [] getEntityIds() { return entityIds; }
}
