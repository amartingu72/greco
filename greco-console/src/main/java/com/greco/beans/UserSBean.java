package com.greco.beans;

import java.io.Serializable;

import com.greco.services.helpers.MemberItem;
import com.greco.services.helpers.UserItem;

/**
 * Bean para gestión de modelo. 
 * @author Alberto Martín
 *
 */
public class UserSBean implements Serializable{

	private static final long serialVersionUID = 2448903992578433630L;
	public static final int UNDEFINED_COMMUNITY=-1;
	private int id;
	private String nickname;
	private String email;
	private String mydata;
	private String password;
	private boolean adds; //Indica si el usuario acepta o no el envío de consejos publicitarios.
	
	//Perfil en la comunidad que hizo login
	private String profile;
	/**
	 * Identificador de sesión donde se hizo la validación de usuario.
	 */
	private String sessionId;
	

	//Id de la comunidad con la que hizo login (comlogin). -1 sin hizo login sin id de comunidad.
	private int communityId;
	
	
	
	
	public UserItem getItem(){
		UserItem userItem=new UserItem();
		userItem.setEmail(email);
		userItem.setId(id);
		userItem.setMydata(mydata);
		userItem.setNickname(nickname);
		userItem.setProfile(profile);
		userItem.setAdds(adds);
		return userItem;
	}
	
	public UserSBean() {
		
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
	
	/**
	 * Indica si el usuario accedió a través de página de usuario (comlogin), y, por tanto, se indicaba comunidad o como administrador, sin indicarla.
	 * @return Si o no.
	 */
	public boolean isAdmin() {
		return this.communityId==UNDEFINED_COMMUNITY;
	}
	
	public int getCommunityId() {
		return communityId;
	}
	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}
	/**
	 * Copia las propiedades del objeto pasado como parámetro. Si el objeto es null, no hace nada.
	 * @param obj Objeto a copiar.
	 */
	public void copy(UserSBean obj) {
		if ( obj != null) {
			this.communityId=obj.getCommunityId();
			this.email=obj.getEmail();
			this.id=obj.getId();
			this.mydata=obj.getMydata();
			this.nickname=obj.getNickname();
			this.profile=obj.getProfile();
			this.adds=obj.isAdds();
		} 
		 
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	/**
	 * Indica si el miembro pasado como parámetro coincide con el usuario logado
	 * Se utiliza para renderizar el botón de borrado de la pestaña de miembros, en la 
	 * edición de comunidad. Un usuario no se puede eliminar a sí mismo desde ahí.
	 * @param memberItem
	 * @return Si o no.
	 */
	public boolean isMe(MemberItem memberItem){
		return memberItem.getId()==this.id;		
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdds() {
		return adds;
	}

	public void setAdds(boolean adds) {
		this.adds = adds;
	}
	
	
	


	
}
