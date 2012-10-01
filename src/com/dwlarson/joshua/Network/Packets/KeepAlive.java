package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

public class KeepAlive extends Packet {
	private int randomID;
	
	public KeepAlive(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData());
		if (bb.get() != 0x00) return;
		System.out.println("Remaining: " + bb.remaining());
		randomID = bb.getInt();
	}
	
	public KeepAlive(int randomID) {
		this.randomID = randomID;
	}
	
	public DatagramPacket getPacket() {
		ByteBuffer data = ByteBuffer.allocate(5);
		data.put((byte)0x00);
		data.putInt(randomID);
		return new DatagramPacket(data.array(), 5);
	}
}
