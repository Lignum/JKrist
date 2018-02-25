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

		String json = HTTPHelper.post(node + "/submit", request);

		if (json == null) {
			return null;
		}

		JSONObject obj = new JSONObject(json);

		if (!obj.optBoolean("ok")) {
			throw new KristAPIException(obj.optString("error", "N/A"));
		}

		if (!obj.optBoolean("success")) {
			return null;
		}

		return obj.has("block") ? new Block(obj.getJSONObject("block")) : null;
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

		String json = HTTPHelper.post(node + "/transactions", request);

		if (json == null) {
			return null;
		}

		JSONObject obj = new JSONObject(json);

		if (!obj.optBoolean("ok")) {
			throw new KristAPIException(obj.optString("error", "N/A"));
		}

		return obj.has("transaction") ? new Transaction(obj.getJSONObject("transaction")) : null;
	}

	public String login(String privateKey) throws KristAPIException {
		JSONObject request = new JSONObject();
		request.put("v", 2);
		request.put("privatekey", privateKey);

		String json = HTTPHelper.post(node + "/login", request);

		if (json == null) {
			return null;
		}

		JSONObject obj = new JSONObject(json);

		if (!obj.optBoolean("ok")) {
			throw new KristAPIException(obj.optString("error", "N/A"));
		}

		return obj.has("address") ? obj.getString("address") : null;
	}

	public long getWork() {
		String json = HTTPHelper.get(node + "/work");

		if (json == null) {
			return -1;
		}

		JSONObject obj = new JSONObject(json);
		return obj.optLong("work", -1);
	}

	/**
	 * @return The current Krist MOTD.
	 */
	public MOTD getMOTD() {
		String json = HTTPHelper.get(node + "/motd");

		if (json == null) {
			return null;
		}

		return new MOTD(new JSONObject(json));
	}
	
	public int getBaseBlockReward() {
		String json = HTTPHelper.get(node + "/blocks/basevalue");

		if (json == null) {
			return -1;
		}

		return new JSONObject(json).optInt("base_value", -1);
	}
	
	public int getBlockReward() {
		String json = HTTPHelper.get(node + "/blocks/value");

		if (json == null) {
			return -1;
		}

		return new JSONObject(json).optInt("value", -1);
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
		return obj.has("address") ? new Address(obj.getJSONObject("address")) : null;
	}

	@SuppressWarnings("unchecked")
	private <T extends KristObject> T[] getList(int cap, String path, String elementsAttrib, Class<T> elemClass) {
		String json = HTTPHelper.get(node + path + "?limit=" + cap);

		if (json == null) {
			return null;
		}

		JSONObject obj = new JSONObject(json);

		if (!obj.has(elementsAttrib)) {
			return null;
		}

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
		return obj.has("transaction") ? new Transaction(obj.getJSONObject("transaction")) : null;
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
		return obj.has("block") ? new Block(obj.getJSONObject("block")) : null;
	}
	
	public Block getLastBlock() {
		String json = HTTPHelper.get(node + "/blocks/last");

		if (json == null) {
			return null;
		}

		JSONObject obj = new JSONObject(json);
		return obj.has("block") ? new Block(obj.getJSONObject("block")) : null;
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
