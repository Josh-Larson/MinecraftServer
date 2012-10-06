package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class EntityMetadata extends Packet {
	private int entityId;
	private Metadata entityMetadata;
	
	public EntityMetadata(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x28) return;
		
		this.entityId = bb.getInt();
		this.entityMetadata = ERROR;
	}
	
	public EntityMetadata(int entityId, metadata entityMetadata) {
		this.entityId = entityId;
		this.entityMetadata = entityMetadata;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 5;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x28);
		bb.putInt((int)entityId);
		ERRORentityMetadata);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public int getEntityId() { return entityId; }
	public metadata getEntityMetadata() { return entityMetadata; }
}
