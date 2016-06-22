package me.lignum.jkrist;

import org.json.JSONObject;

public abstract class KristObject {
	private JSONObject json;
	
	public KristObject(JSONObject json) {
		this.json = json;
	}
	
	public String getJSONString() {
		return json.toString();
	}
}
