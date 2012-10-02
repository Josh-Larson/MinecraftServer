package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class Encruptionkeyrequest extends Packet {
	private String serverId;
	private short publicKeyLength;
	private byte array publicKey;
	private short verifyTokenLength;
	private byte array verifyToken;
	
	public Encruptionkeyrequest(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0xFD) return;
		
		this.serverId = MinecraftServer.getStringFromBuffer(bb);
		this.publicKeyLength = bb.getShort();
		this.publicKey = ERROR;
		this.verifyTokenLength = bb.getShort();
		this.verifyToken = ERROR;
	}
	
	public Encruptionkeyrequest(String serverId, short publicKeyLength, byte array publicKey, short verifyTokenLength, byte array verifyToken) {
		this.serverId = serverId;
		this.publicKeyLength = publicKeyLength;
		this.publicKey = publicKey;
		this.verifyTokenLength = verifyTokenLength;
		this.verifyToken = verifyToken;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 7 + serverId.length();
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0xFD);
		MinecraftServer.putStringToBuffer(serverId, bb);
		bb.putShort((short)publicKeyLength);
		ERRORpublicKey);
		bb.putShort((short)verifyTokenLength);
		ERRORverifyToken);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public String getServerId() { return serverId; }
	public short getPublicKeyLength() { return publicKeyLength; }
	public byte array getPublicKey() { return publicKey; }
	public short getVerifyTokenLength() { return verifyTokenLength; }
	public byte array getVerifyToken() { return verifyToken; }
}
