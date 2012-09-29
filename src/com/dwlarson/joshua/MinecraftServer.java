package com.dwlarson.joshua;

import java.nio.ByteBuffer;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import com.dwlarson.joshua.Network.PacketReader;
import com.dwlarson.joshua.Network.PacketReceiver;

public class MinecraftServer {
	
	public static void main(String [] args) throws NoSuchAlgorithmException {
		PacketReceiver receiver = new PacketReceiver();
		PacketReader     reader = new PacketReader();
		Thread threadReceiver = new Thread(receiver);
		Thread threadReader   = new Thread(reader);
		
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);
        byte[] publicKey = keyGen.genKeyPair().getPublic().getEncoded();
        System.out.println(Arrays.toString(publicKey));

	}
	
	public static void sleep(long milli) {
		try { Thread.sleep(milli); } catch (InterruptedException e) { }
	}
	
	public static String getStringFromBuffer(ByteBuffer bb) {
		short dataLength = bb.getShort();
		byte [] data = new byte[dataLength];
		bb.get(data);
		return new String(data);
	}
	
	public static byte [] getArrayFromBuffer(ByteBuffer bb) {
		short dataLength = bb.getShort();
		byte [] data = new byte[dataLength];
		bb.get(data);
		return data;
	}
	
}
