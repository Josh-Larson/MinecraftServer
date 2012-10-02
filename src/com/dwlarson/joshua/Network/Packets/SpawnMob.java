package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class Spawnmob extends Packet {
	private int eid;
	private byte type;
	private int x;
	private int y;
	private int z;
	private byte yaw;
	private byte pitch;
	private byte headYaw;
	private short velocityZ;
	private short velocityX;
	private short velocityY;
	private metadata metadata;
	
	public Spawnmob(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x18) return;
		
		this.eid = bb.getInt();
		this.type = bb.get();
		this.x = bb.getInt();
		this.y = bb.getInt();
		this.z = bb.getInt();
		this.yaw = bb.get();
		this.pitch = bb.get();
		this.headYaw = bb.get();
		this.velocityZ = bb.getShort();
		this.velocityX = bb.getShort();
		this.velocityY = bb.getShort();
		this.metadata = ERROR;
	}
	
	public Spawnmob(int eid, byte type, int x, int y, int z, byte yaw, byte pitch, byte headYaw, short velocityZ, short velocityX, short velocityY, metadata metadata) {
		this.eid = eid;
		this.type = type;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
		this.headYaw = headYaw;
		this.velocityZ = velocityZ;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.metadata = metadata;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 27;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x18);
		bb.putInt((int)eid);
		bb.put((byte)type);
		bb.putInt((int)x);
		bb.putInt((int)y);
		bb.putInt((int)z);
		bb.put((byte)yaw);
		bb.put((byte)pitch);
		bb.put((byte)headYaw);
		bb.putShort((short)velocityZ);
		bb.putShort((short)velocityX);
		bb.putShort((short)velocityY);
		ERRORmetadata);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public int getEid() { return eid; }
	public byte getType() { return type; }
	public int getX() { return x; }
	public int getY() { return y; }
	public int getZ() { return z; }
	public byte getYaw() { return yaw; }
	public byte getPitch() { return pitch; }
	public byte getHeadYaw() { return headYaw; }
	public short getVelocityZ() { return velocityZ; }
	public short getVelocityX() { return velocityX; }
	public short getVelocityY() { return velocityY; }
	public metadata getMetadata() { return metadata; }
}
