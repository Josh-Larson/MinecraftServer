package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class Destroyentity extends Packet {
	private byte entityCount;
	private array of int entityIds;
	
	public Destroyentity(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x1D) return;
		
		this.entityCount = bb.get();
		this.entityIds = ERROR;
	}
	
	public Destroyentity(byte entityCount, array of int entityIds) {
		this.entityCount = entityCount;
		this.entityIds = entityIds;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 2;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x1D);
		bb.put((byte)entityCount);
		ERRORentityIds);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public byte getEntityCount() { return entityCount; }
	public array of int getEntityIds() { return entityIds; }
}
