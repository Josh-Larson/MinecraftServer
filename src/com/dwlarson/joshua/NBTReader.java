package com.dwlarson.joshua;

import java.nio.ByteBuffer;
import java.util.Vector;

public class NBTReader {
	
	private ByteBuffer data;
	private Vector <Tag> rootTags;
	private boolean done = false;
	
	public NBTReader(ByteBuffer data) {
		this.data = data;
	}
	
	public void read() {
		rootTags = new Vector<Tag>();
		while (canRead() && !done) {
			Tag t = readTag();
			if (t != null) rootTags.add(t);
			else break;
		}
	}
	
	public boolean canRead() {
		if (data.hasRemaining()) return true;
		return false;
	}
	
	public Tag readTag() {
		Tag ret = new Tag(data);
		switch (data.get()) {
			case 0:
				ret.tagEnd();
				done = true;
				break;
			case 1:
				ret.tagByte();
				break;
			case 2:
				ret.tagShort();
				break;
			case 3:
				ret.tagInt();
				break;
			case 4:
				ret.tagLong();
				break;
			case 5:
				ret.tagFloat();
				break;
			case 6:
				ret.tagDouble();
				break;
			case 7:
				ret.tagByteArray();
				break;
			case 8:
				ret.tagString();
				break;
			case 9:
				ret.tagList();
				break;
			case 10:
				ret.tagCompound();
				break;
			default:
				ret = null;
				break;
		}
		return ret;
	}
}
