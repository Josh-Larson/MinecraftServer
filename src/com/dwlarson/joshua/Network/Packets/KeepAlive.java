package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.util.Arrays;

import com.dwlarson.joshua.Network.PacketPing;
import com.dwlarson.joshua.Network.PacketProcess;

public class KeepAlive extends Packet {
	private int randomID;
	
	public KeepAlive(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData());
		if (bb.get() != 0x00) return;
		System.out.println("Remaining: " + bb.remaining());
		System.out.println("Packet Data: " + Arrays.toString(packet.getData()));
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
	
	public void process(PacketProcess process) {
		if (this.randomID == PacketPing.randomID) {
			ByteBuffer data = ByteBuffer.allocate(5);
			data.put((byte)0x00);
			data.putInt(PacketPing.randomID);
			process.write(data);
		}
	}
}
