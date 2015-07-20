package com.greco.beans;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;



import com.greco.services.CommunityDataProvider;
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
	
	/**
	 * Devuelve el nombre de la comunidad de la que se quiere hacer miembro.
	 * @return Nombre de la comunidad.
	 */
	public String getCommunityName(){
		CommunityItem communityItem=communityDataProviser.getCommunityById(subscribeMeBBean.getCommunityId());
		return communityItem.getName();
	}
	
	
	/** NO PROCEDE.
	 * Este método es invocado cada vez que hay un cambio de paso.
	 * Si es el cambio desde email comprobamos que no está dado de alta. Si lo está se le solicitará nuevo login
	 * @param event
	 * @return
	 
	public String onFlowProcess(FlowEvent event) {
		
		String ret=event.getNewStep();
		subscribeMeBBean.setShowLoginBtn(false); 
				
		if ( event.getOldStep().equals("email") ) {
			
			UserSBean userSBean=userDataProvider.loadAdminCredentials(subscribeMeBBean.getEmail());
			if ( userSBean != null ) {
				String msg="";
				String details="";
				CommunityItem[] communities=userDataProvider.getMyComunities(userSBean);
				String comList="";		
				
				//Buscamos nuestra comunidad.
				boolean found=false;
				int i=0;
				while ( (i<communities.length) & !found )
				{
					found=( communities[i].getId()==subscribeMeBBean.getCommunityId() );
					if (!found) comList+=communities[i].getName()+" ";
					i++;
				}
				if ( !found ){
					if ( comList.equals("") ){
						//El usuario existe pero no está asignado a ninguna comunidad.
						msg=Warnings.getString("subscribeMe.user_exist_msg");									
					} else {
						//El usuario es miembro de otras comunidades. Tenemos sus datos y los podemos utilizar
						MessageFormat form=new MessageFormat(Warnings.getString("subscribeMe.member_other_communities_msg"));
						Object[] params={comList};
						msg=form.format(params);
					}
						
				} else {
					//El usuario ya es miembro de esta comunidad.
					msg=Warnings.getString("subscribeMe.user_is_alredy_member_msg");
				}
				
				//Mostramos mensaje informativo.
				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, details);
		        FacesContext.getCurrentInstance().addMessage(null, fm);
		        subscribeMeBBean.setShowLoginBtn(true); //Habilitamos el botón para enviar de nuevo a login.
		        ret="email";  //Nos quedamos en la pestaña de email.
			}
				
			
		}
		
		
        return ret;
    }*/
	
	public String submit() {
		//Creamos el usuario y se suscribimos a la comunidad como candidato (estado Pending), a la espera de que un 
		//administrador le confirme.
		UserItem userItem=new UserItem();
		userItem.setEmail(subscribeMeBBean.getEmail());
		userItem.setMydata(subscribeMeBBean.getMydata());
		userItem.setNickname(subscribeMeBBean.getNickname());
		userItem.setPassword(subscribeMeBBean.getPassword());
		//Creamos usuario
		int userId=userDataProvider.addUser(userItem);
		//Subscribimos el usuario a la comunidad.
		userDataProvider.subscribe(userId, subscribeMeBBean.getCommunityId());
		
		//Creamos mensaje para log
		//Preparamos el mensaje para el log.
		String msg="ID(" + userId + "), NICK(" + userItem.getNickname() +"), EMAIL(" + userItem.getEmail() + ")"  ;
		//Grabamos log
		logger.log("008000",msg);//INFO|Subscripción de nuevo usuario:
		
		
		
		return "/sections/subscriptioncomplete?communityid=" + subscribeMeBBean.getCommunityId() +"&faces-redirect=true";
	}	
	

	public String cancel() {
		return "cancel";
	}
	/**
	 * Volver a comlogin, manteniendo el parámetro de comunidad pasado inicialmente.
	 * @return URL de retorno
	 */
	public String navigateLogin(){
		return "/sections/login/logout?communityid=" + subscribeMeBBean.getCommunityId() +"&faces-redirect=true";
		
		
	}
	/**
	 * Comprueba que el nickname no está duplicado ni supera el tamaño máximo.
	 * @param fc
	 * @param c
	 * @param value
	 */
	public void validateName(FacesContext fc, UIComponent c, Object value) {
		if ( ((String)value).length() > 16 )	//16 es el tamaño máximo prefijado en BD
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
															Warnings.getString("subscribeMe.too_long_nick"),
															null) );
		if (   this.userDataProvider.isDuplicated(((String)value)) )	
		throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
														Warnings.getString("subscribeMe.duplicated_nick"),
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
	
	
	
}
