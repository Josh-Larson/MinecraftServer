package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class Entitystatus extends Packet {
	private int entityId;
	private byte entityStatus;
	
	public Entitystatus(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x26) return;
		
		this.entityId = bb.getInt();
		this.entityStatus = bb.get();
	}
	
	public Entitystatus(int entityId, byte entityStatus) {
		this.entityId = entityId;
		this.entityStatus = entityStatus;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 6;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x26);
		bb.putInt((int)entityId);
		bb.put((byte)entityStatus);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public int getEntityId() { return entityId; }
	public byte getEntityStatus() { return entityStatus; }
}
