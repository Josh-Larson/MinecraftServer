package com.dwlarson.joshua;

import java.nio.ByteBuffer;
import java.util.Vector;

public class NBTReader {
	
	private ByteBuffer data;
	private Vector <Tag> rootTags;
	
	public NBTReader(ByteBuffer data) {
		this.data = data;
	}
	
	public void read() {
		rootTags = new Vector<Tag>();
		while (canRead(data)) {
			Tag t = readTag(data);
			if (t != null) rootTags.add(t);
		}
	}
	
	public boolean canRead(ByteBuffer data) {
		if (data.hasRemaining()) return true;
		return false;
	}
	
	public Tag readTag(ByteBuffer data) {
		Tag ret = new Tag(data);
		switch (data.get()) {
			case 0:
				ret.tagEnd();
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
