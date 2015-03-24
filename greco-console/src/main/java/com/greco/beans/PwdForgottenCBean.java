package com.greco.beans;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;



import com.greco.services.UserDataProvider;
import com.greco.utils.MyLogger;
import com.greco.utils.Warnings;

import javax.annotation.Resource;
import javax.mail.Address;
import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Bean controlador para la funcionalidad de password olvidada 
 *  
 * @author Alberto Martín
 *
 */

public class PwdForgottenCBean {
	
	private static final MyLogger logger = MyLogger.getLogger(PwdForgottenCBean.class.getName());
	//@Resource(name = "mail/gmailAccount")
	@Resource(name = "mail/banestoAccount")
	private Session mailSession;
	private PwdForgottenBBean pwdForgottenBBean;  //Inyectado.
	private UserDataProvider userDataProvider; //Inyectado

	public String submit() {
		String ret=null;
		//Generar nueva contraseña y enviar por correo.
		
		//Comprobamos que el usuario existe.
		UserSBean userSBean=userDataProvider.loadAdminCredentials(pwdForgottenBBean.getEmail());
		if (userSBean == null){
			//Mensaje de error.
			String msg=Warnings.getString("pwdforgotten.unknown_email_msg");
			String details=Warnings.getString("pwdforgotten.unknown_email_detail");
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, details);
	        FacesContext.getCurrentInstance().addMessage(null, fm);
		}
		else {
			//Generamos un contraseña aleatoria y se la asignamos al usuario.
			String pwd=userDataProvider.changePassword(userSBean.getId());
			//Mensaje para logger
			String msg="USERID (" + userSBean.getId() + "), ";
			msg+="EMAIL ("+ userSBean.getEmail() +")";
			//Enviamos email. Indicar comunidad en el asunto.	
			try {
				//userDataProvider.sendEmail(userSBean.getId(), "Greco: cambio de contraseña", "Su nueva contraseña es " + pwd);
				sendEmail("Greco: cambio de contraseña", "Su nueva contraseña es " + pwd);
				//Grabamos log
				
				logger.log("010000",msg);//010000=INFO|Enviada nueva pwd:
				ret="sent";
			} catch (MessagingException e) {
				e.printStackTrace();
				//Generamos mensaje para logger
				logger.log("010001",msg);//010001=ERROR|No es posible enviar correo:
				//Mensaje en pantalla.
				String message=Warnings.getString("pwdforgotten.sending_error_msg");
				String details=Warnings.getString("pwdforgotten.sending_error_detail");
				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, message, details);
		        FacesContext.getCurrentInstance().addMessage(null, fm);
			}
			
		}
		
		
		return ret;
	}
	
	public String cancel() {
		String ret="/sections/login/login?communityid="+ this.pwdForgottenBBean.getCommunityId() +"&faces-redirect=true";
		return ret;
	}
	
	//GETTERs y SETTERs
	public PwdForgottenBBean getPwdForgottenBBean() {
		return pwdForgottenBBean;
	}

	public void setPwdForgottenBBean(PwdForgottenBBean pwdForgottenBBean) {
		this.pwdForgottenBBean = pwdForgottenBBean;
	}

	public UserDataProvider getUserDataProvider() {
		return userDataProvider;
	}

	public void setUserDataProvider(UserDataProvider userDataProvider) {
		this.userDataProvider = userDataProvider;
	}
	
	public void sendEmail(String subject, String text) throws MessagingException {
		
		
		MimeMessage message = new MimeMessage(mailSession);
		
		Address toAddress = new InternetAddress(this.pwdForgottenBBean.getEmail());
		message.setRecipient(RecipientType.TO, toAddress);
		message.setSubject(subject);
		message.setText(text);
		message.saveChanges();
		Transport tr = mailSession.getTransport();
		String serverPassword = mailSession.getProperty("mail.password");
		tr.connect(null, serverPassword);
		tr.sendMessage(message, message.getAllRecipients());
		tr.close();
		
	}
	

}
