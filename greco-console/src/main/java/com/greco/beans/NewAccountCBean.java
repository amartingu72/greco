package com.greco.beans;

import java.text.MessageFormat;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import org.primefaces.event.FlowEvent;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import com.greco.services.AuthenticationProvider;
import com.greco.services.UserDataProvider;
import com.greco.services.helpers.CommunityItem;
import com.greco.services.helpers.UserItem;
import com.greco.utils.MyLogger;
import com.greco.utils.Warnings;


public class NewAccountCBean {
	private static final MyLogger logger = MyLogger.getLogger(NewAccountCBean.class.getName());
	private NewAccountBBean newAccountBBean;  //Inyectado
	private UserDataProvider userDataProvider; //Inyectado.
	private AuthenticationManager authenticationManager; //Inyectado
	
	
	/**
	 * A ELIMINAR. YA NO EXISTE TAP. LO DEJO COMO EJEMPLO DE GESTIÓN DE USUARIOS QUE YA CONOCEMOS PORQUE SE REGISTRARON ANTES.
	 * Este método es invocado cada vez que hay un cambio de paso.
	 * Si es el cambio desde email comprobamos que no está dado de alta. Si lo está se le solicitará nuevo login
	 * @param event
	 * @return
	 */
	public String onFlowProcess(FlowEvent event) {
		
		String ret=event.getNewStep();
		newAccountBBean.setShowLoginBtn(false); 
				
		if ( event.getOldStep().equals("email") ) {
			
			UserSBean userSBean=userDataProvider.loadAdminCredentials(newAccountBBean.getEmail());
			if ( userSBean != null ) {
				String msg="";
				String details="";
				CommunityItem[] communities=userDataProvider.getMyComunities(userSBean);
				String comList="";		
				
				//Buscamos nuestra comunidad.
				int i=0;
				while (i<communities.length)
				{
					comList+=communities[i].getName()+" ";
					i++;
				}
				
				if ( comList.equals("") ){
					//El usuario existe pero no está asignado a ninguna comunidad.
					msg=Warnings.getString("subscribeMe.user_exist_msg");									
				} else {
					//El usuario es miembro de otras comunidades. Tenemos sus datos y los podemos utilizar
					MessageFormat form=new MessageFormat(Warnings.getString("subscribeMe.member_other_communities_msg"));
					Object[] params={comList};
					msg=form.format(params);
				}
				
				//Mostramos mensaje informativo.
				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, details);
		        FacesContext.getCurrentInstance().addMessage(null, fm);
		        newAccountBBean.setShowLoginBtn(true); //Habilitamos el botón para enviar de nuevo a login.
		        ret="email";  //Nos quedamos en la pestaña de email.
			}
				
			
		}
		
		
        return ret;
    }
	
	public String submit() {
		//Creamos el usuario y se suscribimos a la comunidad como candidato (estado Pending), a la espera de que un 
		//administrador le confirme.
		UserItem userItem=new UserItem();
		userItem.setEmail(newAccountBBean.getEmail());
		userItem.setMydata(newAccountBBean.getMydata());
		userItem.setNickname(newAccountBBean.getNickname());
		userItem.setPassword(newAccountBBean.getPassword());
		//Creamos usuario
		int userId=userDataProvider.addUser(userItem);
				
		//Creamos mensaje para log
		//Preparamos el mensaje para el log.
		String msg="ID(" + userId + "), NICK(" + userItem.getNickname() +"), EMAIL(" + userItem.getEmail() + ")"  ;
		//Grabamos log
		logger.log("009000",msg);//INFO|Nuevo usuario (sin comunidad)
				
		//Hacemos login
		
		try {

			// authenticate against spring security
			//Indicamos que intentamos login como administrador..
			
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
