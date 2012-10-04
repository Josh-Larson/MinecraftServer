package com.dwlarson.joshua.Network.Packets;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

<<<<<<< HEAD
import com.dwlarson.joshua.Network.PacketPing;
import com.dwlarson.joshua.Network.PacketProcess;

=======
>>>>>>> 2f3e3b77e67ba27975b9e9e7fe5ef05e9154294e
public class KeepAlive extends Packet {
	private int randomID;
	
	public KeepAlive(DatagramPacket packet) {
		ByteBuffer bb = ByteBuffer.wrap(packet.getData());
		if (bb.get() != 0x00) return;
		System.out.println("Remaining: " + bb.remaining());
		randomID = bb.getInt();
	}
	
	public KeepAlive(int randomID) {
		this.randomID = randomID;
	}
	
	public DatagramPacket getPacket() {
		ByteBuffer data = ByteBuffer.allocate(5);
		data.put((byte)0x00);
		data.putInt(randomID);
		return new DatagramPacket(data.array(), 5);
	}
<<<<<<< HEAD
	
	public void process(PacketProcess process) {
		if (this.randomID == PacketPing.randomID) {
			ByteBuffer data = ByteBuffer.allocate(5);
			data.put((byte)0x00);
			data.putInt(PacketPing.randomID);
			process.write(data);
		}
	}
=======
>>>>>>> 2f3e3b77e67ba27975b9e9e7fe5ef05e9154294e
}
