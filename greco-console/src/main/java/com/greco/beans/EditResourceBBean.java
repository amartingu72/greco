package com.greco.beans;

import javax.annotation.PostConstruct;

import com.greco.services.helpers.ResourceItem;
import com.greco.services.helpers.TimeUnitItem;

public class EditResourceBBean {
	
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
	
	private ResourcesSBean resourcesSBean; //Inyectado
	
	@PostConstruct
	public void initialize() {
		ResourceItem resourceItem=resourcesSBean.getSelectedResource();
		this.id=resourceItem.getId();
		this.availableFromTime=resourceItem.getAvailableFromTime();
		this.availableToTime=resourceItem.getAvailableToTime();
		this.beforehand=resourceItem.getBeforehand();
		this.description=resourceItem.getDescription();
		this.maxtime=resourceItem.getMaxtime();
		this.mintime=resourceItem.getMintime();
		this.name=resourceItem.getName();
		this.type=resourceItem.getType();
		this.timeunit=TimeUnitItem.toStringId(resourceItem.getTimeunit());
		this.beforehandTU=TimeUnitItem.toStringId(resourceItem.getBeforehandTU());
		this.mondayAvailable=resourceItem.isAvailableOnMonday();
		this.tuesdayAvailable=resourceItem.isAvailableOnTuesday();
		this.wednesdayAvailable=resourceItem.isAvailableOnWednesday();
		this.thursdayAvailable=resourceItem.isAvailableOnThursday();
		this.fridayAvailable=resourceItem.isAvailableOnFriday();
		this.saturdayAvailable=resourceItem.isAvailableOnSaturday();
		this.sundayAvailable=resourceItem.isAvailableOnSunday();
		
	}
	
	

	//GETTERs y SETTERs

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
	
	public String getBeforehandTUName() {
		return TimeUnitItem.toString(Integer.parseInt(beforehandTU));
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
	
	public String getTimeunitName(){
		return TimeUnitItem.toString(Integer.parseInt(timeunit));
	}


	public void setTimeunit(String timeunit) {
		this.timeunit = timeunit;
	}


	public ResourcesSBean getResourcesSBean() {
		return resourcesSBean;
	}


	public void setResourcesSBean(ResourcesSBean resourcesSBean) {
		this.resourcesSBean = resourcesSBean;
	}




	

}
