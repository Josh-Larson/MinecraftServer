package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class EncryptionKeyRequest extends Packet {
	private String authString;
	private short publicKeyLength;
	private byte [] publicKey;
	private short verifyTokenLength;
	private byte [] verifyToken;
	
	public EncryptionKeyRequest(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0xFD) return;
		
		this.authString = MinecraftServer.getStringFromBuffer(bb);
		this.publicKeyLength = bb.getShort();
		this.publicKey = new byte[this.publicKeyLength];
		bb.get(publicKey);
		this.verifyTokenLength = bb.getShort();
		this.verifyToken = new byte[this.verifyTokenLength];
	}
	
	public EncryptionKeyRequest(String authString, byte [] publicKey, byte [] verifyToken) {
		this.authString = authString;
		this.publicKeyLength = (short)publicKey.length;
		this.publicKey = publicKey;
		this.verifyTokenLength = (short)verifyToken.length;
		this.verifyToken = verifyToken;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 7 + (authString.length()*2) + publicKey.length + verifyToken.length;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0xFD);
		MinecraftServer.putStringToBuffer(authString, bb);
		bb.putShort((short)publicKeyLength);
		bb.put(publicKey);
		bb.putShort((short)verifyTokenLength);
		bb.put(verifyToken);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public String getAuthString() { return authString; }
	public short getPublicKeyLength() { return publicKeyLength; }
	public byte [] getPublicKey() { return publicKey; }
	public short getVerifyTokenLength() { return verifyTokenLength; }
	public byte [] getVerifyToken() { return verifyToken; }
}
