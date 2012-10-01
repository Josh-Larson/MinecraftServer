package com.dwlarson.joshua.Network.Packets;

import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.nio.ByteBuffer;

import com.dwlarson.joshua.MinecraftServer;
import com.dwlarson.joshua.Network.PacketProcess;

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
	
	public void process(PacketProcess process) {
		String motd = "Josh's Server is AWESOME!";
		String players = "1025";
		String maxPlayers = "2000";
		byte [] seperator = new byte[1];
		seperator[0] = (byte)0xA7;
		String responseString = motd + (new String(seperator)) + players + (new String(seperator)) + maxPlayers;
		System.out.println("Length: " + responseString.length());
		DisconnectKick response = new DisconnectKick(responseString);
		ByteBuffer bb = ByteBuffer.wrap(response.getPacket().getData());
		process.write(bb);
	}
	
}
