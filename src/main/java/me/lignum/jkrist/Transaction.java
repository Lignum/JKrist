package me.lignum.jkrist;

import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import org.json.JSONObject;

public class Transaction extends KristObject {
	private long id;
	private String fromAddress;
	private String toAddress;
	private long value;
	private Date time;
	private String name;
	private String metadata;
	
	public Transaction(JSONObject obj) {
		super(obj);
		
		this.id = obj.getLong("id");
		
		if (!obj.isNull("from")) {
			this.fromAddress = obj.getString("from");
		}
		
		if (!obj.isNull("to")) {
			this.toAddress = obj.getString("to");
		}
		
		this.value = obj.getLong("value");
		this.time = DatatypeConverter.parseDateTime(obj.getString("time")).getTime();
		
		if (!obj.isNull("name")) {
			this.name = obj.getString("name");
		}
		
		if (!obj.isNull("metadata")) {
			this.metadata = obj.getString("metadata");
		}
	}
	
	public long getID() {
		return id;
	}
	
	public String getFromAddress() {
		return fromAddress;
	}
	
	public String getToAddress() {
		return toAddress;
	}
	
	public long getValue() {
		return value;
	}
	
	public String getName() {
		return name;
	}
	
	public String getMetadata() {
		return metadata;
	}
	
	public Date getTime() {
		return time;
	}
	
	public boolean wasMined() {
		return fromAddress == null;
	}
}
