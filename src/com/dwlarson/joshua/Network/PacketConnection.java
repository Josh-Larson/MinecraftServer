package com.dwlarson.joshua.Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class PacketConnection implements Runnable {
	private Thread myThread;
	private ServerSocket socket;
	private boolean running = true;
	
	public ArrayList<RWSocket> clientSockets = new ArrayList<RWSocket>();
	
	public PacketConnection(ServerSocket socket) {
		try {
			this.socket = socket;
			this.socket.setSoTimeout(500);
		} catch (SocketException e) {
			running = false;
			e.printStackTrace();
		}
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
				Socket client = this.socket.accept();
				client.setSoTimeout(0);
				RWSocket clientSocket = new RWSocket(client);
				clientSockets.add(clientSocket);
				System.out.println("Found a Client!");
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
