package com.greco.beans;


public class HeaderCBean {
	private UserSBean userLogged;  //Inyectado
	
	/**
	 * Editar cuenta.
	 * @return
	 */
	public String editAccount(){
		
		return "editAccount";
	}
	
	/**
	 * Log out.
	 * @return 
	 */
	public String exit(){
		String ret;
		ret="/sections/login/logout?communityid=";
		ret+=userLogged.getCommunityId();
		ret+="&faces-redirect=true";
		return ret;
	}
	
	//GETTERs y SETTERs
	
	public UserSBean getUserLogged() {
		return userLogged;
	}
	public void setUserLogged(UserSBean userLogged) {
		this.userLogged = userLogged;
	}
	
	
}
