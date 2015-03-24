package com.greco.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;

public class EditAccountBBean implements Serializable{

	private static final long serialVersionUID = 2324897865750418614L;
    private static final String FAKE_PASSWORD="_:lp_.*[";
	
	
	private int id;
	private String nickname;
	private String password;
	private String email;
	private String profile="USER";
	private String mydata;
	private String dni;
	private boolean pwdUpdated;
	
	private UserSBean userBean; //Inyectado

		
	@PostConstruct
	public void initialize() {
		this.id=userBean.getId();
        this.nickname=userBean.getNickname();
        this.email=userBean.getEmail();
        this.mydata=userBean.getMydata();
        //As� simulo que la contrase�a se ha recuperado.
        this.password=FAKE_PASSWORD;  //Hacemos esto para salvaguardar la PWD del usuario. 
        this.pwdUpdated=false;
        
	}
	
	
	//GETTERs y SETTERs
	
	public void setPassword(String password) {
		this.pwdUpdated=!password.equals(FAKE_PASSWORD);  //Comprueba si la contrase�a se ha modificado o no.
		this.password = password;
	}
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
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
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getMydata() {
		return mydata;
	}
	public void setMydata(String mydata) {
		this.mydata = mydata;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public UserSBean getUserBean() {
		return userBean;
	}
	public void setUserBean(UserSBean userBean) {
		this.userBean = userBean;
	}


	public boolean isPwdUpdated() {
		return pwdUpdated;
	}


	public void setPwdUpdated(boolean pwdUpdated) {
		this.pwdUpdated = pwdUpdated;
	}
	
}
