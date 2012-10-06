package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import com.dwlarson.joshua.MinecraftServer;
import com.dwlarson.joshua.Network.Encryption;
import com.dwlarson.joshua.Network.PacketProcess;

public class EncryptionKeyResponse extends Packet {
	private short sharedSecretLength;
	private byte [] sharedSecret;
	private short verifyTokenLength;
	private byte [] verifyToken;
	
	public EncryptionKeyResponse(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != (byte)0xFC) { System.out.println("Invalid Packet."); return; }
		
		this.sharedSecret = MinecraftServer.getArrayFromBuffer(bb);
		this.sharedSecretLength = (short)this.sharedSecret.length;
		
		this.verifyToken = MinecraftServer.getArrayFromBuffer(bb);
		this.verifyTokenLength = (short)this.verifyToken.length;
	}
	
	public EncryptionKeyResponse(byte [] sharedSecret, byte [] verifyToken) {
		this.sharedSecretLength = (short)sharedSecret.length;
		this.sharedSecret = sharedSecret;
		this.verifyTokenLength = (short)verifyToken.length;
		this.verifyToken = verifyToken;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 5 + sharedSecret.length + verifyToken.length;
		ByteBuffer data = ByteBuffer.allocate(packetLength);
		data.put((byte)0xFC);
		data.putShort(sharedSecretLength);
		data.put(sharedSecret);
		data.putShort(verifyTokenLength);
		data.put(verifyToken);
		return new DatagramPacket(data.array(), packetLength);
	}
	
	public void process(PacketProcess process) {
		try {
			SecretKey key = Encryption.getSecret(this, process.getEncryptionKeyRequest());
			process.setEncryptionKeyResponse(this);
			process.setSecretKey(key);
			EncryptionKeyResponse reply = new EncryptionKeyResponse(new byte[0], new byte[0]);
			ByteBuffer data = ByteBuffer.wrap(reply.getPacket().getData());
			process.write(data);
			process.setEncryptionOn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public short getSharedSecretLength() { return sharedSecretLength; }
	public byte [] getSharedSecret() { return sharedSecret; }
	public short getVerifyTokenLength() { return verifyTokenLength; }
	public byte [] getVerifyToken() { return verifyToken; }
}
