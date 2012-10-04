package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class Spawnpainting extends Packet {
	private int entityId;
	private String title;
	private int x;
	private int y;
	private int z;
	private int direction;
	
	public Spawnpainting(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x19) return;
		
		this.entityId = bb.getInt();
		this.title = MinecraftServer.getStringFromBuffer(bb);
		this.x = bb.getInt();
		this.y = bb.getInt();
		this.z = bb.getInt();
		this.direction = bb.getInt();
	}
	
	public Spawnpainting(int entityId, String title, int x, int y, int z, int direction) {
		this.entityId = entityId;
		this.title = title;
		this.x = x;
		this.y = y;
		this.z = z;
		this.direction = direction;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 23 + title.length();
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x19);
		bb.putInt((int)entityId);
		MinecraftServer.putStringToBuffer(title, bb);
		bb.putInt((int)x);
		bb.putInt((int)y);
		bb.putInt((int)z);
		bb.putInt((int)direction);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public int getEntityId() { return entityId; }
	public String getTitle() { return title; }
	public int getX() { return x; }
	public int getY() { return y; }
	public int getZ() { return z; }
	public int getDirection() { return direction; }
}
