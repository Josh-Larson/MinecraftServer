package com.dwlarson.joshua;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

import com.dwlarson.joshua.Network.Encryption;
import com.dwlarson.joshua.Network.PacketReceiver;

public class MinecraftServer {
	
	public enum ServerReturnValues {
		GOOD,
		ADDRESS_IN_USE
	}
	
	public static Charset UTF16 = Charset.forName("UTF-16");
	private PacketReceiver receiver = new PacketReceiver(this);
	
	/** Server ID */
	private String serverID;
	
	/** Encryption RSA Key */
	private KeyPair rsaKey;
	
	/** Verify Tokens Check the Encryption is Valid */
	private byte [] verifyTokens = {(byte)0x00, (byte)0x01, (byte)0x02, (byte)0x03};
	
	/** Current number of Players */
	private int currentPlayers = 0;
	
	/** Max number of Players */
	private int maxPlayers = 20;
	
	public MinecraftServer() {
		serverID = "Default Server ID";
		generateRSAKey();
		System.out.println("RSA Key: " + Arrays.toString(getPublicKey().getEncoded()));
	}
	
	public static void main(String [] args) {
		MinecraftServer server = new MinecraftServer();
		server.run();
	}
	
	public void run() {
		receiver.start();
		long ticks = 0;
		while (receiver.isOnline()) {
			sleep(100);
			currentPlayers = receiver.getConnectionCount();
			ticks++;
			if (ticks % 100 == 0) {
				Runtime.getRuntime().gc();
			}
		}
	}
	
	public void generateRSAKey() {
		KeyPairGenerator keyGen;
		try {
			keyGen = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return;
		}
		keyGen.initialize(1024);
		this.rsaKey = keyGen.generateKeyPair();
	}
	
	public int getCurrentPlayers() {
		return currentPlayers;
	}
	
	public int getMaxPlayers() {
		return maxPlayers;
	}
	
	public static void sleep(long milli) {
		try { Thread.sleep(milli); } catch (InterruptedException e) { }
	}
	
	public static final String getStringFromBuffer(ByteBuffer bb) {
		short dataLength = (short)(bb.getShort() * 2);
		if (bb.remaining() < dataLength) return "";
		byte [] data = new byte[dataLength];
		bb.get(data);
		return new String(data, UTF16);
	}
	
	public static final void putStringToBuffer(String str, ByteBuffer bb) {
		bb.putShort((short)str.length());
		byte [] bytes = str.getBytes(UTF16);
		if (bytes.length <= 2) return;
		bb.put(bytes, 2, bytes.length-2);
	}
	
	public static final void putCharStringToBuffer(String str, ByteBuffer bb) {
		bb.putShort((short)str.length());
		byte [] bytes = str.getBytes(UTF16);
		if (bytes.length <= 2) return;
		bb.put(bytes, 2, bytes.length-2);
	}
	
	public static final byte [] getArrayFromBuffer(ByteBuffer bb) {
		short dataLength = bb.getShort();
		byte [] data = new byte[dataLength];
		bb.get(data);
		return data;
	}
	
	public static final String getHexString(byte b) {
		String hex = Integer.toHexString(0xFF & b);
		String hexString = "";
		if (hex.length() == 1) {
		    // could use a for loop, but we're only dealing with a single byte
		    hexString += "0";
		}
		hexString += hex;
		return hexString;
	}
	
	public static final String getHexString(byte [] b) {
		String str = "[";
		for (int i = 0; i < b.length; i++) {
			if (i != 0) str += ", ";
			str += getHexString(b[i]);
		}
		str += "]";
		return str;
	}
	
	public String getServerID() {
		return serverID;
	}
	
	public PublicKey getPublicKey() {
		return rsaKey.getPublic();
	}
	
	public PrivateKey getPrivateKey() {
		return rsaKey.getPrivate();
	}
	
	public byte [] getVerifyTokens() {
		return verifyTokens;
	}
}
