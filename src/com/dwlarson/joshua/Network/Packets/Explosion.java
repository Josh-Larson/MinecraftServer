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
	private (byte, byte, byte) × count records;
	private float unknown;
	private float unknown;
	private float unknown;
	
	public Explosion(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x3C) return;
		
		this.x = bb.getDouble();
		this.y = bb.getDouble();
		this.z = bb.getDouble();
		this.radius = bb.getFloat();
		this.recordCount = bb.getInt();
		this.records = ERROR;
		this.unknown = bb.getFloat();
		this.unknown = bb.getFloat();
		this.unknown = bb.getFloat();
	}
	
	public Explosion(double x, double y, double z, float radius, int recordCount, (byte, byte, byte) × count records, float unknown, float unknown, float unknown) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.radius = radius;
		this.recordCount = recordCount;
		this.records = records;
		this.unknown = unknown;
		this.unknown = unknown;
		this.unknown = unknown;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 45;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x3C);
		bb.putDouble((double)x);
		bb.putDouble((double)y);
		bb.putDouble((double)z);
		bb.putFloat((float)radius);
		bb.putInt((int)recordCount);
		ERRORrecords);
		bb.putFloat((float)unknown);
		bb.putFloat((float)unknown);
		bb.putFloat((float)unknown);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public double getX() { return x; }
	public double getY() { return y; }
	public double getZ() { return z; }
	public float getRadius() { return radius; }
	public int getRecordCount() { return recordCount; }
	public (byte, byte, byte) × count getRecords() { return records; }
	public float getUnknown() { return unknown; }
	public float getUnknown() { return unknown; }
	public float getUnknown() { return unknown; }
}
