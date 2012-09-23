package com.dwlarson.joshua;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Vector;
import java.util.zip.GZIPInputStream;

public class FileReader {
	
	private boolean okay = false;
	private DataInputStream dataStream;
	
	public FileReader(String filename) {
		FileInputStream stream;
		try {
			stream = new FileInputStream(filename);
			dataStream = new DataInputStream(new GZIPInputStream(stream));
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
		try {
			byte [] byteArray = new byte[dataStream.available()];
			dataStream.read(byteArray);
			ByteBuffer bb = ByteBuffer.wrap(byteArray);
			NBTReader reader = new NBTReader(bb);
			reader.read();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
}
