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
	//Disponibilidad semanal
	private boolean mondayAvailable;
	private boolean tuesdayAvailable;
	private boolean wednesdayAvailable;
	private boolean thursdayAvailable;
	private boolean fridayAvailable;
	private boolean saturdayAvailable;
	private boolean sundayAvailable;
		
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
		this.mondayAvailable=true;
		this.tuesdayAvailable=true;
		this.wednesdayAvailable=true;
		this.thursdayAvailable=true;
		this.fridayAvailable=true;
		this.saturdayAvailable=true;
		this.sundayAvailable=true;
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




	public boolean isMondayAvailable() {
		return mondayAvailable;
	}




	public void setMondayAvailable(boolean mondayAvailable) {
		this.mondayAvailable = mondayAvailable;
	}




	public boolean isTuesdayAvailable() {
		return tuesdayAvailable;
	}




	public void setTuesdayAvailable(boolean tuesdayAvailable) {
		this.tuesdayAvailable = tuesdayAvailable;
	}




	public boolean isWednesdayAvailable() {
		return wednesdayAvailable;
	}




	public void setWednesdayAvailable(boolean wednesdayAvailable) {
		this.wednesdayAvailable = wednesdayAvailable;
	}




	public boolean isThursdayAvailable() {
		return thursdayAvailable;
	}




	public void setThursdayAvailable(boolean thursdayAvailable) {
		this.thursdayAvailable = thursdayAvailable;
	}




	public boolean isFridayAvailable() {
		return fridayAvailable;
	}




	public void setFridayAvailable(boolean fridayAvailable) {
		this.fridayAvailable = fridayAvailable;
	}




	public boolean isSaturdayAvailable() {
		return saturdayAvailable;
	}




	public void setSaturdayAvailable(boolean saturdayAvailable) {
		this.saturdayAvailable = saturdayAvailable;
	}




	public boolean isSundayAvailable() {
		return sundayAvailable;
	}




	public void setSundayAvailable(boolean sundayAvailable) {
		this.sundayAvailable = sundayAvailable;
	}
	

}
