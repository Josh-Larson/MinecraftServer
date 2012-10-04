package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class Clickwindow extends Packet {
	private byte windowId;
	private short slot;
	private boolean right-click;
	private short actionNumber;
	private boolean shift;
	private slot clickedItem;
	
	public Clickwindow(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x66) return;
		
		this.windowId = bb.get();
		this.slot = bb.getShort();
		this.right-click = (bb.get() == (byte)1) ? true : false;
		this.actionNumber = bb.getShort();
		this.shift = (bb.get() == (byte)1) ? true : false;
		this.clickedItem = ERROR;
	}
	
	public Clickwindow(byte windowId, short slot, boolean right-click, short actionNumber, boolean shift, slot clickedItem) {
		this.windowId = windowId;
		this.slot = slot;
		this.right-click = right-click;
		this.actionNumber = actionNumber;
		this.shift = shift;
		this.clickedItem = clickedItem;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 8;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x66);
		bb.put((byte)windowId);
		bb.putShort((short)slot);
		(bb.put((byte)right-click? (byte)1 : (byte)0);
		bb.putShort((short)actionNumber);
		(bb.put((byte)shift? (byte)1 : (byte)0);
		ERRORclickedItem);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public byte getWindowId() { return windowId; }
	public short getSlot() { return slot; }
	public boolean getRight-click() { return right-click; }
	public short getActionNumber() { return actionNumber; }
	public boolean getShift() { return shift; }
	public slot getClickedItem() { return clickedItem; }
}
