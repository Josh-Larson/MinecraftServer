package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;
import com.dwlarson.joshua.Network.PacketProcess;

public class RequestEncryptionKey extends Packet {
	private String serverID;
	private short publicKeyLength;
	private byte [] publicKey;
	private short verifyTokenLength;
	private byte [] verifyToken;
	
	public RequestEncryptionKey(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0xFD) return;
		
		serverID = MinecraftServer.getStringFromBuffer(bb);
		
		publicKey = MinecraftServer.getArrayFromBuffer(bb);
		
		verifyToken = MinecraftServer.getArrayFromBuffer(bb);
	}
	
	public RequestEncryptionKey(String serverID, byte [] publicKey, byte [] verifyToken) {
		this.serverID = serverID;
		this.publicKeyLength = (short)publicKey.length;
		this.publicKey = publicKey;
		this.verifyTokenLength = (short)verifyToken.length;
		this.verifyToken = verifyToken;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 7 + serverID.length() + publicKey.length + verifyToken.length;
		ByteBuffer data = ByteBuffer.allocate(packetLength);
		data.put((byte)0xFD);
		data.putShort((short)serverID.length());
		data.put(serverID.getBytes());
		data.putShort(publicKeyLength);
		data.put(publicKey);
		data.putShort(verifyTokenLength);
		data.put(verifyToken);
		return new DatagramPacket(data.array(), packetLength);
	}
	
	public String getServerID() { return serverID; }
	public short getPublicKeyLength() { return publicKeyLength; }
	public byte [] getPublicKey() { return publicKey; }
	public short getVerifyTokenLength() { return verifyTokenLength; }
	public byte [] getVerifyToken() { return verifyToken; }
}
