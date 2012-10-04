package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class Changegamestate extends Packet {
	private byte reason;
	private byte gameMode;
	
	public Changegamestate(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x46) return;
		
		this.reason = bb.get();
		this.gameMode = bb.get();
	}
	
	public Changegamestate(byte reason, byte gameMode) {
		this.reason = reason;
		this.gameMode = gameMode;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 3;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x46);
		bb.put((byte)reason);
		bb.put((byte)gameMode);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public byte getReason() { return reason; }
	public byte getGameMode() { return gameMode; }
}
