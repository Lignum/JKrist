package me.lignum.jkrist;

import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import org.json.JSONObject;

public class Block extends KristObject {
	private long height;
	private String address;
	private String hash;
	private String shortHash;
	private int value;
	private int difficulty;
	private Date time;
	
	public Block(JSONObject block) {
		super(block);
		
		this.height = block.getLong("height");
		this.address = block.getString("address");
		this.hash = block.getString("hash");
		this.shortHash = block.getString("short_hash");
		this.value = block.getInt("value");
		this.difficulty = block.getInt("difficulty");
		this.time = DatatypeConverter.parseDateTime(block.getString("time")).getTime();
	}
	
	public long getHeight() {
		return height;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getHash() {
		return hash;
	}
	
	public String getShortHash() {
		return shortHash;
	}
	
	public int getValue() {
		return value;
	}
	
	public int getDifficulty() {
		return difficulty;
	}
	
	public Date getTime() {
		return time;
	}
}
