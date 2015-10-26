package com.greco.beans;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.greco.utils.EMail;


public class ContactCBean {
	private ContactBBean contactBBean; //Inyectado
	
	public String sendMessage(){
		EMail email=new EMail(contactBBean.getEmail());
		if ( email.isOk() ) {
			//Enviar correo
			contactBBean.setCollapsed(true);
			//Poner mensaje de que todo fue bien.
			//Poner mensaje indicando que hay un problema.
			FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage("mainForm:contactMessage",new FacesMessage(FacesMessage.SEVERITY_INFO, "Ok",  
	        				"Hemos enviado su mensaje al administrador." ) );
		}
		else {
			contactBBean.setCollapsed(false);
			//Poner mensaje indicando que hay un problema.
			FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage("mainForm:contactMessage",new FacesMessage(FacesMessage.SEVERITY_INFO, "Error de formato",  
	        				"Revise el correo de contacto introducido" ) );
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
	
	

}
