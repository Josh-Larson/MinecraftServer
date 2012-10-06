package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class EntityTeleport extends Packet {
	private int eid;
	private int x;
	private int y;
	private int z;
	private byte yaw;
	private byte pitch;
	
	public EntityTeleport(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x22) return;
		
		this.eid = bb.getInt();
		this.x = bb.getInt();
		this.y = bb.getInt();
		this.z = bb.getInt();
		this.yaw = bb.get();
		this.pitch = bb.get();
	}
	
	public EntityTeleport(int eid, int x, int y, int z, byte yaw, byte pitch) {
		this.eid = eid;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 19;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x22);
		bb.putInt((int)eid);
		bb.putInt((int)x);
		bb.putInt((int)y);
		bb.putInt((int)z);
		bb.put((byte)yaw);
		bb.put((byte)pitch);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public int getEid() { return eid; }
	public int getX() { return x; }
	public int getY() { return y; }
	public int getZ() { return z; }
	public byte getYaw() { return yaw; }
	public byte getPitch() { return pitch; }
}
