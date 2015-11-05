package com.greco.beans;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;







import javax.mail.MessagingException;

import com.greco.services.CommunityDataProvider;
import com.greco.services.MailProvider;
import com.greco.services.UserDataProvider;
import com.greco.services.helpers.CommunityItem;
import com.greco.services.helpers.UserItem;
import com.greco.utils.MyLogger;
import com.greco.utils.Warnings;



public class SubscribeMeCBean {
	private static final MyLogger logger = MyLogger.getLogger(SubscribeMeCBean.class.getName());
	private SubscribeMeBBean subscribeMeBBean;  //Inyectado
	private UserDataProvider userDataProvider; //Inyectado.
	private CommunityDataProvider communityDataProviser; //Inyectado.
	private MailProvider mailProvider; //Inyectado
	
	/**
	 * Devuelve el nombre de la comunidad de la que se quiere hacer miembro.
	 * @return Nombre de la comunidad.
	 */
	public String getCommunityName(){
		CommunityItem communityItem=communityDataProviser.getCommunityById(subscribeMeBBean.getCommunityId());
		return communityItem.getName();
	}
	
	

	public String gotoStep1(){
		
		this.subscribeMeBBean.step=1;
	    return null;
	}
	
	public String gotoStep2(){
		this.subscribeMeBBean.step=2;
	    return null;
	}
	
	public String gotoStep3(){
		this.subscribeMeBBean.step=3;
	    return null;
	}
	
	
	public String submit() {
		//Creamos el usuario y se suscribimos a la comunidad como candidato (estado Pending), a la espera de que un 
		//administrador le confirme.
		UserItem userItem=new UserItem();
		userItem.setEmail(subscribeMeBBean.getEmail());
		userItem.setMydata(subscribeMeBBean.getMydata());
		userItem.setNickname(subscribeMeBBean.getNickname());
		userItem.setPassword(subscribeMeBBean.getPassword());
		userItem.setAdds(subscribeMeBBean.isAdds());
		//Creamos usuario
		int userId=userDataProvider.addUser(userItem);
		//Subscribimos el usuario a la comunidad.
		userDataProvider.subscribe(userId, subscribeMeBBean.getCommunityId(),subscribeMeBBean.getApplication());
		
		CommunityItem communityItem=communityDataProviser.getCommunityById(subscribeMeBBean.getCommunityId());
		if ( !communityItem.isMembercheck()) {
			try {
				mailProvider.sendSubscriptionApproved(userItem, communityItem);
			} catch (MessagingException e) {
				logger.log("013001");//013000=INFO|Error al enviar mail.
				e.printStackTrace();
			}
		}
		
		//Creamos mensaje para log
		//Preparamos el mensaje para el log.
		String msg="ID(" + userId + "), NICK(" + userItem.getNickname() +"), EMAIL(" + userItem.getEmail() + ")"  ;
		//Grabamos log
		logger.log("008000",msg);//INFO|Subscripción de nuevo usuario:
		
		
		
		return "/sections/subscriptioncomplete?communityid=" + subscribeMeBBean.getCommunityId() +"&faces-redirect=true";
	}	
	

	public String cancel(){
		return navigateLogin();
	}
	
	
	/**
	 * Volver a comlogin, manteniendo el parámetro de comunidad pasado inicialmente.
	 * @return URL de retorno
	 */
	public String navigateLogin(){
		return "/sections/login/logout?communityid=" + subscribeMeBBean.getCommunityId() +"&faces-redirect=true";
		
		
	}
	/**
	 * Comprueba que el nickname no está duplicado.
	 * @param fc
	 * @param c
	 * @param value
	 */
	public void validateName(FacesContext fc, UIComponent c, Object value) {
		if (   ((String)value).trim().isEmpty() )	
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
															Warnings.getString("newaccount.nick_required"),
															null) );
		
		if (   this.userDataProvider.isDuplicated(((String)value)) )	
		throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
														Warnings.getString("newaccount.duplicated_nick"),
														null) );
	}
	
	
	/**
	 * Comprueba que el emaiñl no está duplicado.
	 * @param fc
	 * @param c
	 * @param value
	 */
	public void validateUniqueEmailAddress(FacesContext fc, UIComponent c, Object value) {
		if (   this.userDataProvider.loadAdminCredentials(((String)value))!=null )	
		throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
														Warnings.getString("newaccount.duplicated_email"),
														null) );
	}
	
	//GETTERs y SETTERs
	
	
	public SubscribeMeBBean getSubscribeMeBBean() {
		return subscribeMeBBean;
	}

	public void setSubscribeMeBBean(SubscribeMeBBean subscribeMeBBean) {
		this.subscribeMeBBean = subscribeMeBBean;
	}

	public UserDataProvider getUserDataPRovider() {
		return userDataProvider;
	}

	public void setUserDataPRovider(UserDataProvider userDataProvider) {
		this.userDataProvider = userDataProvider;
	}


	public CommunityDataProvider getCommunityDataProviser() {
		return communityDataProviser;
	}


	public void setCommunityDataProviser(CommunityDataProvider communityDataProviser) {
		this.communityDataProviser = communityDataProviser;
	}
	
	public MailProvider getMailProvider() {
		return mailProvider;
	}

	public void setMailProvider(MailProvider mailProvider) {
		this.mailProvider = mailProvider;
	}
	
}
