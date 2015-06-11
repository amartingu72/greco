package com.greco.beans;

import javax.annotation.PostConstruct;

import com.greco.services.CommunityDataProvider;
import com.greco.services.helpers.CommunityItem;
import com.greco.services.helpers.ResourceItemGroup;

public class MyReservationsBBean {
	//Grupo lista donde cada elemento (grupo), es un grupo de recursos del mismo tipo.
	private ResourceItemGroup[] resourceItemGroups;
	private CommunityDataProvider communityDataProvider; //Inyectado.
	private UserSBean userSBean; //Inyectado
	

	@PostConstruct
	public void init(){
		//Cargo la lista de grupos de recursos.
		CommunityItem communityItem=communityDataProvider.getCommunityById(userSBean.getId());
		resourceItemGroups=communityDataProvider.getResources(communityItem);
	}

	 	
	//GETTERS y SETTERs
	public ResourceItemGroup[] getResourceItemGroups() {
		return resourceItemGroups;
	}

	public void setResourceItemGroups(ResourceItemGroup[] resourceItemGroups) {
		this.resourceItemGroups = resourceItemGroups;
	}


	public CommunityDataProvider getCommunityDataProvider() {
		return communityDataProvider;
	}


	public void setCommunityDataProvider(CommunityDataProvider communityDataProvider) {
		this.communityDataProvider = communityDataProvider;
	}


	public UserSBean getUserSBean() {
		return userSBean;
	}


	public void setUserSBean(UserSBean userSBean) {
		this.userSBean = userSBean;
	}
	
	 
}
