package com.greco.beans;

import javax.mail.MessagingException;

import com.greco.services.CommunityDataProvider;
import com.greco.services.MailProvider;
import com.greco.services.UserDataProvider;
import com.greco.services.helpers.CommunityItem;
import com.greco.services.helpers.UserItem;
import com.greco.utils.MyLogger;

public class NoMemberCBean {
	private static final MyLogger logger = MyLogger.getLogger(SubscribeMeCBean.class.getName());
	private NoMemberBBean noMemberBBean; //Inyectado
	private UserDataProvider userDataProvider; //Inyectado.
	private MailProvider mailProvider; //Inyectado
	private CommunityDataProvider communityDataProvider; //Inyectado
	
	
	public String subscribe(){
		//Suscribimos al usuario en la comunidad como pendiente.
		//Subscribimos el usuario a la comunidad.
		UserSBean user=noMemberBBean.getUserSBean();
		userDataProvider.subscribe(user.getId(), user.getCommunityId(),noMemberBBean.getApplication());
		
		
		//Si la comunidad no requiere aprobación de un administrador para suscribirse, enviamos correo.
		
		CommunityItem communityItem=communityDataProvider.getCommunityById(user.getCommunityId());
		if ( !communityItem.isMembercheck()) {
			UserItem userItem=user.getItem();
			try {
				mailProvider.sendSubscriptionApproved(userItem, communityItem);
			} catch (MessagingException e) {
				logger.log("013001");//013000=INFO|Error al enviar mail.
				e.printStackTrace();
			}
		}
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

	public MailProvider getMailProvider() {
		return mailProvider;
	}

	public void setMailProvider(MailProvider mailProvider) {
		this.mailProvider = mailProvider;
	}

	public CommunityDataProvider getCommunityDataProvider() {
		return communityDataProvider;
	}

	public void setCommunityDataProvider(CommunityDataProvider communityDataProvider) {
		this.communityDataProvider = communityDataProvider;
	}
	
}
