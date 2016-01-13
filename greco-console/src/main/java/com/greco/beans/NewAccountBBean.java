package com.greco.beans;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;



/**
 * Backing bean para nueva cuenta.
 * @author Alberto Mart�n
 *
 */
public class NewAccountBBean {
	private int id; //Tendr� un valor distintso de -1 solo si procese de un "back" de la p�gina siguiente. 
	private String nickname;
	private String password;
	private String email;
	private String mydata;
	private boolean agree; //T�rminos y condiciones.
	private boolean adds; //Indicador de si el usuario acepta o no correo con publicidad.
	//El bot�n de login es una opci�n de volver al l�gin cuando, al registrarse, observamos que el usuario
	//ya existe.
	private boolean showLoginBtn;
	private String actCode; //C�digo de activaci�n.
	
	
	
	
	@PostConstruct
	public void initialize(){
		
		this.showLoginBtn=false;
		this.adds=false;
		this.agree=false;
		ExternalContext ec=FacesContext.getCurrentInstance().getExternalContext();
		//Si se instancia procedente del bot�n "back" del segundo paso de creaci�n de cuenta, cargamos los datos de UserSBean 
		Map<String,String> params =  ec.getRequestParameterMap();
		
		String action = params.get("action");
		if ( (action!=null) && (action.equals("back")) ) {
			UserSBean userSBean=(UserSBean)ec.getSessionMap().get("userLogged");
			id=userSBean.getId();
			nickname=userSBean.getNickname();
			password=params.get("pwd");
			email=userSBean.getEmail();
			mydata=userSBean.getMydata();
			adds=userSBean.isAdds();
			agree=true;
		}
		else id=-1;
			
		
	}
	/**
	 * Indica si a la p�gina newaccount llegamos por primera vez o hemos hecho un back desde el step2.
	 * @return
	 */
	public boolean isReedited(){
		return this.getId()!=-1;
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



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}
	

	
}
