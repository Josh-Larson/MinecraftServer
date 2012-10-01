package com.dwlarson.joshua;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import com.dwlarson.joshua.Network.PacketReceiver;

public class MinecraftServer {
	
	public static Charset UTF16 = Charset.forName("UTF-16");
	private PacketReceiver receiver = new PacketReceiver(this);
	
	/** Server ID */
	private String serverID;
	
	/** Encryption RSA Key */
	private byte [] rsaKey;
	
	/** Verify Tokens Check the Encryption is Valid */
	private byte [] verifyTokens = {(byte)0x00, (byte)0x01, (byte)0x02, (byte)0x03};
	
	public MinecraftServer() {
		serverID = "Default Server ID";
		rsaKey = generateRSAKey();
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
			ticks++;
			if (ticks % 100 == 0) {
				Runtime.getRuntime().gc();
			}
		}
	}
	
	public byte [] generateRSAKey() {
		KeyPairGenerator keyGen;
		try {
			keyGen = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		keyGen.initialize(1024);
		byte[] publicKey = keyGen.genKeyPair().getPublic().getEncoded();
		return publicKey;
	}
	
	public static void sleep(long milli) {
		try { Thread.sleep(milli); } catch (InterruptedException e) { }
	}
	
	public static String getStringFromBuffer(ByteBuffer bb) {
		short dataLength = (short)(bb.getShort() * 2);
		if (bb.remaining() < dataLength) return "";
		byte [] data = new byte[dataLength];
		bb.get(data);
		return new String(data, UTF16);
	}
	
	public static void putStringToBuffer(String str, ByteBuffer bb) {
		bb.putShort((short)str.length());
		byte [] bytes = str.getBytes(UTF16);
		if (bytes.length <= 2) return;
		bb.put(bytes, 2, bytes.length-2);
	}
	
	public static void putCharStringToBuffer(String str, ByteBuffer bb) {
		bb.putShort((short)str.length());
		byte [] bytes = str.getBytes(UTF16);
		if (bytes.length <= 2) return;
		bb.put(bytes, 2, bytes.length-2);
	}
	
	public static byte [] getArrayFromBuffer(ByteBuffer bb) {
		short dataLength = bb.getShort();
		byte [] data = new byte[dataLength];
		bb.get(data);
		return data;
	}
	
	public static String getHexString(byte b) {
		String hex = Integer.toHexString(0xFF & b);
		String hexString = "";
		if (hex.length() == 1) {
		    // could use a for loop, but we're only dealing with a single byte
		    hexString += "0";
		}
		hexString += hex;
		return hexString;
	}
	
	public static String getHexString(byte [] b) {
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
	
	public byte [] getRSAKey() {
		return rsaKey;
	}
	
	public byte [] getVerifyTokens() {
		return verifyTokens;
	}
}
