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
	 * Crea un usuario.
	 * @return
	 */
	public String submit() {
		//Creamos el usuario administrador
		UserItem userItem=new UserItem();
		userItem.setEmail(newAccountBBean.getEmail());
		userItem.setMydata(newAccountBBean.getMydata());
		userItem.setNickname(newAccountBBean.getNickname());
		userItem.setPassword(newAccountBBean.getPassword());
		userItem.setAdds(newAccountBBean.isAdds());
		//Creamos usuario
		int userId=userDataProvider.addUser(userItem);
				
		//Creamos mensaje para log
		//Preparamos el mensaje para el log.
		String msg="ID(" + userId + "), NICK(" + userItem.getNickname() +"), EMAIL(" + userItem.getEmail() + ") ADDS (" + userItem.isAdds() +")"  ;
		//Grabamos log
		logger.log("009000",msg);//INFO|Nuevo usuario (sin comunidad)
				
		//Hacemos login
		
		try {

			// authenticate against spring security
			//Indicamos que intentamos login en aplicación de administración.
			
			Authentication request = new UsernamePasswordAuthenticationToken(this.newAccountBBean.getEmail()+ "#" + AuthenticationProvider.ROLE_ADMIN, this.newAccountBBean.getPassword());            

			Authentication result = authenticationManager.authenticate(request);
			SecurityContextHolder.getContext().setAuthentication(result);

		} catch (AuthenticationException e) {

			FacesMessage fm = new FacesMessage(Warnings.getString("Login.login_failed")); 
			FacesContext.getCurrentInstance().addMessage("User", fm);
			logger.log("001005");//INFO|Intento de login fallido. Usuario o contraseña incorrectos.

			return null;
		}
		
		//Obtenemos resto de datos para cargar el bean de sesión UserBean.
		UserSBean userBean=new UserSBean();
		userBean=this.userDataProvider.loadAdminCredentials(this.newAccountBBean.getEmail());
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userLogged", userBean);
		logger.log("001000");//INFO|Intento exitoso de login como administrador.				
        		
		
		return "success";
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
