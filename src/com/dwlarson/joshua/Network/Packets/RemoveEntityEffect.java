package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class Removeentityeffect extends Packet {
	private int entityId;
	private byte effectId;
	
	public Removeentityeffect(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x2a) return;
		
		this.entityId = bb.getInt();
		this.effectId = bb.get();
	}
	
	public Removeentityeffect(int entityId, byte effectId) {
		this.entityId = entityId;
		this.effectId = effectId;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 6;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x2a);
		bb.putInt((int)entityId);
		bb.put((byte)effectId);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public int getEntityId() { return entityId; }
	public byte getEffectId() { return effectId; }
}
