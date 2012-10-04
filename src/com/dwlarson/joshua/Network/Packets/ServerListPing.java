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
		String players = "";
		String maxPlayers = "";
		players += process.getMinecraft().getCurrentPlayers();
		maxPlayers += process.getMinecraft().getMaxPlayers();
		System.out.println(players + " / " + maxPlayers);
		String responseString = motd + "\u00A7" + players + "\u00A7" + maxPlayers;
		System.out.println("Length: " + responseString.length());
		DisconnectKick response = new DisconnectKick(responseString);
		ByteBuffer bb = ByteBuffer.wrap(response.getPacket().getData());
		process.write(bb);
	}
	
}
