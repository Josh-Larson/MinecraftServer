package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.dwlarson.joshua.MinecraftServer;

public class ItemData extends Packet {
	private short itemType;
	private short itemId;
	private byte textLength;
	private byte [] text;
	
	public ItemData(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData()).order(ByteOrder.BIG_ENDIAN);
		if (bb.get() != 0x83) return;
		
		this.itemType = bb.getShort();
		this.itemId = bb.getShort();
		this.textLength = bb.get();
		this.text = new byte[this.textLength];
		bb.get(this.text);
	}
	
	public ItemData(short itemType, short itemId, byte textLength, byte [] text) {
		this.itemType = itemType;
		this.itemId = itemId;
		this.textLength = textLength;
		this.text = text;
	}
	
	public DatagramPacket getPacket() {
		int packetLength = 6 + text.length;
		ByteBuffer bb = ByteBuffer.allocate(packetLength);
		bb.put((byte)0x83);
		bb.putShort((short)itemType);
		bb.putShort((short)itemId);
		bb.put((byte)textLength);
		bb.put(text);
		return new DatagramPacket(bb.array(), packetLength);
	}
	
	public short getItemType() { return itemType; }
	public short getItemId() { return itemId; }
	public byte getTextLength() { return textLength; }
	public byte [] getText() { return text; }
}
