package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class Explosion extends Packet {
	private double x;
	private double y;
	private double z;
	private float radius;
	private int recordCount;
	private Record records;
	private float unknown1;
	private float unknown2;
	private float unknown3;
	
	public Explosion(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x3C) return;
		
		this.x = bb.getDouble();
		this.y = bb.getDouble();
		this.z = bb.getDouble();
		this.radius = bb.getFloat();
		this.recordCount = bb.getInt();
		this.records = new Record(bb, this.recordCount);
		this.unknown1 = bb.getFloat();
		this.unknown2 = bb.getFloat();
		this.unknown3 = bb.getFloat();
	}
	
	public Explosion(double x, double y, double z, float radius, int recordCount, Record records, float unknown1, float unknown2, float unknown3) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.radius = radius;
		this.recordCount = recordCount;
		this.records = records;
		this.unknown1 = unknown1;
		this.unknown2 = unknown2;
		this.unknown3 = unknown3;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 45 + records.getLength();
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x3C);
		bb.putDouble((double)x);
		bb.putDouble((double)y);
		bb.putDouble((double)z);
		bb.putFloat((float)radius);
		bb.putInt((int)recordCount);
		bb.put(records.getByteData());
		bb.putFloat((float)unknown1);
		bb.putFloat((float)unknown2);
		bb.putFloat((float)unknown3);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public double getX() { return x; }
	public double getY() { return y; }
	public double getZ() { return z; }
	public float getRadius() { return radius; }
	public int getRecordCount() { return recordCount; }
	public Record getRecords() { return records; }
	public float getUnknown1() { return unknown1; }
	public float getUnknown2() { return unknown2; }
	public float getUnknown3() { return unknown3; }
}
