package me.lignum.jkrist;

import java.lang.reflect.Array;

import org.json.JSONArray;
import org.json.JSONObject;

public class Krist {
	/** The default krist node to use */
	private static final String DEFAULT_NODE = "http://krist.ceriat.net";
	
	private static final int DEFAULT_LIST_CAP = 50;
	
	/** The Krist URL to use */
	private String node;
	
	/**
	 * @param node The Krist node to use for all API calls.
	 */
	public Krist(String node) {
		this.node = node;
	}
	
	public Krist() {
		this(DEFAULT_NODE);
	}

	/**
	 * Submits a block.
	 * @param address The address to send rewards to.
	 * @param nonce The nonce.
	 * @return The mined {@link me.lignum.jkrist.Block}.
	 * @throws KristAPIException Can be thrown if the nonce is too long.
     	*/
	public Block submitBlock(String address, String nonce) throws KristAPIException {
		JSONObject request = new JSONObject();
		request.put("address", address);
		request.put("nonce", nonce);

		JSONObject obj = new JSONObject(HTTPHelper.post(node + "/submit", request));

		if (!obj.getBoolean("ok")) {
			throw new KristAPIException(obj.getString("error"));
		}

		if (!obj.getBoolean("success")) {
			return null;
		}

		return new Block(obj.getJSONObject("block"));
	}
	
	/**
	 * Makes a transaction.
	 * @param privatekey The privatekey of your address.
	 * @param to The address to send Krist to.
	 * @param amount The amount of Krist to send.
	 * @return The sent {@link me.lignum.jkrist.Transaction}.
	 * @throws KristAPIException Can be thrown.
     	*/
	public Transaction makeTransaction(String privatekey, String to, int amount) throws KristAPIException {
		JSONObject request = new JSONObject();
		request.put("privatekey", privatekey);
		request.put("to", to);
		request.put("amount", amount);

		JSONObject obj = new JSONObject(HTTPHelper.post(node + "/transactions", request));

		if (!obj.getBoolean("ok")) {
			throw new KristAPIException(obj.getString("error"));
		}

		return new Transaction(obj.getJSONObject("transaction"));
	}

	public long getWork() {
		JSONObject obj = new JSONObject(HTTPHelper.get(node + "/work"));
		return obj.getLong("work");
	}

	/**
	 * @return The current Krist MOTD.
	 */
	public MOTD getMOTD() {
		return new MOTD(new JSONObject(HTTPHelper.get(node + "/motd")));
	}
	
	public int getBaseBlockReward() {
		return new JSONObject(HTTPHelper.get(node + "/blocks/basevalue")).getInt("base_value");
	}
	
	public int getBlockReward() {
		return new JSONObject(HTTPHelper.get(node + "/blocks/value")).getInt("value");
	}
	
	/**
	 * 
	 * @param address The address to inspect.
	 * @return An {@link me.lignum.jkrist.Address} object. Or <code>null</code> if no such address was found.
	 */
	public Address getAddress(String address) {
		String json = HTTPHelper.get(node + "/addresses/" + address);

		if (json == null) {
			return null;
		}

		JSONObject obj = new JSONObject(json);
		return new Address(obj.getJSONObject("address"));
	}

	@SuppressWarnings("unchecked")
	private <T extends KristObject> T[] getList(int cap, String path, String elementsAttrib, Class<T> elemClass) {
		String json = HTTPHelper.get(node + path + "?limit=" + cap);

		if (json == null) {
			return null;
		}

		JSONObject obj = new JSONObject(json);
		T[] list = (T[]) Array.newInstance(elemClass, obj.getInt("count"));
		
		JSONArray elements = obj.getJSONArray(elementsAttrib);
		
		for (int i = 0; i < elements.length(); ++i) {
			JSONObject element = elements.getJSONObject(i);
			T kObject = null;
			
			try {
				kObject = elemClass.getConstructor(JSONObject.class).newInstance(element);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			list[i] = kObject;
		}
	
		return list;
	}
	
	/**
	 * Gets the rich list.
	 * @param cap The maximum amount of addresses to return.
	 * @return The addresses with the most Krist.
	 */
	public Address[] getTopAddresses(int cap) {
		return getList(cap, "/addresses/rich", "addresses", Address.class);
	}
	
	public Address[] getTopAddresses() {
		return getTopAddresses(DEFAULT_LIST_CAP);
	}
	
	public Address[] getAddresses(int cap) {
		return getList(cap, "/addresses", "addresses", Address.class);
	}
	
	public Address[] getAddresses() {
		return getAddresses(DEFAULT_LIST_CAP);
	}
	
	public Transaction getTransaction(int id) {
		String json = HTTPHelper.get(node + "/transactions/" + id);

		if (json == null) {
			return null;
		}

		JSONObject obj = new JSONObject(json);
		return new Transaction(obj.getJSONObject("transaction"));
	}
	
	public Transaction[] getTransactions(int cap) {
		return getList(cap, "/transactions", "transactions", Transaction.class);
	}
	
	public Transaction[] getTransactions() {
		return getTransactions(DEFAULT_LIST_CAP);
	}
	
	public Transaction[] getRecentTransactions(int cap) {
		return getList(cap, "/transactions/latest", "transactions", Transaction.class);
	}
	
	public Transaction[] getRecentTransactions() {
		return getRecentTransactions(DEFAULT_LIST_CAP);
	}
	
	public Block getBlock(int height) {
		String json = HTTPHelper.get(node + "/blocks/" + height);

		if (json == null) {
			return null;
		}

		JSONObject obj = new JSONObject(json);
		return new Block(obj.getJSONObject("block"));
	}
	
	public Block getLastBlock() {
		String json = HTTPHelper.get(node + "/blocks/last");

		if (json == null) {
			return null;
		}

		JSONObject obj = new JSONObject(json);
		return new Block(obj.getJSONObject("block"));
	}
	
	public Block[] getRecentBlocks(int cap) {
		return getList(cap, "/blocks/latest", "blocks", Block.class);
	}
	
	public Block[] getRecentBlocks() {
		return getRecentBlocks(DEFAULT_LIST_CAP);
	}
	
	public Block[] getBlocks(int cap) {
		return getList(cap, "/blocks", "blocks", Block.class);
	}
	
	public Block[] getBlocks() {
		return getBlocks(DEFAULT_LIST_CAP);
	}

	public Block[] getLowestBlocks(int cap) {
		return getList(cap, "/blocks/lowest", "blocks", Block.class);
	}

	public Block[] getLowestBlocks() {
		return getLowestBlocks(DEFAULT_LIST_CAP);
	}
}
