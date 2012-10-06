package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class PlayerAbilities extends Packet {
	private byte flags;
	private byte flyingSpeed;
	private byte walkingSpeed;
	
	public PlayerAbilities(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0xCA) return;
		
		this.flags = bb.get();
		this.flyingSpeed = bb.get();
		this.walkingSpeed = bb.get();
	}
	
	public PlayerAbilities(byte flags, byte flyingSpeed, byte walkingSpeed) {
		this.flags = flags;
		this.flyingSpeed = flyingSpeed;
		this.walkingSpeed = walkingSpeed;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 4;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0xCA);
		bb.put((byte)flags);
		bb.put((byte)flyingSpeed);
		bb.put((byte)walkingSpeed);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public byte getFlags() { return flags; }
	public byte getFlyingSpeed() { return flyingSpeed; }
	public byte getWalkingSpeed() { return walkingSpeed; }
}
