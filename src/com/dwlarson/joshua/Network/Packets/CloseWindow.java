package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class CloseWindow extends Packet {
	private byte windowId;
	
	public CloseWindow(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x65) return;
		
		this.windowId = bb.get();
	}
	
	public CloseWindow(byte windowId) {
		this.windowId = windowId;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 2;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x65);
		bb.put((byte)windowId);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public byte getWindowId() { return windowId; }
}
