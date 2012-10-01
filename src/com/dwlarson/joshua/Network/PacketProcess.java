package com.dwlarson.joshua.Network;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

import com.dwlarson.joshua.MinecraftServer;
import com.dwlarson.joshua.Network.Packets.*;

public class PacketProcess implements Runnable {
	private RWSocket socket;
	private boolean running = false;
	private MinecraftServer server;
	
	public PacketProcess(MinecraftServer server) {
		this.server = server;
	}
	
	public void process(DatagramPacket packet) {
		if (packet.getLength() == 0) return;
		ByteBuffer data = ByteBuffer.wrap(packet.getData());
		int packetType = (data.get() & 0xFF);
		Packet pkt = null;
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
			case 0xFD:
				pkt = new RequestEncryptionKey(packet);
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
	
	public byte [] getPublicKey() {
		return server.generateRSAKey();
	}
	
	public void write(ByteBuffer data) {
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
