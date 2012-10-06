package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SetExperience extends Packet {
	private float experienceBar;
	private short level;
	private short totalExperience;
	
	public SetExperience(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x2B) return;
		
		this.experienceBar = bb.getFloat();
		this.level = bb.getShort();
		this.totalExperience = bb.getShort();
	}
	
	public SetExperience(float experienceBar, short level, short totalExperience) {
		this.experienceBar = experienceBar;
		this.level = level;
		this.totalExperience = totalExperience;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 9;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x2B);
		bb.putFloat((float)experienceBar);
		bb.putShort((short)level);
		bb.putShort((short)totalExperience);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public float getExperienceBar() { return experienceBar; }
	public short getLevel() { return level; }
	public short getTotalExperience() { return totalExperience; }
}
