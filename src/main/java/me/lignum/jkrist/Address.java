package me.lignum.jkrist;

import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import org.json.JSONObject;

public class Address extends KristObject {
	private String name;
	private long balance;
	private long totalIn;
	private long totalOut;
	private Date firstSeen;
	
	public Address(JSONObject addr) {
		super(addr);
		
		this.name = addr.getString("address");
		this.balance = addr.getLong("balance");
		this.totalIn = addr.getLong("totalin");
		this.totalOut = addr.getLong("totalout");
		this.firstSeen = DatatypeConverter.parseDateTime(addr.getString("firstseen")).getTime();
	}
	
	public String getName() {
		return name;
	}
	
	public long getBalance() {
		return balance;
	}
	
	public long getTotalIn() {
		return totalIn;
	}
	
	public long getTotalOut() {
		return totalOut;
	}
	
	public Date getFirstSeen() {
		return firstSeen;
	}
}
