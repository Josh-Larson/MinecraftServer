package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class MapChunkBulk extends Packet {
	private short chunkCount;
	private int chunkDataLength;
	private byte [] data;
	private chunk bulk meta information metaInformation;
	
	public MapChunkBulk(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x38) return;
		
		this.chunkCount = bb.getShort();
		this.chunkDataLength = bb.getInt();
		this.data = new byte[this.chunkDataLength];
		bb.get(this.data);
		this.metaInformation = ERROR;
	}
	
	public MapChunkBulk(short chunkCount, int chunkDataLength, byte array data, chunk bulk meta information metaInformation) {
		this.chunkCount = chunkCount;
		this.chunkDataLength = chunkDataLength;
		this.data = data;
		this.metaInformation = metaInformation;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 8;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x38);
		bb.putShort((short)chunkCount);
		bb.putInt((int)chunkDataLength);
		bb.put(data);
		ERRORmetaInformation);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public short getChunkCount() { return chunkCount; }
	public int getChunkDataLength() { return chunkDataLength; }
	public byte [] getData() { return data; }
	public chunk bulk meta information getMetaInformation() { return metaInformation; }
}
