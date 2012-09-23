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
	private ByteBuffer dataBuffer;
	
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
			Vector <Tag> rootTags = new Vector<Tag>();
			while (canRead(bb)) {
				rootTags.add((readTag(bb)));
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public boolean canRead(ByteBuffer data) {
		if (data.hasRemaining()) return true;
		return false;
	}
	
	public Tag readTag(ByteBuffer data) {
		Tag ret = null;
		switch (data.get()) {
			case 0:
				ret.tagEnd();
				break;
		}
		return ret;
	}
	
}
