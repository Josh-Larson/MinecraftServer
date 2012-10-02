package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class Attachentity extends Packet {
	private int entityId;
	private int vehicleId;
	
	public Attachentity(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x27) return;
		
		this.entityId = bb.getInt();
		this.vehicleId = bb.getInt();
	}
	
	public Attachentity(int entityId, int vehicleId) {
		this.entityId = entityId;
		this.vehicleId = vehicleId;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 9;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x27);
		bb.putInt((int)entityId);
		bb.putInt((int)vehicleId);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public int getEntityId() { return entityId; }
	public int getVehicleId() { return vehicleId; }
}
