package com.greco.beans;


import java.io.Serializable;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;



public class LoginBBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String email;
	private String password;
	private int communityId; 

	
	
	@PostConstruct
	public void init(){
		//Si la comunidad viene en un parámetro de la URL la utilizo.
		Map<String,String> params =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String comId=params.get("communityid");
		if ( (comId != null) && (comId != "0")   ) 
			communityId= Integer.parseInt(comId);
			
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
	
		
	
}
