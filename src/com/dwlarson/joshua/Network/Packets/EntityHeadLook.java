package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class EntityHeadLook extends Packet {
	private int entityId;
	private byte headYaw;
	
	public EntityHeadLook(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x23) return;
		
		this.entityId = bb.getInt();
		this.headYaw = bb.get();
	}
	
	public EntityHeadLook(int entityId, byte headYaw) {
		this.entityId = entityId;
		this.headYaw = headYaw;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 6;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x23);
		bb.putInt((int)entityId);
		bb.put((byte)headYaw);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public int getEntityId() { return entityId; }
	public byte getHeadYaw() { return headYaw; }
}
