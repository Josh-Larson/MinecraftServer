package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class BlockChange extends Packet {
	private int x;
	private byte y;
	private int z;
	private short blockType;
	private byte blockMetadata;
	
	public BlockChange(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x35) return;
		
		this.x = bb.getInt();
		this.y = bb.get();
		this.z = bb.getInt();
		this.blockType = bb.getShort();
		this.blockMetadata = bb.get();
	}
	
	public BlockChange(int x, byte y, int z, short blockType, byte blockMetadata) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.blockType = blockType;
		this.blockMetadata = blockMetadata;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 13;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x35);
		bb.putInt((int)x);
		bb.put((byte)y);
		bb.putInt((int)z);
		bb.putShort((short)blockType);
		bb.put((byte)blockMetadata);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public int getX() { return x; }
	public byte getY() { return y; }
	public int getZ() { return z; }
	public short getBlockType() { return blockType; }
	public byte getBlockMetadata() { return blockMetadata; }
}
