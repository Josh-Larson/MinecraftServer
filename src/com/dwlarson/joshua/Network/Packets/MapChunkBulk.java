package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class Mapchunkbulk extends Packet {
	private short chunkCount;
	private int chunkDataLength;
	private byte array data;
	private chunk bulk meta information metaInformation;
	
	public Mapchunkbulk(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x38) return;
		
		this.chunkCount = bb.getShort();
		this.chunkDataLength = bb.getInt();
		this.data = ERROR;
		this.metaInformation = ERROR;
	}
	
	public Mapchunkbulk(short chunkCount, int chunkDataLength, byte array data, chunk bulk meta information metaInformation) {
		this.chunkCount = chunkCount;
		this.chunkDataLength = chunkDataLength;
		this.data = data;
		this.metaInformation = metaInformation;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 7;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x38);
		bb.putShort((short)chunkCount);
		bb.putInt((int)chunkDataLength);
		ERRORdata);
		ERRORmetaInformation);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public short getChunkCount() { return chunkCount; }
	public int getChunkDataLength() { return chunkDataLength; }
	public byte array getData() { return data; }
	public chunk bulk meta information getMetaInformation() { return metaInformation; }
}
