package com.greco.beans;


import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import com.greco.services.CommunityDataProvider;
import com.greco.services.helpers.CommunityItem;



public class LoginBBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String email;
	private String password;
	private int communityId; 
	private CommunityItem communityItem;
	private CommunityDataProvider communityDataProvider;

	/**
	 * Valida que el parámetro communityid (GET request), tiene una comunidad reconocida.
	 * Solo se invoca cuando se hace login desde una comunidad y nunca desde la página de administración.
	 * @return
	 */
	public String checkParam(){
		String ret=null;
		//Si el ID de comunidad pasasdo como parámetro (GET), tiene un valor válido, le buscamos.
		if (communityId != -1)
			communityItem=communityDataProvider.getCommunityById(communityId);
		if (communityItem == null) ret="notfound"; 
		return ret;
	}
	
	@PostConstruct
	public void init(){
		//Si la comunidad viene en un parámetro de la URL la utilizo.
		Map<String,String> params =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String comId=params.get("communityid");
		if ( (comId != null) && (comId != "0")   ) {
			try{
				communityId= Integer.parseInt(comId);
			} catch (NumberFormatException e){
				//Asigno -1 para indicar que el parámetro tiene un valor erroneo.
				communityId=-1;
			}
			
				
		}
			
	}
	
	
	
	/**
	 * Igual que getCommunity pero no verifica si ha venido un parámetro por URL.
	 * @return
	 */
	public int getSelectedCommunity(){
		return communityId;
	}
	
	
	public int getCommunityId(){
		return communityId;
		
	}
	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email=email;
	}
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password=password;
	}



	public CommunityItem getCommunityItem() {
		return communityItem;
	}



	public void setCommunityItem(CommunityItem communityItem) {
		this.communityItem = communityItem;
	}



	public CommunityDataProvider getCommunityDataProvider() {
		return communityDataProvider;
	}



	public void setCommunityDataProvider(CommunityDataProvider communityDataProvider) {
		this.communityDataProvider = communityDataProvider;
	}
	
	
		
	
}
