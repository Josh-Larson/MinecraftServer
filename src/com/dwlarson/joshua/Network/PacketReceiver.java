package com.dwlarson.joshua.Network;

import java.io.IOException;
<<<<<<< HEAD
import java.net.BindException;
=======
>>>>>>> 2f3e3b77e67ba27975b9e9e7fe5ef05e9154294e
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
<<<<<<< HEAD
		} catch (BindException e) {
			System.out.println("Address already in use.");
			System.out.println("Server Shuting Down.");
			System.exit(MinecraftServer.ServerReturnValues.ADDRESS_IN_USE.ordinal());
=======
>>>>>>> 2f3e3b77e67ba27975b9e9e7fe5ef05e9154294e
		} catch (IOException e) {
			e.printStackTrace();
		}
		connection = new PacketConnection(socket, server);
	}
	
<<<<<<< HEAD
	public int getConnectionCount() {
		return this.connection.getConnectionCount();
	}
	
=======
>>>>>>> 2f3e3b77e67ba27975b9e9e7fe5ef05e9154294e
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
