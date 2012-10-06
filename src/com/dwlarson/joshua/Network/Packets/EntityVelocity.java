package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class EntityVelocity extends Packet {
	private int entityId;
	private short velocityX;
	private short velocityY;
	private short velocityZ;
	
	public EntityVelocity(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x1C) return;
		
		this.entityId = bb.getInt();
		this.velocityX = bb.getShort();
		this.velocityY = bb.getShort();
		this.velocityZ = bb.getShort();
	}
	
	public EntityVelocity(int entityId, short velocityX, short velocityY, short velocityZ) {
		this.entityId = entityId;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.velocityZ = velocityZ;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 11;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x1C);
		bb.putInt((int)entityId);
		bb.putShort((short)velocityX);
		bb.putShort((short)velocityY);
		bb.putShort((short)velocityZ);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public int getEntityId() { return entityId; }
	public short getVelocityX() { return velocityX; }
	public short getVelocityY() { return velocityY; }
	public short getVelocityZ() { return velocityZ; }
}
