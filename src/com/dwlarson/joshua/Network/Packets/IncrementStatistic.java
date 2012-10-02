package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class Incrementstatistic extends Packet {
	private int statisticId;
	private byte amount;
	
	public Incrementstatistic(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0xC8) return;
		
		this.statisticId = bb.getInt();
		this.amount = bb.get();
	}
	
	public Incrementstatistic(int statisticId, byte amount) {
		this.statisticId = statisticId;
		this.amount = amount;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 6;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0xC8);
		bb.putInt((int)statisticId);
		bb.put((byte)amount);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public int getStatisticId() { return statisticId; }
	public byte getAmount() { return amount; }
}
