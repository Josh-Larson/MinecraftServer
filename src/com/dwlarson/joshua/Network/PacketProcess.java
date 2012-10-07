package com.dwlarson.joshua.Network;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.SecretKey;

import com.dwlarson.joshua.MinecraftServer;
import com.dwlarson.joshua.Network.Packets.*;

public class PacketProcess implements Runnable {
	private RWSocket socket;
	private boolean running = false;
	private MinecraftServer server;
	private Encryption encryption;
	private EncryptionKeyRequest request;
	private EncryptionKeyResponse response;
	private Key secretKey;
	private KeyPair keys;
	
	public PacketProcess(MinecraftServer server) {
		this.server = server;
	}
	
	public void setEncryptionKeyRequest(EncryptionKeyRequest request) { this.request = request; }
	public void setEncryptionKeyResponse(EncryptionKeyResponse response) { this.response = response; }
	public EncryptionKeyRequest getEncryptionKeyRequest() { return request; }
	public EncryptionKeyResponse getEncryptionKeyResponse() { return response; }
	public void setSecretKey(Key secretKey) {
		this.secretKey = secretKey;
		this.socket.setSecretKey(secretKey);
	}
	public void setKeyPair(KeyPair key) {
		this.keys = key;
	}
	
	public void process(DatagramPacket packet) {
		if (packet.getLength() == 0) return;
		ByteBuffer data = ByteBuffer.wrap(packet.getData());
		int packetType = (data.get() & 0xFF);
		Packet pkt = null;
		System.out.println("Received Packet (" + MinecraftServer.getHexString((byte)packetType) + ")");
		switch (packetType) {
			case 0x00:
				pkt = new KeepAlive(packet);
				break;
			case 0x01:
				pkt = new LoginRequest(packet);
				break;
			case 0x02:
				pkt = new Handshake(packet);
				break;
			case 0xFC:
				pkt = new EncryptionKeyResponse(packet);
				break;
			case 0xFD:
				pkt = new EncryptionKeyRequest(packet);
				break;
			case 0xFE:
				pkt = new ServerListPing(packet);
				break;
			default:
				System.out.println("Unhandled Packet.");
				break;
		}
		if (pkt != null) {
			pkt.process(this);
		}
	}
	
	public MinecraftServer getMinecraft() {
		return server;
	}
	
	public void setEncryption(Encryption encryption) {
		this.encryption = encryption;
	}
	
	public PublicKey getPublic() {
		return this.keys.getPublic();
	}
	
	public PrivateKey getPrivateKey() {
		return this.keys.getPrivate();
	}
	
	public KeyPair getKeyPair() {
		return this.keys;
	}
	
	public Encryption getEncryption() {
		return encryption;
	}
	
	public void setEncryptionOn() {
		this.socket.setEncryptionOn();
	}
	
	public void setEncryptionOff() {
		this.socket.setEncryptionOff();
	}
	
	public void write(ByteBuffer data) {
		this.socket.write(data);
	}
	
	public void end() {
		running = false;
	}
	
	public void disconnect(String reason) {
		DatagramPacket packet = new DisconnectKick(reason).getPacket();
		ByteBuffer data = ByteBuffer.allocate(packet.getLength());
		this.socket.write(data);
	}
	
	public void setSocket(RWSocket socket) {
		this.socket = socket;
	}
	
	public void run() {
		running = true;
		while (running) {
			DatagramPacket packet = socket.getNextPacket();
			if (packet != null) {
				process(packet);
			} else {
				MinecraftServer.sleep(1);
			}
		}
	}
}
