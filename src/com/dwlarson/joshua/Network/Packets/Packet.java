package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;

import com.dwlarson.joshua.Network.PacketProcess;

public abstract class Packet {
	public abstract DatagramPacket getPacket();
	public void process(PacketProcess process) {
		
	}
}
