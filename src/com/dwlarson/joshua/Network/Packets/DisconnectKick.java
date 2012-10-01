package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class DisconnectKick extends Packet {
	private String reason;
	
	public DisconnectKick(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0xFF) return;
		
		this.reason = MinecraftServer.getStringFromBuffer(bb);
	}
	
	public DisconnectKick(String reason) {
		this.reason = reason;
	}
	
	public DatagramPacket getPacket() { 
		int packetLength = 3 + (reason.length()+1) * 2;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0xFF);
		MinecraftServer.putStringToBuffer(reason, bb);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public String getReason() { return reason; }
}