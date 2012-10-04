package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class Openwindow extends Packet {
	private byte windowId;
	private byte inventoryType;
	private String windowTitle;
	private byte numberOfSlots;
	
	public Openwindow(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x64) return;
		
		this.windowId = bb.get();
		this.inventoryType = bb.get();
		this.windowTitle = MinecraftServer.getStringFromBuffer(bb);
		this.numberOfSlots = bb.get();
	}
	
	public Openwindow(byte windowId, byte inventoryType, String windowTitle, byte numberOfSlots) {
		this.windowId = windowId;
		this.inventoryType = inventoryType;
		this.windowTitle = windowTitle;
		this.numberOfSlots = numberOfSlots;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 6 + windowTitle.length();
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x64);
		bb.put((byte)windowId);
		bb.put((byte)inventoryType);
		MinecraftServer.putStringToBuffer(windowTitle, bb);
		bb.put((byte)numberOfSlots);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public byte getWindowId() { return windowId; }
	public byte getInventoryType() { return inventoryType; }
	public String getWindowTitle() { return windowTitle; }
	public byte getNumberOfSlots() { return numberOfSlots; }
}
