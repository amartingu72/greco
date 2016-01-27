package com.greco.beans;


import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.greco.services.AuthenticationProvider;
import com.greco.services.UserDataProvider;
import com.greco.services.except.user.NoMemberException;
import com.greco.services.except.user.PendingException;
import com.greco.services.except.user.UnavailableCommunity;
import com.greco.services.except.user.UnknownCommunityException;
import com.greco.services.helpers.CommunityItem;
import com.greco.utils.MyLogger;
import com.greco.utils.Warnings;


public class LoginCBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private static final MyLogger log = MyLogger.getLogger(LoginCBean.class.getName());
	
	
	private LoginBBean loginBBean;
	private UserDataProvider userDataProvider;
	private AuthenticationManager authenticationManager;
		
	public String login() {
		String ret;
		if (loginBBean.getCommunityId() == 0) 
			ret=admLogin();  //Acceso a página de administración.
		else ret=commLogin();
		return ret;
		
	}
	
		
	
	/**
	 * Login desde página de administración 
	 * @return
	 */
	private String admLogin() {
		
			
		try {

			// authenticate against spring security
			//Indicamos que intentamos login como administrador..
			Authentication request = new UsernamePasswordAuthenticationToken(loginBBean.getEmail()+ "#" + AuthenticationProvider.ROLE_ADMIN, loginBBean.getPassword());            

			Authentication result = authenticationManager.authenticate(request);
			SecurityContextHolder.getContext().setAuthentication(result);
			
		} catch (AuthenticationException e) {

			FacesMessage fm = new FacesMessage(Warnings.getString("Login.login_failed")); 
			FacesContext.getCurrentInstance().addMessage(null, fm);
			log.log("001005");//INFO|Intento de login fallido. Usuario o contraseña incorrectos.

			return "failure";
		}
		
		//Obtenemos resto de datos para cargar el bean de sesión UserBean.
		UserSBean userBean=new UserSBean();
		userBean=this.userDataProvider.loadAdminCredentials(loginBBean.getEmail());
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userLogged", userBean);
		log.log("001000");//INFO|Intento exitoso de login como administrador.
		String ret;
		//Instancio el bean como si se hubiera llamado desde una página JSF.
		FacesContext context = FacesContext.getCurrentInstance();
		CommunitiesSBean communitiesSBean = (CommunitiesSBean) context.getApplication().evaluateExpressionGet(context, "#{communitiesSBean}", CommunitiesSBean.class);
		
		//Si tengo solo una, edito esa directamente.
		List<CommunityItem> communities=communitiesSBean.getMyCommunities();
		if (communities.size()==1){
			communitiesSBean.setSelectedItem(communities.get(0));
			//Actualizamos la lista de recursos correspondientes
			ResourcesSBean resourcesSBean = (ResourcesSBean) context.getApplication().evaluateExpressionGet(context, "#{resourcesSBean}", ResourcesSBean.class);
			resourcesSBean.setCommunityItem(communities.get(0));
			ret="edit";
		}
		//Si tengo más de una comunidad, pido al usuario que seleccione la que quiera administrar.
		else ret="select";
		//Pongo el bean en la sesión.
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("communitiesSBean", communitiesSBean);
		
		//Compruebo si este usuario es la primera vez que hace login (en consola o site), y, si es la primera vez, redirijo a página de activación.
		if ( !userBean.isActivated() ) ret="check";
		
        return ret;	
	}
	
	
	
	/**
	 * Login desde una página de comunidad.
	 * @return
	 */
	private String commLogin(){
		
		String ret=null;
		
		UserSBean user=null;
		try {
			// authenticate against spring security
			//NOTA: En el email le paso que voy de comlogin.
			Authentication request = new UsernamePasswordAuthenticationToken(loginBBean.getEmail() + "#" + AuthenticationProvider.ROLE_USER, loginBBean.getPassword());            
			Authentication result = authenticationManager.authenticate(request);
			SecurityContextHolder.getContext().setAuthentication(result);
			

		} catch (AuthenticationException e) {
			FacesMessage fm = new FacesMessage(Warnings.getString("Login.login_failed")); 
			FacesContext.getCurrentInstance().addMessage("mainForm:message", fm);
			log.log("001005");//INFO|Intento de login fallido. Usuario o contraseña incorrectos.
			return null;
		}
		
		//Obtenemos los datos para el bean que guardará sus datos durante la sesión.
		
		try {
			
			user=userDataProvider.loadUserCredentials(loginBBean.getEmail(), loginBBean.getCommunityId());
			
			if ( user!=null) {
				
				//Pongo el bean en la sesión.
				
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userLogged", user);
				ret="login";
				log.log("001001"); //INFO|Intento exitoso de login como usuario.
				//Compruebo si este usuario es la primera vez que hace login (en consola o site), y, si es la primera vez, redirijo a página de activación.
				if ( !user.isActivated() ) ret="check";
			}
			else {
				FacesMessage fm = new FacesMessage(Warnings.getString("Login.login_failed")); 
				FacesContext.getCurrentInstance().addMessage("mainForm:message", fm);
				log.log("001005");//INFO|Intento de login fallido. Usuario o contraseña incorrectos.
			}
		} catch (NoMemberException e) {
			//FacesMessage fm = new FacesMessage(Warnings.getString("ComLogin.not_member")); //$NON-NLS-1$
			//FacesContext.getCurrentInstance().addMessage(null, fm);
			//Recuperamos sus datos para otras comunidades o como adminsitrador.
			user=userDataProvider.loadAdminCredentials(loginBBean.getEmail());
			//Le asigno la comunidad objetivo.
			user.setCommunityId(loginBBean.getCommunityId());
			//Pongo el bean en la sesión.
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userLogged", user);
			ret="nomember";
			log.log("001004"); //WARNING|Intento de login fallido. El usuario no es miembro de la comunidad.
		} catch (UnknownCommunityException e) {
			FacesMessage fm = new FacesMessage(Warnings.getString("ComLogin.unknown_community")); 
			FacesContext.getCurrentInstance().addMessage("mainForm:message", fm);
			log.log("001003"); //INFO|Intento de login como usuario fallido. La comunidad no existe."
		} catch (UnavailableCommunity e) {
			FacesMessage fm = new FacesMessage(Warnings.getString("ComLogin.community_not_available")); 
			FacesContext.getCurrentInstance().addMessage("mainForm:message", fm);
			log.log("001006"); //INFO|Intento de login como usuario fallido. La comunidad no está disponible en este momento.
		} catch (PendingException e) {
			FacesMessage fm = new FacesMessage(Warnings.getString("ComLogin.subscription_pending")); 
			FacesContext.getCurrentInstance().addMessage("mainForm:message", fm);
			String msg="USER_EMAIL(" + loginBBean.getEmail() + ") ";
			msg="COMMUNITY_ID(" + loginBBean.getCommunityId() + ") ";
			log.log("001007",msg); //INFO|Intento de login fallido. La suscripción fue cursada pero no aprobada para la comunidad indicada.
		}
		
				
		return ret;
	}
	
	
	
	/**
	 * El registro puede ser en dos sentidos:
	 * - Susbcripción a una comunidad.
	 * - Crear un usuario administrador.
	 * En función de la URL de inicio (con o sin parámetro de comunidad), irá a uno o a otro 
	 * @return
	 */
	public String register(){
		String ret;
		if ( this.loginBBean.getCommunityId() == 0 )
			ret="register";
		else ret="subscribeme";
		return ret;
	}
	
	/**
	 * Ir a página para recuperación de contraseña.
	 * @return
	 */
	public String pwdForgotten() {
		return "pwd_forgotten";
	}

//Getter y setter
	
	public UserDataProvider getUserDataProvider() {
		return userDataProvider;
	}
	public void setUserDataProvider(UserDataProvider userDataProvider) {
		this.userDataProvider = userDataProvider;
	}
	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	public LoginBBean getLoginBBean() {
		return loginBBean;
	}
	public void setLoginBBean(LoginBBean loginBBean) {
		this.loginBBean = loginBBean;
	}

}
