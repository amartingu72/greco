package com.greco.beans;


import java.io.Serializable;

import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import com.greco.services.AuthenticationProvider;
import com.greco.services.UserCommunityDataProvider;

import com.greco.services.helpers.CommunityItem;

public class CommunitiesSBean implements Serializable{
	
	private static final long serialVersionUID = -2492929364241535103L;
	
	
	
	private List<CommunityItem> myCommunities;
	private CommunityItem selectedItem=null;  //Comunidad seleccionada
	
	private UserCommunityDataProvider userCommunityDataProvider; //Inyectado
	private UserSBean userBean; //Inyectado.
	
	
	
	@PostConstruct
	public void initialize() {
		//Obtengo la lista de comunidades.
		
		myCommunities=userCommunityDataProvider.getMyAdministeredCommunities(userBean.getItem());		
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
	
	/**
	 * Devuelve el item de comunidad correspondiente al id pasado como parámetro, dentro de la lista
	 * de comunidades que el usuario que ha iniciado la sesión administra.	  
	 * @param itemId
	 * @return
	 */
	public CommunityItem getCommunityItem(int itemId){
		Iterator<CommunityItem> it= myCommunities.iterator();
		boolean bfound=false;
		CommunityItem communityItem=null;
		while (!bfound && it.hasNext() ){
			communityItem=it.next();
			bfound=communityItem.getId()==itemId;
		}
		if (!bfound) communityItem=null;
		return communityItem;
	}
		
	//GETTERs y SETTERs.

	

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

	public UserCommunityDataProvider getUserCommunityDataProvider() {
		return userCommunityDataProvider;
	}

	public void setUserCommunityDataProvider(
			UserCommunityDataProvider userCommunityDataProvider) {
		this.userCommunityDataProvider = userCommunityDataProvider;
	}
	
	
		
	
}
