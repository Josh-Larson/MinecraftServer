package com.dwlarson.joshua.Network;

import java.io.IOException;
import java.net.ServerSocket;

import com.dwlarson.joshua.MinecraftServer;

public class PacketReceiver implements Runnable {
	private boolean online = true;
	private final int portNumber = 25565;
	private Thread myThread;
	private PacketConnection connection;
	private ServerSocket socket;
	private MinecraftServer server;
	
	public PacketReceiver(MinecraftServer server) {
		this.server = server;
		try {
			socket = new ServerSocket(portNumber);
		} catch (IOException e) {
			e.printStackTrace();
		}
		connection = new PacketConnection(socket, server);
	}
	
	public void start() {
		myThread = new Thread(this);
		myThread.start();
		connection.start();
	}
	
	public boolean isOnline() {
		return online;
	}
	
	public void run() {
		if (online) System.out.println("PacketReceiver Is Running On Port ");
		connection.join();
		System.out.println("PacketReceiver Has Stopped Running");
	}
}
