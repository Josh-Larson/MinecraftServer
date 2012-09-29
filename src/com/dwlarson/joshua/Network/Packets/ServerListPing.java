package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;

public class ServerListPing extends Packet {
	
	public ServerListPing(DatagramPacket packet) {
		
	}
	
	public ServerListPing() {
		
	}
	
	public DatagramPacket getPacket() {
		byte [] data = new byte[1];
		data[0] = (byte)0xFE;
		return new DatagramPacket(data, 1);
	}
	
}
