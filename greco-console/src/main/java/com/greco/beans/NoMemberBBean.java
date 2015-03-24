package com.greco.beans;

import javax.annotation.PostConstruct;
import com.greco.services.UserDataProvider;
import com.greco.services.helpers.CommunityItem;

public class NoMemberBBean {
	UserSBean userSBean;  //Inyectado
	UserDataProvider userDataProvider;  //Inyectado
	CommunityItem[] communityItems;

	
	@PostConstruct
	public void initilize(){
		//Obtenemos las comunidades en las que está suscrito.
		communityItems=userDataProvider.getMyComunities(userSBean);		
	}
	
	//GETTERs y SETTERs
	
	public UserSBean getUserSBean() {
		return userSBean;
	}

	public void setUserSBean(UserSBean userSBean) {
		this.userSBean = userSBean;
	}

	public UserDataProvider getUserDataProvider() {
		return userDataProvider;
	}

	public void setUserDataProvider(UserDataProvider userDataProvider) {
		this.userDataProvider = userDataProvider;
	}

	public CommunityItem[] getCommunityItems() {
		return communityItems;
	}

	public void setCommunityItems(CommunityItem[] communityItems) {
		this.communityItems = communityItems;
	}
	
	

}
