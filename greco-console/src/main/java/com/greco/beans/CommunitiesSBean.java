package com.greco.beans;


import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import com.greco.services.AuthenticationProvider;
import com.greco.services.UserDataProvider;
import com.greco.services.helpers.CommunityItem;

public class CommunitiesSBean implements Serializable{
	
	private static final long serialVersionUID = -2492929364241535103L;
	
	
	
	private List<CommunityItem> myCommunities;
	private CommunityItem selectedItem=null;  //Comunidad seleccionada
	
	private UserDataProvider userDataProvider; //Inyectado.
	private UserSBean userBean; //Inyectado.
	
	
	
	@PostConstruct
	public void initialize() {
		//Obtengo la lista de comunidades.
		myCommunities=Arrays.asList(userDataProvider.getMyComunities(userBean));				
	}
	
	/**
	 * Devuelve true si existe una comunidad con idéntico nombre, país y comunidad.
	 * @param country
	 * @param zipcode
	 * @param name
	 * @return
	 */
	public boolean contains(int id,String country, String zipcode, String name){
		CommunityItem communityItem= new CommunityItem(id, country, zipcode, name);
		return myCommunities.contains(communityItem);
		
		
	}
		
	//GETTERs y SETTERs.

	public UserDataProvider getUserDataProvider() {
		return userDataProvider;
	}

	public void setUserDataProvider(UserDataProvider userDataProvider) {
		this.userDataProvider = userDataProvider;
	}

	public UserSBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserSBean userBean) {
		this.userBean = userBean;
	}

	public List<CommunityItem> getMyCommunities() {
		return myCommunities;
	}

	public void setMyCommunities(List<CommunityItem> myCommunities) {
		this.myCommunities = myCommunities;
	}
	public CommunityItem getSelectedItem() {
		return selectedItem;
	}
	
	/**
	 * Asigno el item de comunidad seleccionado. 
	 * @param selectedItem Item de comunidad. Si es null, se entiende que se ha seleccionado crear una nueva comunidad.
	 */
	public void setSelectedItem(CommunityItem selectedItem) {
		if (selectedItem == null) {
			//Asignamos valores por defecto ya que se considera una nueva comunidad.
			this.selectedItem=new CommunityItem(0, false,null,null,null,AuthenticationProvider.ROLE_ADMIN,null, null);
		} else	this.selectedItem = selectedItem;
	}
	
	/**
	 * Indica que ninguna comunidad está seleccionada. 
	 */
	public void unsetSelectedItem() {
		this.selectedItem = null;
	}
	
	
		
	
}
