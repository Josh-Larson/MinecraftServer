package com.dwlarson.joshua.Network.Packets;

import java.nio.ByteBuffer;

import com.dwlarson.joshua.NBT.NBTReader;
import com.dwlarson.joshua.NBT.TagReader;

public class Slot {
	private short blockID;
	private byte itemCount;
	private short itemDamage;
	private short metaLength;
	private Enchantment [] enchantments;
	
	public Slot(ByteBuffer data) {
		this.blockID = data.getShort();
		if (this.blockID != -1) {
			this.itemCount = data.get();
			this.itemDamage = data.getShort();
			this.metaLength = data.getShort();
			if (this.metaLength != -1) {
				NBTReader reader = new NBTReader(data);
				reader.read();
				TagReader compound = reader.getTag("");
				if (compound != null) {
					TagReader list = compound.getTag("enc");
					TagReader [] ench = (TagReader [])list.getPayload();
					this.enchantments = new Enchantment[ench.length];
					for (int i = 0; i < ench.length; i++) {
						this.enchantments[i] = new Enchantment(ench[i]);
					}
				}
			}
		}
	}
	
	public int getLength() {
		return 1;
	}
	
	public byte [] getByteData() {
		/* TODO - Return actual byte data */
		return new byte[1];
	}
	
	public class Enchantment {
		private int id;
		private int level;
		
		public Enchantment(TagReader t) {
			id = (short)t.getTag("id").getPayload()[0];
			level = (short)t.getTag("lvl").getPayload()[0];
		}
	}
}
