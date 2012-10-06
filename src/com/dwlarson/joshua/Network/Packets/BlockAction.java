package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class BlockAction extends Packet {
	private int x;
	private short y;
	private int z;
	private byte byte1;
	private byte byte2;
	private short blockId;
	
	public BlockAction(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x36) return;
		
		this.x = bb.getInt();
		this.y = bb.getShort();
		this.z = bb.getInt();
		this.byte1 = bb.get();
		this.byte2 = bb.get();
		this.blockId = bb.getShort();
	}
	
	public BlockAction(int x, short y, int z, byte byte1, byte byte2, short blockId) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.byte1 = byte1;
		this.byte2 = byte2;
		this.blockId = blockId;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 15;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x36);
		bb.putInt((int)x);
		bb.putShort((short)y);
		bb.putInt((int)z);
		bb.put((byte)byte1);
		bb.put((byte)byte2);
		bb.putShort((short)blockId);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public int getX() { return x; }
	public short getY() { return y; }
	public int getZ() { return z; }
	public byte getByte1() { return byte1; }
	public byte getByte2() { return byte2; }
	public short getBlockId() { return blockId; }
}
