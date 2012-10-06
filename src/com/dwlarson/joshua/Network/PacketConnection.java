package com.dwlarson.joshua.Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Date;

import com.dwlarson.joshua.MinecraftServer;

public class PacketConnection implements Runnable {
	private Thread myThread;
	private ServerSocket socket;
	private boolean running = true;
	private MinecraftServer server;
	private Date date;
	
	public ArrayList<RWSocket> clientSockets = new ArrayList<RWSocket>();
	
	public PacketConnection(ServerSocket socket, MinecraftServer server) {
		this.server = server;
		this.socket = socket;
		date = new Date();
	}
	
	public int getConnectionCount() {
		return this.clientSockets.size();
	}
	
	public void start() {
		myThread = new Thread(this);
		myThread.start();
	}
	
	public void join() {
		try {
			myThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void stop() {
		running = false;
	}
	
	public void run() {
		while (running) {
			try {
				RWSocket clientSocket = new RWSocket(this.server);
				Socket client = this.socket.accept();
				long startTime = date.getTime();
				clientSocket.init(client);
				clientSockets.add(clientSocket);
				long endTime = date.getTime();
				System.out.println("Found Client. Address: " + client.getRemoteSocketAddress() + " Initialization Took: " + (endTime - startTime));
			} catch (SocketTimeoutException e) {
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
