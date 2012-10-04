package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

import com.dwlarson.joshua.MinecraftServer;

public class LoginRequest extends Packet {
	private int entityType;
	private String levelType;
	private byte gameMode;
	private byte dimension;
	private byte difficulty;
	private byte maxPlayers; // Unsigned
	
	public LoginRequest(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData());
		if (bb.get() != 0x01) return;
		entityType = bb.getInt();
		levelType = MinecraftServer.getStringFromBuffer(bb);
		gameMode = bb.get();
		dimension = bb.get();
		difficulty = bb.get();
		bb.get(); // Unused Variable
		maxPlayers = bb.get();
	}
	
	public LoginRequest(int entityType, String levelType, byte gameMode, byte dimension, byte difficulty, byte maxPlayers) {
		this.entityType = entityType;
		this.levelType = levelType;
		this.gameMode = gameMode;
		this.dimension = dimension;
		this.difficulty = difficulty;
		this.maxPlayers = maxPlayers;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 12 + levelType.length();
		ByteBuffer data = ByteBuffer.allocate(packetLength);
		data.put((byte)0x01);
		data.putShort((short)levelType.length());
		data.put(levelType.getBytes());
		data.put(gameMode);
		data.put(dimension);
		data.put(difficulty);
		data.put((byte)0x00);
		data.put(maxPlayers);
		return new DatagramPacket(data.array(), packetLength);
	}
	
	public int getEntityType() { return entityType; }
	public String getLevelType() { return levelType; }
	public byte getGameMode() { return gameMode; }
	public byte getDimension() { return dimension; }
	public byte getDifficulty() { return difficulty; }
	public byte getMaxPlayers() { return maxPlayers; }
}
