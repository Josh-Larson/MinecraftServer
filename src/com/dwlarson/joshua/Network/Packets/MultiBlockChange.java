package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class MultiBlockChange extends Packet {
	private int chunkX;
	private int chunkZ;
	private short recordCount;
	private int dataSize;
	private  data;
	
	public MultiBlockChange(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x34) return;
		
		this.chunkX = bb.getInt();
		this.chunkZ = bb.getInt();
		this.recordCount = bb.getShort();
		this.dataSize = bb.getInt();
		this.data = ERROR;
	}
	
	public MultiBlockChange(int chunkX, int chunkZ, short recordCount, int dataSize,  data) {
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
		this.recordCount = recordCount;
		this.dataSize = dataSize;
		this.data = data;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 15;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x34);
		bb.putInt((int)chunkX);
		bb.putInt((int)chunkZ);
		bb.putShort((short)recordCount);
		bb.putInt((int)dataSize);
		ERRORdata);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public int getChunkX() { return chunkX; }
	public int getChunkZ() { return chunkZ; }
	public short getRecordCount() { return recordCount; }
	public int getDataSize() { return dataSize; }
	public  getData() { return data; }
}
