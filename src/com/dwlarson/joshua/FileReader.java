package com.dwlarson.joshua;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.zip.GZIPInputStream;
import java.util.Vector;

import com.dwlarson.joshua.NBT.NBTReader;
import com.dwlarson.joshua.NBT.TagReader;
import com.dwlarson.joshua.NBT.TagType;

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
		System.out.println("Recorded Root Tags: " + reader.getTags().size());
		for (int i = 0; i < reader.getTags().size(); i++) {
			readTagPayload(reader.getTags().get(i), 0);
		}
	}
	
	public void readTagPayload(TagReader t, int tab) {
		for (int i = 0; i < tab; i++) System.out.print("    ");
		if (t.getTag() == TagType.COMPOUND || t.getTag() == TagType.LIST) {
			System.out.println("Tag Array '" + t.getName() + "' [" + t.getPayload().length + "]");
			for (int i = 0; i < t.getPayload().length; i++) {
				readTagPayload((TagReader)t.getPayload()[i], tab+1);
			}
		} else {
			if (t.getPayload().length > 0) {
				System.out.println(t.getName() + ": " + t.getPayload()[0]);
			} else {
				System.out.println("Invalid Payload Length. TagType: " + t.getTag().toString());
			}
		}
	}
	
}
