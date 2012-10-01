package com.dwlarson.joshua.Network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;

import com.dwlarson.joshua.MinecraftServer;

public class RWSocket implements Runnable {
	private Socket socket;
	private Queue <DatagramPacket> readPackets;
	private Thread readThread;
	private Thread processThread;
	private PacketProcess process;
	private MinecraftServer server;
	private boolean running = false;
	public RWSocket(Socket s, MinecraftServer server) {
		this.socket = s;
		this.server = server;
		running = true;

		this.readThread = new Thread(this);
		this.readPackets = new LinkedList<DatagramPacket>();
		this.readThread.start();

		this.process = new PacketProcess(this.server);
		this.process.setSocket(this);
		this.processThread = new Thread(this.process);
		this.processThread.start();
	}
	
	public void run() {
		while (running) {
			read(); // Blocking Method
		}
	}
	
	public DatagramPacket getNextPacket() {
		if (readPackets.size() == 0) return null;
		return readPackets.poll();
	}
	
	public void write(ByteBuffer data) {
		try {
			this.socket.getOutputStream().write(data.array());
			System.out.println("Just Wrote Data To Stream: " + MinecraftServer.getHexString(data.array()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void read() {
		byte [] bData = new byte[2028];
		int bytesRead = 0;
		try {
			bytesRead = this.socket.getInputStream().read(bData);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		if (bytesRead > 0) {
			byte [] data = new byte[bytesRead];
			System.arraycopy(bData, 0, data, 0, bytesRead);
			DatagramPacket packet = new DatagramPacket(data, data.length);
			packet.setAddress(this.socket.getInetAddress());
			packet.setPort(this.socket.getPort());
			readPackets.add(packet);
		} else {
			MinecraftServer.sleep(2);
		}
	}
}