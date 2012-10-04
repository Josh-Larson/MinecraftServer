package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class Namedsoundeffect extends Packet {
	private String soundName;
	private int effectPositionX;
	private int effectPositionY;
	private int effectPositionZ;
	private float volume;
	private byte pitch;
	
	public Namedsoundeffect(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x3E) return;
		
		this.soundName = MinecraftServer.getStringFromBuffer(bb);
		this.effectPositionX = bb.getInt();
		this.effectPositionY = bb.getInt();
		this.effectPositionZ = bb.getInt();
		this.volume = bb.getFloat();
		this.pitch = bb.get();
	}
	
	public Namedsoundeffect(String soundName, int effectPositionX, int effectPositionY, int effectPositionZ, float volume, byte pitch) {
		this.soundName = soundName;
		this.effectPositionX = effectPositionX;
		this.effectPositionY = effectPositionY;
		this.effectPositionZ = effectPositionZ;
		this.volume = volume;
		this.pitch = pitch;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 20 + soundName.length();
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x3E);
		MinecraftServer.putStringToBuffer(soundName, bb);
		bb.putInt((int)effectPositionX);
		bb.putInt((int)effectPositionY);
		bb.putInt((int)effectPositionZ);
		bb.putFloat((float)volume);
		bb.put((byte)pitch);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public String getSoundName() { return soundName; }
	public int getEffectPositionX() { return effectPositionX; }
	public int getEffectPositionY() { return effectPositionY; }
	public int getEffectPositionZ() { return effectPositionZ; }
	public float getVolume() { return volume; }
	public byte getPitch() { return pitch; }
}
