package com.greco.beans;

import com.greco.services.UserDataProvider;
import com.greco.utils.MyLogger;

public class NoMemberCBean {
	private static final MyLogger logger = MyLogger.getLogger(SubscribeMeCBean.class.getName());
	NoMemberBBean noMemberBBean; //Inyectado
	private UserDataProvider userDataProvider; //Inyectado.
	
	public String subscribe(){
		//Suscribimos al usuario en la comunidad como pendiente.
		//Subscribimos el usuario a la comunidad.
		UserSBean user=noMemberBBean.getUserSBean();
		userDataProvider.subscribe(user.getId(), user.getCommunityId(),noMemberBBean.getApplication());
				
		//Creamos mensaje para log
		
		logger.log("011000","");//INFO|Subscripción solicitada:
		return "completed";
	}
	
	public UserDataProvider getUserDataProvider() {
		return userDataProvider;
	}

	public void setUserDataProvider(UserDataProvider userDataProvider) {
		this.userDataProvider = userDataProvider;
	}

	public String cancel(){
		String ret="/sections/login/logout?communityid=" + this.noMemberBBean.getUserSBean().getCommunityId() +"&faces-redirect=true";
		return ret;
		
	}
	
	//GETTERs y SETTERs

	public NoMemberBBean getNoMemberBBean() {
		return noMemberBBean;
	}

	public void setNoMemberBBean(NoMemberBBean noMemberBBean) {
		this.noMemberBBean = noMemberBBean;
	}

}
