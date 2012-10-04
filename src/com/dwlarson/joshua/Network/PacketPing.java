package com.dwlarson.joshua.Network;

import java.nio.ByteBuffer;

import com.dwlarson.joshua.MinecraftServer;

public class PacketPing implements Runnable {
	private PacketConnection connection;
	private Thread myThread;
	private boolean running = false;
	public static int randomID = (int)Math.random();
	
	public PacketPing() {
		randomID = (int)Math.random();
	}
	
	public void end() {
		this.running = false;
	}
	
	public void start() {
		this.myThread = new Thread(this);
		running = true;
		this.myThread.start();
	}
	
	public void setConnection(PacketConnection connection) {
		this.connection = connection;
	}
	
	public void run() {
		ByteBuffer pingData = ByteBuffer.allocate(5);
		pingData.put((byte)0x00);
		pingData.putInt(randomID);
		while (running) {
			for (int i = 0; i > connection.clientSockets.size(); i++) {
				connection.clientSockets.get(i).write(pingData);
			}
			MinecraftServer.sleep(1);
		}
	}
}
