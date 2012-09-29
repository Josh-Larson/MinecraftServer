package com.dwlarson.joshua.Network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import com.dwlarson.joshua.MinecraftServer;

public class PacketReceiver implements Runnable {
	private DatagramSocket socket;
	private boolean online = true;
	private PacketReader reader;
	
	public PacketReceiver() {
		try {
			socket = new DatagramSocket(9996);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			online = false;
		}
	}
	
	public boolean isOnline() {
		return online;
	}
	
	public void setReaderInstance(PacketReader reader) {
		this.reader = reader;
	}
	
	public void run() {
		try {
			socket.setSoTimeout(500);
		} catch (SocketException e) {
			online = false;
			e.printStackTrace();
		}
		while (online) {
			DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
			try {
				socket.receive(packet);
				reader.addQueue(packet);
			} catch (SocketTimeoutException e) {
				continue;
			} catch (IOException e) {
				e.printStackTrace();
			}
			MinecraftServer.sleep(1);
		}
	}
}
