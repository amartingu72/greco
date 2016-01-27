package com.greco.beans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.greco.services.MailProvider;
import com.greco.services.UserDataProvider;
import com.greco.utils.MyLogger;
import com.greco.utils.Warnings;

public class ActivateAccountCBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private static final MyLogger log = MyLogger.getLogger(ActivateAccountCBean.class.getName());
	
	private UserSBean userLogged;
	private ActivateAccountBBean activateAccountBBean;
	private UserDataProvider userDataProvider;
	private CommunitiesSBean communitiesSBean;
	private MailProvider mailProvider;
	
	/**
	 * Validar código de activación y continuar.
	 * @return
	 */
	public String submit(){
		String ret=null;
		if (userDataProvider.activate(userLogged.getItem(), activateAccountBBean.getActCode()) ) {
			//Comprobamos al bean se le está llamando desde la consola o desde un site.
			if ( userLogged.getCommunityId() != -1) { //Llama desde un site.
				ret="activated";  
			}
			else { //Llama desde la consola.
				//Vemos si administra una sola comunidad (salida "edit"), o varias (salida "select")
				if ( communitiesSBean.getSelectedItem() != null ) //Hay comunidad seleccionada.
					ret="edit";
				else
					ret="select";
			}
		}
		else {
			//Código no válido. Mostrar mensaje de error.
			FacesMessage fm = new FacesMessage(
					Warnings.getString("login.activation_failed"),
					Warnings.getString("login.activation_failed_details")); 
			FacesContext.getCurrentInstance().addMessage(null, fm);
			log.log("001008");//WARNING|Activación de cuenta fallida.
			
		}
			
		return ret;
	}
	/**
	 * Enviar de nuevo código de activación a la cuenta de correo.
	 * @return
	 */
	public String send(){
		
		userDataProvider.sendActivactionCode(userLogged.getItem());
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
				Warnings.getString("newaccount.actcode_sent"),
				Warnings.getString("newaccount.actcode_sent_detail")); 
		FacesContext.getCurrentInstance().addMessage(null, fm);
		
		
		return "send";
	}
	
	
	
	
	/**
	 * Cancelar activación y volver al login de consola o comunidad según corresponda.
	 * @return
	 */
	public String cancel(){
		String ret;
		if (userLogged.getCommunityId() == -1)
			//Volvemos a la consola
			ret="/sections/login/login?faces-redirect=true";
		else
			//Volvemos al site de origen.
			ret="/sites/welcome?communityid=" + userLogged.getCommunityId() + "&faces-redirect=true";
		return ret;
	}

	public UserSBean getUserLogged() {
		return userLogged;
	}

	public void setUserLogged(UserSBean userLogged) {
		this.userLogged = userLogged;
	}
	public ActivateAccountBBean getActivateAccountBBean() {
		return activateAccountBBean;
	}
	public void setActivateAccountBBean(ActivateAccountBBean activateAccountBBean) {
		this.activateAccountBBean = activateAccountBBean;
	}
	public UserDataProvider getUserDataProvider() {
		return userDataProvider;
	}
	public void setUserDataProvider(UserDataProvider userDataProvider) {
		this.userDataProvider = userDataProvider;
	}
	public CommunitiesSBean getCommunitiesSBean() {
		return communitiesSBean;
	}
	public void setCommunitiesSBean(CommunitiesSBean communitiesSBean) {
		this.communitiesSBean = communitiesSBean;
	}
	public MailProvider getMailProvider() {
		return mailProvider;
	}
	public void setMailProvider(MailProvider mailProvider) {
		this.mailProvider = mailProvider;
	}
	
	
}
