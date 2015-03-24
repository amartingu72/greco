package com.greco.beans;

import javax.annotation.PostConstruct;
import com.greco.services.helpers.ResourceItem;

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
		this.timeunit=resourceItem.getTimeunit();
		this.beforehandTU=resourceItem.getBeforehandTU();
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


	public ResourcesSBean getResourcesSBean() {
		return resourcesSBean;
	}


	public void setResourcesSBean(ResourcesSBean resourcesSBean) {
		this.resourcesSBean = resourcesSBean;
	}

}
