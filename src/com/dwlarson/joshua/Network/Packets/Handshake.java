package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Random;

import com.dwlarson.joshua.MinecraftServer;
import com.dwlarson.joshua.Network.Encryption;
import com.dwlarson.joshua.Network.PacketProcess;

public class Handshake extends Packet {
	private byte protocolVersion;
	private String username;
	private String serverHost;
	private int serverPort;
	
	public Handshake(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x02) return;
		
		this.protocolVersion = bb.get();
		
		this.username = MinecraftServer.getStringFromBuffer(bb);
		
		this.serverHost = MinecraftServer.getStringFromBuffer(bb);
		
		this.serverPort = bb.getInt();
	}
	
	public Handshake(byte protocolVersion, String username, String serverHost, int serverPort) {
		this.protocolVersion = protocolVersion;
		this.username = username;
		this.serverHost = serverHost;
		this.serverPort = serverPort;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 10 + username.length() + serverHost.length();
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x02);
		bb.put(protocolVersion);
		bb.putShort((short)username.length());
		bb.put(username.getBytes());
		bb.putShort((short)serverHost.length());
		bb.put(serverHost.getBytes());
		bb.putInt(serverPort);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public void process(PacketProcess process) {
		try {
			EncryptionKeyRequest request = Encryption.encryptRequest(process.getKeyPair());
			process.setEncryptionKeyRequest(request);
			ByteBuffer data = ByteBuffer.wrap(request.getPacket().getData());
			process.write(data);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	public byte getProtocolVersion() { return protocolVersion; }
	public String getUsername() { return username; }
	public String getServerHost() { return serverHost; }
	public int getServerPort() { return serverPort; }
}
