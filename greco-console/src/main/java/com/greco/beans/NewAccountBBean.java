package com.greco.beans;

import javax.annotation.PostConstruct;


/**
 * Backing bean para nueva cuenta.
 * @author Alberto Martín
 *
 */
public class NewAccountBBean {
	
	private String nickname;
	private String password;
	private String email;
	private String mydata;
	private boolean agree; //Términos y condiciones.
	private boolean adds; //Indicador de si el usuario acepta o no correo con publicidad.
	//El botón de login es una opción de volver al lógin cuando, al registrarse, observamos que el usuario
	//ya existe.
	private boolean showLoginBtn;
	private String actCode; //Código de activación.
	
	
	
	@PostConstruct
	public void initialize(){
		
		this.showLoginBtn=false;
		this.adds=false;
		this.agree=false;
	}
	
	
	
	//GETTERs y SETTERs
	
	public boolean isAdds() {
		return adds;
	}



	public void setAdds(boolean adds) {
		this.adds = adds;
	}

	public boolean isAgree() {
		return agree;
	}

	public void setAgree(boolean agree) {
		this.agree = agree;
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

	public String getActCode() {
		return actCode;
	}

	public void setActCode(String actCode) {
		this.actCode = actCode;
	}
	

	
}
