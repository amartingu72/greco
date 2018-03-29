package com.greco.beans;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.greco.services.AuthenticationProvider;
import com.greco.services.UserDataProvider;
import com.greco.services.helpers.UserItem;
import com.greco.utils.MyLogger;
import com.greco.utils.Warnings;


public class NewAccountCBean {
	private static final MyLogger logger = MyLogger.getLogger(NewAccountCBean.class.getName());
	private NewAccountBBean newAccountBBean;  //Inyectado
	private UserDataProvider userDataProvider; //Inyectado.
	private AuthenticationManager authenticationManager; //Inyectado
	
	/**
	 * Volver a la página de introducción de datos.
	 * @return
	 */
	public String navigateStep1(){
		//Borramos el usuario.
		UserSBean userSBean=(UserSBean)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userLogged");
		userDataProvider.remove(userSBean.getItem());
		return "step1";
	}
	
	/**
	 * Ir a la página de activación de usuario.
	 * @return
	 */
	public String navigateStep2(){
		//Creamos el usuario administrador
		UserItem userItem=new UserItem();
		userItem.setEmail(newAccountBBean.getEmail().trim());
		userItem.setMydata(newAccountBBean.getMydata());
		userItem.setNickname(newAccountBBean.getNickname().trim());
		userItem.setPassword(newAccountBBean.getPassword());
		userItem.setAdds(newAccountBBean.isAdds());
		
		int userId;
		
		//Creamos usuario
		if ( newAccountBBean.isReedited() ){
			//Creamos el usuario pero mantenemos el código de activación.
			UserSBean userBean=(UserSBean)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userLogged");
			userId=userDataProvider.addUser(userItem, userBean.getActCode()); 
		} else
			userId=userDataProvider.addUser(userItem); //Generamos nuevo código de activación.
		
				
		//Creamos mensaje para log
		//Preparamos el mensaje para el log.
		String msg="ID(" + userId + "), NICK(" + userItem.getNickname().trim() +"), EMAIL(" + userItem.getEmail().trim() + ") ADDS (" + userItem.isAdds() +")"  ;
		//Grabamos log
		if ( newAccountBBean.isReedited() ){
			logger.log("009001",msg);//INFO|Nuevo usuario reeditado(sin comunidad)
		} 
		else
			logger.log("009000",msg);//INFO|Nuevo usuario (sin comunidad)
				
		//Obtenemos resto de datos para cargar el bean de sesión UserBean.
		UserSBean userBean=new UserSBean();
		userBean=this.userDataProvider.loadAdminCredentials(this.newAccountBBean.getEmail().trim());
		//Ponemos la pwd en claro por si es usuario solicita volver a la pagina anterior y para poder hacer login si va a la siguiente.
		userBean.setPassword(userItem.getPassword());
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userLogged", userBean);	
		return "step2";
	}
	
	
	/**
	 * Comprueva código de activación accede al sistema.
	 * @return
	 */
	public String navigateWelcome() {		
		String ret=null;
		
		//Comprobamos código de activación.
		UserSBean userBean=(UserSBean)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userLogged");
		
		if (userDataProvider.activate(userBean.getItem(),this.newAccountBBean.getActCode())) {
			/*try {
	
				// authenticate against spring security
				//Indicamos que intentamos login en aplicación de administración.
				
				Authentication request = new UsernamePasswordAuthenticationToken(userBean.getEmail()+ "#" + AuthenticationProvider.ROLE_ADMIN, userBean.getPassword());            
	
				Authentication result = authenticationManager.authenticate(request);
				SecurityContextHolder.getContext().setAuthentication(result);
	
			} catch (AuthenticationException e) {
	
				FacesMessage fm = new FacesMessage(Warnings.getString("Login.login_failed")); 
				FacesContext.getCurrentInstance().addMessage("User", fm);
				logger.log("001005");//INFO|Intento de login fallido. Usuario o contraseña incorrectos.
	
				return null;
			}*/
			ret="success";
			logger.log("001000");//INFO|Intento exitoso de login como administrador.				
		}
		else {
			//Código no válido. Mostrar mensaje de error.
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					Warnings.getString("login.activation_failed"),
					Warnings.getString("login.activation_failed_details")); 
			FacesContext.getCurrentInstance().addMessage("actform:actcode", fm);
			logger.log("001008");//WARNING|Activación de cuenta fallida.
			
		}
		
		return ret;
	}	
	
	/**
	 * Envía de nuevo el código de activación por correo, al usuario registrado.
	 */
	public String sendActCode(){
		UserSBean userSBean=(UserSBean)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userLogged");
		userDataProvider.sendActivactionCode(userSBean.getItem());
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
				Warnings.getString("newaccount.actcode_sent"),
				Warnings.getString("newaccount.actcode_sent_detail")); 
		FacesContext.getCurrentInstance().addMessage("actform:sendActCode", fm);
		return null;
		
	}

	/**
	 * Volver a login.
	 * @return URL de retorno
	 */
	public String navigateLogin(){
		return "cancel";
		
		
	}
	/**
	 * Comprueba que el nickname no está duplicado.
	 * @param fc
	 * @param c
	 * @param value
	 */
	public void validateName(FacesContext fc, UIComponent c, Object value) {
		String nickname=((String)value);
		if (   nickname.isEmpty() )	
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
															Warnings.getString("newaccount.nick_required"),
															null) );
		
		if (   this.userDataProvider.isDuplicated(nickname) )	
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
		if (   this.userDataProvider.loadAdminCredentials( ((String)value))!=null )	
		throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
														Warnings.getString("newaccount.duplicated_email"),
														null) );
	}
	
	
	//GETTERs y SETTERs
	
	
	
	public NewAccountBBean getNewAccountBBean() {
		return newAccountBBean;
	}

	public void setNewAccountBBean(NewAccountBBean newAccountBBean) {
		this.newAccountBBean = newAccountBBean;
	}

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public UserDataProvider getUserDataProvider() {
		return userDataProvider;
	}

	public void setUserDataProvider(UserDataProvider userDataProvider) {
		this.userDataProvider = userDataProvider;
	}
	
	
	
	
	
}
