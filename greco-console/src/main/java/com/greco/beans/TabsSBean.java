package com.greco.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

public class TabsSBean implements Serializable{

	private static final long serialVersionUID = 816538909139706100L;

	
	private String selectedTab;
	
	@PostConstruct	
	public void initialize() {
		this.selectedTab="reservations";
	}
	
	public String getSelectedTab() { 
		return selectedTab; 
	}

	public String changeTab(String newValue) {
		 String ret;
		 //Si selecciono logout, volver al inicio.
		 if (newValue.equals("logout"))
		 {
			UserSBean userBean=(UserSBean)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userLogged");
			//Comprobamos si el inicio es login o comlogin.
			if ( userBean.getCommunityId() != UserSBean.UNDEFINED_COMMUNITY ) 
				ret= "/sections/login/logout?communityid=" + userBean.getCommunityId() + "&";
			else 
				ret= "/sections/login/logout?"; 
		 }
		 else {
			 selectedTab = newValue;
		     ret= "/sections/" + newValue + "?";	 
		 }
		 return ret + "faces-redirect=true";
	  }

	

	
	
	
}
