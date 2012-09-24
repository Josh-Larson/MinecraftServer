package com.dwlarson.joshua;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.zip.GZIPInputStream;
import java.util.Vector;

public class FileReader {
	
	private boolean okay = false;
	private DataInputStream dataStream;
	private ByteBuffer dataBuffer;
	private byte [] byteData;
	private int fileLength = 0;
	
	public FileReader(String filename) {
		FileInputStream stream;
		try {
			stream = new FileInputStream(filename);
			dataStream = new DataInputStream(new GZIPInputStream(stream));
			Vector <Object> bytes = new Vector<Object>();
			while (true) {
				try {
					bytes.add(dataStream.readByte());
				} catch (Exception e) {
					break;
				}
				fileLength++;
			}
			byteData = new byte[fileLength];
			for (int i = 0; i < fileLength; i++) {
				byteData[i] = (byte)bytes.get(i);
			}
			dataBuffer = ByteBuffer.wrap(byteData);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			okay = false;
			return;
		} catch (IOException e) {
			okay = false;
			e.printStackTrace();
			return;
		}
		okay = true;
	}
	
	public void read() {
		if (okay == false) return;
		NBTReader reader = new NBTReader(dataBuffer);
		reader.read();
	}
	
}
