package com.greco.beans;

import javax.annotation.PostConstruct;

public class NewResourceBBean {
	
	private int id;
	private String name;
	private String description;
	private int mintime;
	private int maxtime;
	private String availableFromTime;
	private String availableToTime;
	private int beforehand;
	private String beforehandTU; 
	private String type;
	private String timeunit;
		
	@PostConstruct
	public void initialize() {
		this.availableFromTime="00:00";
		this.availableToTime="23:59";
		this.beforehand=24;
		this.description="";
		this.timeunit="";
		this.beforehandTU="";
		this.maxtime=1;
		this.mintime=1;
		this.name="";
		this.type="";
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


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getMintime() {
		return mintime;
	}


	public void setMintime(int mintime) {
		this.mintime = mintime;
	}


	public int getMaxtime() {
		return maxtime;
	}


	public void setMaxtime(int maxtime) {
		this.maxtime = maxtime;
	}


	public String getAvailableFromTime() {
		return availableFromTime;
	}


	public void setAvailableFromTime(String availableFromTime) {
		this.availableFromTime = availableFromTime;
	}


	public String getAvailableToTime() {
		return availableToTime;
	}


	public void setAvailableToTime(String availableToTime) {
		this.availableToTime = availableToTime;
	}


	public int getBeforehand() {
		return beforehand;
	}


	public void setBeforehand(int beforehand) {
		this.beforehand = beforehand;
	}


	public String getBeforehandTU() {
		return beforehandTU;
	}


	public void setBeforehandTU(String beforehandTU) {
		this.beforehandTU = beforehandTU;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getTimeunit() {
		return timeunit;
	}


	public void setTimeunit(String timeunit) {
		this.timeunit = timeunit;
	}

}
