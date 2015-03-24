package com.greco.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import org.primefaces.model.LazyDataModel;

import com.greco.services.UserCommunityDataProvider;
import com.greco.services.helpers.CommunityItem;
import com.greco.services.helpers.LazyMembersDataModel;
import com.greco.services.helpers.MemberItem;
import com.greco.services.helpers.ResourceItem;

public class EditCommunityBBean implements Serializable{
	
	private static final long serialVersionUID = 8805026487804632813L;
	
	private int id;
	private String name;
	private String country;
	private String zipcode;
	private String mydata;
	private boolean available;
	private String joiningDate;
	private String profiles;
	private boolean saveBtnVisible;
	
	
	private List <ResourceItem> myResources; //inyectado
	
	private LazyDataModel<MemberItem> members;
	
	private UserCommunityDataProvider userCommunityDataProvider; //inyectado
	
	
	
	//Recurso seleccionado cuando se pulsa editar.
	private ResourceItem selectedItem;
	
	//Miembro seleccionado cuando se pulsa eliminar (o dar de baja).
	private MemberItem selectedMember;
	
	
	@PostConstruct
	public void initialize() {
		//Obtengo la comunidad seleccionada de Communities
		CommunitiesSBean comms = (CommunitiesSBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("communitiesSBean");
		CommunityItem item=comms.getSelectedItem();
		
		//Inicializo el objeto que gestionará la tabla de miembros.
		members=new LazyMembersDataModel(item, userCommunityDataProvider);
						
		this.id=item.getId();
		this.name=item.getName();
		this.country=item.getCountry();
		this.zipcode=item.getZipcode();
		this.mydata=item.getMyData();
		this.available=item.isAvailable();
		this.joiningDate=item.getJoiningDate();
		this.profiles=item.getProfiles();
		
		//Botón 'Guardar' visible.
		this.saveBtnVisible=true;
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
	
	public List<ResourceItem> getMyResources() { 
		return myResources; 
	}
		
	public void setMyResources(List<ResourceItem> myResources) {
		this.myResources = myResources;
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

	public String getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}

	public String getProfiles() {
		return profiles;
	}

	public void setProfiles(String profiles) {
		this.profiles = profiles;
	}

	public LazyDataModel<MemberItem> getMembers() {
		return members;
	}

	public void setMembers(LazyDataModel<MemberItem> members) {
		this.members = members;
	}

	public UserCommunityDataProvider getUserCommunityDataProvider() {
		return userCommunityDataProvider;
	}

	public void setUserCommunityDataProvider(
			UserCommunityDataProvider userCommunityDataProvider) {
		this.userCommunityDataProvider = userCommunityDataProvider;
	}

	public boolean isSaveBtnVisible() {
		return saveBtnVisible;
	}

	public void setSaveBtnVisible(boolean saveBtnVisible) {
		this.saveBtnVisible = saveBtnVisible;
	}

	public MemberItem getSelectedMember() {
		return selectedMember;
	}

	public void setSelectedMember(MemberItem selectedMember) {
		this.selectedMember = selectedMember;
	}
	
}
