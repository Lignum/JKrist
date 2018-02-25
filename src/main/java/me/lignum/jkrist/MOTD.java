package me.lignum.jkrist;

import java.util.Date;

import org.json.JSONObject;

public class MOTD extends KristObject {
	private String message;
	private String psa;
	private String schroedingersCatState;
	private Date modifiedDate;
	
	protected MOTD(JSONObject obj) {
		super(obj);
	
		this.message = obj.getString("motd");
		this.psa = obj.getString("psa");
		this.schroedingersCatState = obj.getString("schrodingers_cat");

		this.modifiedDate = Utils.parseISO8601(obj.getString("set"));
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getPSA() {
		return psa;
	}
	
	public String getSchroedingersCatState() {
		return schroedingersCatState;
	}
	
	public Date getDateModified() {
		return modifiedDate;
	}
}
