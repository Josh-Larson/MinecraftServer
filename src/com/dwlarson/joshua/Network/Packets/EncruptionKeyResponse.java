package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class Encruptionkeyresponse extends Packet {
	private short sharedSecretLength;
	private byte array sharedSecret;
	private short verifyTokenLength;
	private byte array verifyTokenResponse;
	
	public Encruptionkeyresponse(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0xFC) return;
		
		this.sharedSecretLength = bb.getShort();
		this.sharedSecret = ERROR;
		this.verifyTokenLength = bb.getShort();
		this.verifyTokenResponse = ERROR;
	}
	
	public Encruptionkeyresponse(short sharedSecretLength, byte array sharedSecret, short verifyTokenLength, byte array verifyTokenResponse) {
		this.sharedSecretLength = sharedSecretLength;
		this.sharedSecret = sharedSecret;
		this.verifyTokenLength = verifyTokenLength;
		this.verifyTokenResponse = verifyTokenResponse;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 5;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0xFC);
		bb.putShort((short)sharedSecretLength);
		ERRORsharedSecret);
		bb.putShort((short)verifyTokenLength);
		ERRORverifyTokenResponse);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public short getSharedSecretLength() { return sharedSecretLength; }
	public byte array getSharedSecret() { return sharedSecret; }
	public short getVerifyTokenLength() { return verifyTokenLength; }
	public byte array getVerifyTokenResponse() { return verifyTokenResponse; }
}
