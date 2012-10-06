package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ChunkData extends Packet {
	private int x;
	private int z;
	private boolean groundUpContinuous;
	private short primaryBitMap;
	private short addBitMap;
	private int compressedSize;
	private byte [] compressedData;
	
	public ChunkData(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x33) return;
		
		this.x = bb.getInt();
		this.z = bb.getInt();
		this.groundUpContinuous = (bb.get() == (byte)1) ? true : false;
		this.primaryBitMap = bb.getShort();
		this.addBitMap = bb.getShort();
		this.compressedSize = bb.getInt();
		this.compressedData = new byte[compressedSize];
		bb.get(this.compressedData);
	}
	
	public ChunkData(int x, int z, boolean groundUpContinuous, short primaryBitMap, short addBitMap, int compressedSize, byte [] compressedData) {
		this.x = x;
		this.z = z;
		this.groundUpContinuous = groundUpContinuous;
		this.primaryBitMap = primaryBitMap;
		this.addBitMap = addBitMap;
		this.compressedSize = compressedSize;
		this.compressedData = compressedData;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 18;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x33);
		bb.putInt((int)x);
		bb.putInt((int)z);
		bb.put(groundUpContinuous? (byte)1 : (byte)0);
		bb.putShort((short)primaryBitMap);
		bb.putShort((short)addBitMap);
		bb.putInt((int)compressedSize);
		bb.put(this.compressedData);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public int getX() { return x; }
	public int getZ() { return z; }
	public boolean getGroundUpContinuous() { return groundUpContinuous; }
	public short getPrimaryBitMap() { return primaryBitMap; }
	public short getAddBitMap() { return addBitMap; }
	public int getCompressedSize() { return compressedSize; }
	public byte [] getCompressedData() { return compressedData; }
}
