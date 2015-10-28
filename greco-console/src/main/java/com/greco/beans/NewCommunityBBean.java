package com.greco.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import com.greco.services.helpers.ResourceItem;

public class NewCommunityBBean implements Serializable{
	
	private static final long serialVersionUID = 8805026487804632813L;
	private int id;
	private String name;
	private String country;
	private String zipcode;
	private String mydata;
	private boolean available;
	private boolean membercheck;
	
	
	
	
	//Recurso seleccionado cuando se pulsa editar.
	private ResourceItem selectedItem;
	
	
	@PostConstruct
	public void initialize() {
		
		available=true;
		membercheck=false;
		/*//Obtengo la comunidad seleccionada de Communities
		CommunitiesSBean comms = (CommunitiesSBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("communitiesSBean");
		CommunityItem item=comms.getSelectedItem();
		this.name=item.getName();
		this.country=item.getCountry();
		this.zipcode=item.getZipcode();
		this.mydata=item.getMyData();
		this.available=item.isAvailable();*/
	}
	
	//GETTERs y SETTERs	

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
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
	
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}

	
	public String getZipcode() {
		return zipcode;
	}
	
	

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getMydata() {
		return mydata;
	}
	public void setMydata(String mydata) {
		this.mydata = mydata;
	}

	public ResourceItem getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(ResourceItem selectedItem) {
		this.selectedItem = selectedItem;
	}

	public boolean isMembercheck() {
		return membercheck;
	}

	public void setMembercheck(boolean membercheck) {
		this.membercheck = membercheck;
	}
	
	
	
}
