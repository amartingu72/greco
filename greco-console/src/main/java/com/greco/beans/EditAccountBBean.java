package com.greco.beans;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import com.greco.services.UserCommunityDataProvider;
import com.greco.services.helpers.MemberItem;

public class EditAccountBBean implements Serializable{

	private static final long serialVersionUID = 2324897865750418614L;
    private static final String FAKE_PASSWORD="_:lp_.*[";
	
	
	private int id;
	private String nickname;
	private String password;
	private String email;
	private String profile="USER";
	private String mydata; //Sin uso por el momento
	private String dni;
	private List<MemberItem> subscriptions;
	private boolean pwdUpdated;
	
	private UserSBean userBean; //Inyectado
	private UserCommunityDataProvider userCommunityDataProvider; //Inyectado

		
	@PostConstruct
	public void initialize() {
		this.id=userBean.getId();
        this.nickname=userBean.getNickname();
        this.email=userBean.getEmail();
        this.mydata=userBean.getMydata();
        //Así simulo que la contraseña se ha recuperado.
        this.password=FAKE_PASSWORD;  //Hacemos esto para salvaguardar la PWD del usuario. 
        this.pwdUpdated=false;
        this.subscriptions=userCommunityDataProvider.getMyCommunities(userBean.getItem());
        
	}
	
	/**
	 * En el caso de que se invoque desde un site, devuelve el mensaje de suscripción. Si se invoca desde la consola, devolverá null.
	 * @return
	 */
	public String getApplication(){
		String ret=null;
		if (userBean.getCommunityId() != 0){
			Iterator<MemberItem> it=subscriptions.iterator();
			boolean found=false;
			
			MemberItem memberItem=null;
			while (it.hasNext() && !found){
				memberItem=it.next();
				if ( userBean.getCommunityId()==memberItem.getCommunityItem().getId() ) {
					ret=memberItem.getApplication();
					found=true;
				}
			}
		}
		return ret;
	}
	
	
	/**
	 * Indica si el usuario tiene, al menos, una suscripción.
	 * @return
	 */
	public boolean isSubscriptionsPending(){
		return this.subscriptions.isEmpty();
	}
	
	//GETTERs y SETTERs
	
	public void setPassword(String password) {
		this.pwdUpdated=!password.equals(FAKE_PASSWORD);  //Comprueba si la contraseña se ha modificado o no.
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


	public List<MemberItem> getSubscriptions() {
		return subscriptions;
	}


	public void setSubscriptions(List<MemberItem> subscriptions) {
		this.subscriptions = subscriptions;
	}


	public UserCommunityDataProvider getUserCommunityDataProvider() {
		return userCommunityDataProvider;
	}


	public void setUserCommunityDataProvider(
			UserCommunityDataProvider userCommunityDataProvider) {
		this.userCommunityDataProvider = userCommunityDataProvider;
	}
	
	

	
}
