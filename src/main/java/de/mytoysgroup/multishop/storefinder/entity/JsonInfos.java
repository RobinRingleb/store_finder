package de.mytoysgroup.multishop.storefinder.entity;

public class JsonInfos {
	
	private String headline;
	private String info;
	private Branches[] branches;
	
	public JsonInfos() {}
	
	public String getHeadline() {
		return headline;
	}
	
	public String getInfo() {
		return info;
	}
	
	public Branches[] getBranches() {
		return branches;
	}
}