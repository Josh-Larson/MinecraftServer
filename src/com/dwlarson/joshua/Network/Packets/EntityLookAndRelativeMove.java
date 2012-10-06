package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class EntityLookAndRelativeMove extends Packet {
	private int eid;
	private byte dx;
	private byte dy;
	private byte dz;
	private byte yaw;
	private byte pitch;
	
	public EntityLookAndRelativeMove(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x21) return;
		
		this.eid = bb.getInt();
		this.dx = bb.get();
		this.dy = bb.get();
		this.dz = bb.get();
		this.yaw = bb.get();
		this.pitch = bb.get();
	}
	
	public EntityLookAndRelativeMove(int eid, byte dx, byte dy, byte dz, byte yaw, byte pitch) {
		this.eid = eid;
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
		this.yaw = yaw;
		this.pitch = pitch;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 10;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x21);
		bb.putInt((int)eid);
		bb.put((byte)dx);
		bb.put((byte)dy);
		bb.put((byte)dz);
		bb.put((byte)yaw);
		bb.put((byte)pitch);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public int getEid() { return eid; }
	public byte getDx() { return dx; }
	public byte getDy() { return dy; }
	public byte getDz() { return dz; }
	public byte getYaw() { return yaw; }
	public byte getPitch() { return pitch; }
}
