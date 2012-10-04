package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class Entitylook extends Packet {
	private int eid;
	private byte yaw;
	private byte pitch;
	
	public Entitylook(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x20) return;
		
		this.eid = bb.getInt();
		this.yaw = bb.get();
		this.pitch = bb.get();
	}
	
	public Entitylook(int eid, byte yaw, byte pitch) {
		this.eid = eid;
		this.yaw = yaw;
		this.pitch = pitch;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 7;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x20);
		bb.putInt((int)eid);
		bb.put((byte)yaw);
		bb.put((byte)pitch);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public int getEid() { return eid; }
	public byte getYaw() { return yaw; }
	public byte getPitch() { return pitch; }
}
