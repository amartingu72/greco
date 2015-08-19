package com.greco.beans;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;


/**
 * Backing bean para nueva cuenta.
 * @author Alberto Martín
 *
 */
public class SubscribeMeBBean {
	private int communityId;
	private String nickname;
	private String password;
	private String email;
	private String mydata;
	//El botón de login es una opción de volver al lógin cuando, al registrarse, observamos que el usuario
	//ya existe.
	private boolean showLoginBtn;
	int step; //Indica el paso en que nos encontramos
	
	
	
	@PostConstruct
	public void initialize(){
		Map<String,String> map=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String sCom=map.get("communityid");
		communityId=Integer.parseInt(sCom);
		String sStep=map.get("step");
		this.showLoginBtn=false;
		step=Integer.parseInt(sStep); //Situamos al usuario en el primer paso indicado.
	}
	
	
	
	//GETTERs y SETTERs
	
	public int getCommunityId() {
		return communityId;
	}
	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMydata() {
		return mydata;
	}
	public void setMydata(String mydata) {
		this.mydata = mydata;
	}

	public boolean isShowLoginBtn() {
		return showLoginBtn;
	}

	public void setShowLoginBtn(boolean showLoginBtn) {
		this.showLoginBtn = showLoginBtn;
	}

	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	

	
}
