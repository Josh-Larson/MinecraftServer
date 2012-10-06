package com.dwlarson.joshua.Network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.LinkedList;
import java.util.Queue;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.crypto.io.CipherInputStream;
import org.bouncycastle.crypto.io.CipherOutputStream;

import com.dwlarson.joshua.MinecraftServer;

public class RWSocket implements Runnable {
	private Socket socket;
	private Queue <DatagramPacket> readPackets;
	private Thread readThread;
	private Thread processThread;
	private PacketProcess process;
	private MinecraftServer server;
	private CipherInputStream readStream;
	private CipherOutputStream writeStream;
	private boolean running = false;
	private boolean encrypted = false;
	private SecretKey key;
	private KeyPair keys;
	private PrivateKey privateKey;
	
	public RWSocket(MinecraftServer server) {
		this.server = server;
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(1024);
			this.keys = keyGen.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		this.readThread = new Thread(this);
		this.readPackets = new LinkedList<DatagramPacket>();

		this.process = new PacketProcess(this.server);
		this.process.setKeyPair(keys);
		this.processThread = new Thread(this.process);
	}
	
	public void init(Socket s) {
		this.socket = s;
		running = true;
		
		this.readThread.start();

		this.process.setSocket(this);
		this.processThread.start();
	}
	
	public void run() {
		while (running) {
			if (this.socket.isClosed()) { running = false; break; }
			if (this.socket.isInputShutdown()) { running = false; break; }
			read(); // Blocking Method
		}
	}
	
	public DatagramPacket getNextPacket() {
		return readPackets.poll();
	}
	
	public void write(ByteBuffer data) {
		try {
			if (encrypted) {
				byte [] output = Encryption.encrypt(keys.getPublic(), data.array());
				if (output == null) {
					System.out.println("Error while decrypting data. With client " + this.socket.getRemoteSocketAddress());
				} else {
					System.out.println("Successfully Encrypted " + output.length + " bytes");
					writeStream.write(output);
				}
			} else {
				this.socket.getOutputStream().write(data.array());
				//System.out.println("Just Wrote Data To Stream: " + MinecraftServer.getHexString(data.array()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// Random Encryption Issue
			e.printStackTrace();
		}
	}
	
	private void read() {
		byte [] bData = new byte[2028];
		int bytesRead = 0;
		try {
			if (encrypted) {
				bytesRead = readStream.read(bData);
			} else {
				bytesRead = this.socket.getInputStream().read(bData);
			}
		} catch (SocketException e) {
			running = false;
			bytesRead = 0;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		if (bytesRead > 0) {
			// Read the TCP data
			byte [] data = new byte[bytesRead];
			System.arraycopy(bData, 0, data, 0, bytesRead);
			
			/*if (encrypted) {
				// Decrypt Data
				try {
					data = Encryption.decrypt(privateKey, data);
					System.out.println("Decrypted Packet");
				} catch (Exception e) {
					// Decryption Error
					e.printStackTrace();
					data = null;
				}
				if (data == null) return;
			}*/
			
			// Add the packet to the queue
			DatagramPacket packet = new DatagramPacket(data, data.length);
			packet.setAddress(this.socket.getInetAddress());
			packet.setPort(this.socket.getPort());
			readPackets.add(packet);
		} else {
			MinecraftServer.sleep(2);
		}
	}
	
	public void disconnect() {
		this.running = false;
		try {
			this.socket.close();
		} catch (IOException e) {
			
		}
	}
	
	public void setSecretKey(SecretKey key) {
		this.key = key;
		try {
			readStream = new CipherInputStream(this.socket.getInputStream(), Encryption.getCipher(false, key));
			writeStream = new CipherOutputStream(this.socket.getOutputStream(), Encryption.getCipher(true, key));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setEncryptionOn() {
		encrypted = true;
	}
	
	public void setEncryptionOff() {
		encrypted = false;
	}
}