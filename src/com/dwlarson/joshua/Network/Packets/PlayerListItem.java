package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class PlayerListItem extends Packet {
	private String playerName;
	private boolean online;
	private short ping;
	
	public PlayerListItem(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0xC9) return;
		
		this.playerName = MinecraftServer.getStringFromBuffer(bb);
		this.online = (bb.get() == (byte)1) ? true : false;
		this.ping = bb.getShort();
	}
	
	public PlayerListItem(String playerName, boolean online, short ping) {
		this.playerName = playerName;
		this.online = online;
		this.ping = ping;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 6 + playerName.length();
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0xC9);
		MinecraftServer.putStringToBuffer(playerName, bb);
		bb.put(online? (byte)1 : (byte)0);
		bb.putShort((short)ping);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public String getPlayerName() { return playerName; }
	public boolean getOnline() { return online; }
	public short getPing() { return ping; }
}
