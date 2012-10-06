package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SpawnObjectVehicle extends Packet {
	private int eid;
	private byte type;
	private int x;
	private int y;
	private int z;
	private int objectData;
	private short speedX;
	private short speedY;
	private short speedZ;
	
	public SpawnObjectVehicle(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x17) return;
		
		this.eid = bb.getInt();
		this.type = bb.get();
		this.x = bb.getInt();
		this.y = bb.getInt();
		this.z = bb.getInt();
		this.objectData = bb.getInt();
		this.speedX = bb.getShort();
		this.speedY = bb.getShort();
		this.speedZ = bb.getShort();
	}
	
	public SpawnObjectVehicle(int eid, byte type, int x, int y, int z, int objectData, short speedX, short speedY, short speedZ) {
		this.eid = eid;
		this.type = type;
		this.x = x;
		this.y = y;
		this.z = z;
		this.objectData = objectData;
		this.speedX = speedX;
		this.speedY = speedY;
		this.speedZ = speedZ;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 28;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x17);
		bb.putInt((int)eid);
		bb.put((byte)type);
		bb.putInt((int)x);
		bb.putInt((int)y);
		bb.putInt((int)z);
		bb.putInt((int)objectData);
		bb.putShort((short)speedX);
		bb.putShort((short)speedY);
		bb.putShort((short)speedZ);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public int getEid() { return eid; }
	public byte getType() { return type; }
	public int getX() { return x; }
	public int getY() { return y; }
	public int getZ() { return z; }
	public int getObjectData() { return objectData; }
	public short getSpeedX() { return speedX; }
	public short getSpeedY() { return speedY; }
	public short getSpeedZ() { return speedZ; }
}
