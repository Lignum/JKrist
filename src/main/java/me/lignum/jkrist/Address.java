package me.lignum.jkrist;

import java.util.Date;

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
		this.firstSeen = Utils.parseISO8601(addr.getString("firstseen"));
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

	public static String makeKristWalletPrivateKey(String password) {
		return Utils.sha256("KRISTWALLET" + password) + "-000";
	}

	public static char byteToHexChar(int inp) {
		int b = 48 + inp / 7;
		if (b > 57) b += 39;
		if (b > 122) b = 101;
		return (char)b;
	}

	public static String makeV2Address(String pkey) {
		StringBuilder address = new StringBuilder("k");
		String hash = Utils.sha256(Utils.sha256(pkey));
		String[] proteins = new String[9];

		for (int i = 0; i < proteins.length; i++) {
			proteins[i] = hash.substring(0, 2);
			hash = Utils.sha256(Utils.sha256(hash));
		}

		for (int i = 0; i < 9;) {
			String pair = hash.substring(i * 2, (i * 2) + 2);
			int index = Integer.parseInt(pair, 16) % 9;

			if (proteins[index] == null) {
				hash = Utils.sha256(hash);
			} else {
				int protein = Integer.parseInt(proteins[index], 16);
				address.append(byteToHexChar(protein));
				proteins[index] = null;
				i++;
			}
		}

		return address.toString();
	}
}
