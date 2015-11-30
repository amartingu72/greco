package com.greco.beans;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;







import javax.mail.MessagingException;



import com.greco.services.MailProvider;
import com.greco.services.UserCommunityDataProvider;
import com.greco.services.helpers.CommunityItem;
import com.greco.utils.EMail;
import com.greco.utils.MyLogger;
import com.greco.utils.Warnings;


public class ContactCBean {
	private static final MyLogger log = MyLogger.getLogger(ContactCBean.class.getName());
	
	private LoginBBean loginBBean; //Inyectado
	private UserCommunityDataProvider userCommunityDataProvider; //Inyectado
	private ContactBBean contactBBean; //Inyectado
	private MailProvider mailProvider; //Inyectado
	
	public String sendMessage(){
		EMail email=new EMail(contactBBean.getEmail());
		if ( email.isOk() ) {
			//Enviar correo
			contactBBean.setCollapsed(true);		
			
			CommunityItem communityItem=loginBBean.getCommunityItem();
			try {
				mailProvider.sendMessageToAdmin(userCommunityDataProvider.getAdmindList(communityItem), email, communityItem, contactBBean.getMessage());
				FacesContext context = FacesContext.getCurrentInstance();
		        context.addMessage("mainForm:contactEmail",new FacesMessage(FacesMessage.SEVERITY_INFO, Warnings.getString("contact.sent"),  
		        		Warnings.getString("contact.sent_details") ) );
		        this.contactBBean.clear();  //Vaciamos el contenido.
		        
			} catch (MessagingException e) {
				
				e.printStackTrace();
				FacesContext context = FacesContext.getCurrentInstance();
		        context.addMessage("mainForm:contactEmail",new FacesMessage(FacesMessage.SEVERITY_INFO, Warnings.getString("contact.email_error"),  
		        		Warnings.getString("contact.email_error_details") ) );
		        		
		        //Preparamos log
		        String msg="(EMAIL)" + email.toString() + " (MSG) " + contactBBean.getMessage();		
		        log.log("015000",msg);//INFO|Error al enviar un mensaje a los adminstradores
			}
			
	     
	        
		}
		else {
			contactBBean.setCollapsed(false);
			//Poner mensaje indicando que hay un problema.
			FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage("mainForm:contactEmail",new FacesMessage(FacesMessage.SEVERITY_INFO, Warnings.getString("contact.email_format_error"),  
	        		Warnings.getString("contact.email_format_error_details") ) );
		}
		return null;
	}
	
	//GETTERs y SETTERs

	public ContactBBean getContactBBean() {
		return contactBBean;
	}

	public void setContactBBean(ContactBBean contactBBean) {
		this.contactBBean = contactBBean;
	}

	public MailProvider getMailProvider() {
		return mailProvider;
	}

	public void setMailProvider(MailProvider mailProvider) {
		this.mailProvider = mailProvider;
	}
	
	public LoginBBean getLoginBBean() {
		return loginBBean;
	}

	public void setLoginBBean(LoginBBean loginBBean) {
		this.loginBBean = loginBBean;
	}

	public UserCommunityDataProvider getUserCommunityDataProvider() {
		return userCommunityDataProvider;
	}

	public void setUserCommunityDataProvider(
			UserCommunityDataProvider userCommunityDataProvider) {
		this.userCommunityDataProvider = userCommunityDataProvider;
	}
	

}
