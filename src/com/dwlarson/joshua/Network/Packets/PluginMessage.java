package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class PluginMessage extends Packet {
	private String channel;
	private short length;
	private byte [] data;
	
	public PluginMessage(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0xFA) return;
		
		this.channel = MinecraftServer.getStringFromBuffer(bb);
		this.length = bb.getShort();
		this.data = new byte[this.length];
		bb.get(this.data);
	}
	
	public PluginMessage(String channel, short length, byte [] data) {
		this.channel = channel;
		this.length = length;
		this.data = data;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 5 + channel.length();
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0xFA);
		MinecraftServer.putStringToBuffer(channel, bb);
		bb.putShort((short)length);
		bb.put(data);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public String getChannel() { return channel; }
	public short getLength() { return length; }
	public byte [] getData() { return data; }
}
