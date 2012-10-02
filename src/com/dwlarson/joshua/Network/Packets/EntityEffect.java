package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class Entityeffect extends Packet {
	private int entityId;
	private byte effectId;
	private byte amplifier;
	private short duration;
	
	public Entityeffect(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x29) return;
		
		this.entityId = bb.getInt();
		this.effectId = bb.get();
		this.amplifier = bb.get();
		this.duration = bb.getShort();
	}
	
	public Entityeffect(int entityId, byte effectId, byte amplifier, short duration) {
		this.entityId = entityId;
		this.effectId = effectId;
		this.amplifier = amplifier;
		this.duration = duration;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 9;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x29);
		bb.putInt((int)entityId);
		bb.put((byte)effectId);
		bb.put((byte)amplifier);
		bb.putShort((short)duration);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public int getEntityId() { return entityId; }
	public byte getEffectId() { return effectId; }
	public byte getAmplifier() { return amplifier; }
	public short getDuration() { return duration; }
}
