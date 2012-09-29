package com.dwlarson.joshua.Network;

import java.net.DatagramPacket;
import java.util.LinkedList;
import java.util.Queue;

public class PacketReader implements Runnable {
	
	private Queue <DatagramPacket> packetQueue;
	private boolean running = true;
	private PacketProcess process = new PacketProcess();
	
	public PacketReader() {
		packetQueue = new LinkedList<DatagramPacket>();
	}
	
	public synchronized void addQueue(DatagramPacket pkt) {
		packetQueue.offer(pkt);
	}
	
	public void run() {
		while (running) {
			
		}
	}
	
	
}
