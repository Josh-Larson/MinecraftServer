package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SoundOrParticleEffect extends Packet {
	private int effectId;
	private int x;
	private byte y;
	private int z;
	private int data;
	
	public SoundOrParticleEffect(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x3D) return;
		
		this.effectId = bb.getInt();
		this.x = bb.getInt();
		this.y = bb.get();
		this.z = bb.getInt();
		this.data = bb.getInt();
	}
	
	public SoundOrParticleEffect(int effectId, int x, byte y, int z, int data) {
		this.effectId = effectId;
		this.x = x;
		this.y = y;
		this.z = z;
		this.data = data;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 18;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x3D);
		bb.putInt((int)effectId);
		bb.putInt((int)x);
		bb.put((byte)y);
		bb.putInt((int)z);
		bb.putInt((int)data);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public int getEffectId() { return effectId; }
	public int getX() { return x; }
	public byte getY() { return y; }
	public int getZ() { return z; }
	public int getData() { return data; }
}
