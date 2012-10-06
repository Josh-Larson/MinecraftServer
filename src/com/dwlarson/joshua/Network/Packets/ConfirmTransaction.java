package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class ConfirmTransaction extends Packet {
	private byte windowId;
	private short actionNumber;
	private boolean accepted;
	
	public ConfirmTransaction(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x6A) return;
		
		this.windowId = bb.get();
		this.actionNumber = bb.getShort();
		this.accepted = (bb.get() == (byte)1) ? true : false;
	}
	
	public ConfirmTransaction(byte windowId, short actionNumber, boolean accepted) {
		this.windowId = windowId;
		this.actionNumber = actionNumber;
		this.accepted = accepted;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 5;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x6A);
		bb.put((byte)windowId);
		bb.putShort((short)actionNumber);
		bb.put(accepted? (byte)1 : (byte)0);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public byte getWindowId() { return windowId; }
	public short getActionNumber() { return actionNumber; }
	public boolean getAccepted() { return accepted; }
}
