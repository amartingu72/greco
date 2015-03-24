package com.greco.services.helpers;

public class CountryItem {
	private int id;
	private String name;

	
	public CountryItem(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	@Override
	public String toString(){
		return name;
	}
	
	//GETTERs y SETTERs
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	
}
