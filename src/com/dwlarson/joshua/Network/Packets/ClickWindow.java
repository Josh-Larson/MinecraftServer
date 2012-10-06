package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class ClickWindow extends Packet {
	private byte windowId;
	private short slot;
	private boolean rightClick;
	private short actionNumber;
	private boolean shift;
	private Slot clickedItem;
	
	public ClickWindow(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x66) return;
		
		this.windowId = bb.get();
		this.slot = bb.getShort();
		this.rightClick = (bb.get() == (byte)1) ? true : false;
		this.actionNumber = bb.getShort();
		this.shift = (bb.get() == (byte)1) ? true : false;
		this.clickedItem = new Slot(bb);
	}
	
	public ClickWindow(byte windowId, short slot, boolean rightClick, short actionNumber, boolean shift, Slot clickedItem) {
		this.windowId = windowId;
		this.slot = slot;
		this.rightClick = rightClick;
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
		bb.put(rightClick? (byte)1 : (byte)0);
		bb.putShort((short)actionNumber);
		bb.put(shift? (byte)1 : (byte)0);
		bb.put(clickedItem.getByteData());
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public byte getWindowId() { return windowId; }
	public short getSlot() { return slot; }
	public boolean getRightClick() { return rightClick; }
	public short getActionNumber() { return actionNumber; }
	public boolean getShift() { return shift; }
	public Slot getClickedItem() { return clickedItem; }
}
