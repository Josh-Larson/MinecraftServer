package com.dwlarson.joshua.Network;

import java.util.HashMap;
import java.util.Map;

import com.dwlarson.joshua.Network.Packets.*;

public class PacketProcess {
	@SuppressWarnings("rawtypes")
	private Map <Object, Class> packetMap = new HashMap<Object, Class>();
	public PacketProcess() {
		packetMap.put((byte)0x02, Handshake.class);
		packetMap.put((byte)0xFD, RequestEncryptionKey.class);
		packetMap.put((byte)0xFE, ServerListPing.class);
	}
	
	
}
