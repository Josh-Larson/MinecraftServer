package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class CollectItem extends Packet {
	private int collectedEid;
	private int collectorEid;
	
	public CollectItem(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x16) return;
		
		this.collectedEid = bb.getInt();
		this.collectorEid = bb.getInt();
	}
	
	public CollectItem(int collectedEid, int collectorEid) {
		this.collectedEid = collectedEid;
		this.collectorEid = collectorEid;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 9;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x16);
		bb.putInt((int)collectedEid);
		bb.putInt((int)collectorEid);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public int getCollectedEid() { return collectedEid; }
	public int getCollectorEid() { return collectorEid; }
}
