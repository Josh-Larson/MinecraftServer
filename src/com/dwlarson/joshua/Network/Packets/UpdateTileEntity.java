package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class Updatetileentity extends Packet {
	private int x;
	private short y;
	private int z;
	private byte action;
	private short dataLength;
	private byte array - present if data length > 0 nbtData;
	
	public Updatetileentity(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x84) return;
		
		this.x = bb.getInt();
		this.y = bb.getShort();
		this.z = bb.getInt();
		this.action = bb.get();
		this.dataLength = bb.getShort();
		this.nbtData = ERROR;
	}
	
	public Updatetileentity(int x, short y, int z, byte action, short dataLength, byte array - present if data length > 0 nbtData) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.action = action;
		this.dataLength = dataLength;
		this.nbtData = nbtData;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 14;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x84);
		bb.putInt((int)x);
		bb.putShort((short)y);
		bb.putInt((int)z);
		bb.put((byte)action);
		bb.putShort((short)dataLength);
		ERRORnbtData);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public int getX() { return x; }
	public short getY() { return y; }
	public int getZ() { return z; }
	public byte getAction() { return action; }
	public short getDataLength() { return dataLength; }
	public byte array - present if data length > 0 getNbtData() { return nbtData; }
}
