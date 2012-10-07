package com.dwlarson.joshua.Network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

import javax.crypto.SecretKey;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
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
	private BufferedBlockCipher readCipher;
	private BufferedBlockCipher writeCipher;
	private boolean running = false;
	private boolean encrypted = false;
	private Key key;
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
		Date date = new Date();
		long lastRead = date.getTime();
		int badComms = 0;
		while (running) {
			if (this.socket.isClosed()) { running = false; break; }
			if (this.socket.isInputShutdown()) { running = false; break; }
			read(); // Blocking Method
			if (date.getTime()- lastRead < 15L) {
				badComms++;
				if (badComms > 5) {
					System.out.println("Client Ended Communications.");
					running = false;
					break;
				}
			} else {
				badComms = 0;
			}
			lastRead = date.getTime();
		}
		this.process.end();
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
		byte [] bData = new byte[2048];
		byte [] data = null;
		int bytesRead = 0;
		try {
			/*if (encrypted) {
				System.out.println("Listening...");
				bytesRead = readStream.read(bData);
				System.out.println("Decrypting Data. " + bytesRead);
				if (bytesRead > 0) {
					data = new byte[readCipher.getOutputSize(bData.length)];
					int outputLen = readCipher.processBytes(bData, 0, bytesRead, data, 0);
					try {
						outputLen += readCipher.doFinal(data, outputLen);
						byte [] realData = new byte[outputLen];
						System.arraycopy(data, 0, realData, 0, outputLen);
						data = realData;
						System.out.println("Decrypted Data.");
					} catch (Exception e) {
						e.printStackTrace();
						data = null;
					}
				}
			} else {*/
				bytesRead = this.socket.getInputStream().read(bData);
				if (encrypted) {
					if (bytesRead > 0) {
						data = new byte[readCipher.getOutputSize(bData.length)];
						int outputLen = readCipher.processBytes(bData, 0, bytesRead, data, 0);
						try {
							outputLen += readCipher.doFinal(data, outputLen);
							byte [] realData = new byte[outputLen];
							System.arraycopy(data, 0, realData, 0, outputLen);
							data = realData;
							System.out.println("Decrypted Data. " + MinecraftServer.getHexString(data));
						} catch (Exception e) {
							e.printStackTrace();
							data = null;
						}
					}
				} else {
					if (bytesRead > 0) {
						data = new byte[bytesRead];
						System.arraycopy(bData, 0, data, 0, bytesRead);
					}
				}
			//}
		} catch (SocketException e) {
			running = false;
			bytesRead = 0;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		if (data != null) {
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
			System.out.println("Added Packet. ------- " + packet.getLength());
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
	
	public void setSecretKey(Key key) {
		this.key = key;
		try {
			readCipher = Encryption.getCipher(false, this.key);
			writeCipher = Encryption.getCipher(true, this.key);
			readStream = new CipherInputStream(this.socket.getInputStream(), readCipher);
			writeStream = new CipherOutputStream(this.socket.getOutputStream(), writeCipher);
			System.out.println("Set the Ciphers.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setEncryptionOn() {
		encrypted = true;
		System.out.println("Set Encryption On.");
		readThread.interrupt();
	}
	
	public void setEncryptionOff() {
		encrypted = false;
	}
}