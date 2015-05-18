package com.greco.beans;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;


/**
 * Bean para funcionalidad de recuperación de pwd olvidada.
 * @author Alberto Martín
 *
 */
public class PwdForgottenBBean {
	//Para el caso de que la petición de pwd venga de comlogin.
	//Es necesaria para conocer el punto de retorno cuando termine.
	private int communityId;
	private String email;

	@PostConstruct
	public void initialize(){
		String sCom=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("communityid");
		if ( sCom != null ) //Login realizado como comlogin.
			communityId=Integer.parseInt(sCom);
		else communityId=0; //No hay comunidad debido a que se hizo login como adm
	}
	
	//GETTERs y SETTERs
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getCommunityId() {
		return communityId;
	}

	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}
	
	

}
