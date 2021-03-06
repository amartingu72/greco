package com.greco.beans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import com.greco.services.CommunityDataProvider;
import com.greco.services.ReservationDataProvider;
import com.greco.services.UserCommunityDataProvider;
import com.greco.services.UserDataProvider;
import com.greco.services.except.user.NoCommunityAdminException;
import com.greco.services.except.user.NoMemberException;
import com.greco.services.helpers.MemberItem;
import com.greco.services.helpers.UserItem;
import com.greco.utils.MyLogger;
import com.greco.utils.Warnings;


public class EditAccountCBean implements Serializable{
	//Criterios de b�squeda.
    public static final int LAST_30=1; //�ltimas 30 reservas.
    public static final int THIS_MONTH=2; //Reservas realizadas en el mes en curso.
    public static final int LAST_MONTH=3; //Reservas realizadas el mes anterior.
    
	private static final long serialVersionUID = -7168931307619229978L;
	private static final MyLogger logger = MyLogger.getLogger(EditAccountCBean.class.getName());
	
	private UserSBean userSBean; //Inyectado 
	private EditAccountBBean editAccountBBean; //Inyectado
	private UserDataProvider userDataProvider; //Inyectado
	private UserCommunityDataProvider userCommunityDataProvider; //Inyectado
	private ReservationDataProvider rerservationDataProvider; //Inyectado
	private CommunityDataProvider communityDataProvider; //Inyectado

	 public UserCommunityDataProvider getUserCommunityDataProvider() {
		return userCommunityDataProvider;
	}

	public void setUserCommunityDataProvider(
			UserCommunityDataProvider userCommunityDataProvider) {
		this.userCommunityDataProvider = userCommunityDataProvider;
	}

	public String save() {
		UserItem userItem=new UserItem();
		userItem.setId(userSBean.getId());
		
		userItem.setEmail(editAccountBBean.getEmail());
		userItem.setMydata(editAccountBBean.getMydata());
		userItem.setNickname(editAccountBBean.getNickname());
		userItem.setPassword(editAccountBBean.getPassword());
		userItem.setAdds(editAccountBBean.isAdds());
		
		userDataProvider.save(userItem, editAccountBBean.isPwdUpdated());
		
		
				
		//Preparamos el mensaje para el log.
		String msg="NICKNAME(" + userSBean.getNickname() + " > " + editAccountBBean.getNickname() + ") ";
		msg+="EMAIL(" + userSBean.getEmail() + " > " + editAccountBBean.getEmail() + ") ";
		msg+="MYDATA("+ userSBean.getMydata() + " > " + editAccountBBean.getMydata() +") PWD_UPATED(" + editAccountBBean.isPwdUpdated() + ") ADDS(" + userSBean.isAdds()+ " > " + editAccountBBean.isAdds()+ ")";
		//Grabamos log
		logger.log("005000",msg);//INFO|Cuenta modificada:
		
		//Si ha cambiado la cuenta de correo, indicar que le hemos enviado un c�digo de activaci�n que deber� incluir en su pr�ximo login.
		String details=""; 
		if ( !this.editAccountBBean.getEmail().equals(userSBean.getEmail()) ){
			details=Warnings.getString("editaccount.email_updated");
		}
		
		//Actualizamos bean de soporte.
		userSBean.setEmail(editAccountBBean.getEmail());
		userSBean.setMydata(editAccountBBean.getMydata());
		userSBean.setNickname(editAccountBBean.getNickname());
		userSBean.setAdds(editAccountBBean.isAdds());
		
		//Mostramos mensaje informativo.
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, Warnings.getString("editaccount.updated"), details);
        FacesContext.getCurrentInstance().addMessage(null, fm);
        
		return null;
	
	}
	 
	public String unsubscribe(){
		MemberItem memberItem=userCommunityDataProvider.find(userSBean.getItem(), userSBean.getCommunityId());
		String ret=null;
        if (memberItem!=null) {
			try {
				userCommunityDataProvider.removeMember(memberItem);
				ret="unsubscribed";
				//Grabamos el log
				String msg="userID (" + memberItem.getId() + ") COMMUNITYID(" + memberItem.getCommunityId() + ")";
				logger.log("007003", msg ); //007003=INFO|Baja de miembro:
				
				//Invalidamos la sesi�n.
				FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
				
			} catch (NoCommunityAdminException e) {
				//Generamos mensaje para el usuario.
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage("editAccountForm:unsubscribeMsgs",new FacesMessage(FacesMessage.SEVERITY_WARN, Warnings.getString("editcommunity.nocommunityadmin_del_msg"),  
	    				Warnings.getString("editcommunity.nocommunityadmin_del_detail" ) ) );
			} catch (NoMemberException e) {
				FacesContext context = FacesContext.getCurrentInstance();
		         
		        context.addMessage("editAccountForm:unsubscribeMsgs",new FacesMessage(FacesMessage.SEVERITY_WARN, Warnings.getString("editmembership.removed"),  
		        				Warnings.getString("editmembership.removed_details" ) ) );
			}
        }
        else {
        	FacesContext context = FacesContext.getCurrentInstance();
	         
	        context.addMessage("editAccountForm:unsubscribeMsgs",new FacesMessage(FacesMessage.SEVERITY_WARN, Warnings.getString("editmembership.removed"),  
	        				Warnings.getString("editmembership.removed_details" ) ) );
        }
		
		return ret;
	}
	
	/**
	 * B�squeda de reservas seg�n criterio (p�gina de site)
	 */
	public String searchLocalReservations(){
		editAccountBBean.setSearchMessage("");
		switch (editAccountBBean.getSearchCriteria()) {
		case LAST_30:
			editAccountBBean.setOldReservations(this.rerservationDataProvider.getOldReservations(
						editAccountBBean.getId(), 
						communityDataProvider.getCommunityById(userSBean.getCommunityId())));
			break;
		case THIS_MONTH:
			editAccountBBean.setOldReservations(this.rerservationDataProvider.getThisMonthReservations(
					editAccountBBean.getId(), 
					communityDataProvider.getCommunityById(userSBean.getCommunityId())));
			break;
		case LAST_MONTH:
			editAccountBBean.setOldReservations(this.rerservationDataProvider.getLastMonthReservations(
					editAccountBBean.getId(), 
					communityDataProvider.getCommunityById(userSBean.getCommunityId())));
				
		}
		//Si no se recupera ninguna, muestro un mensaje.
		if ( editAccountBBean.oldReservationsRetrieved() == 0 ){
			editAccountBBean.setSearchMessage(Warnings.getString("editaccount.no_data_details"));
		}
			
		return null;
		
	}
	
	/**
	 * Comprueba que el nickname no est� duplicado.
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
		UserItem userItem=this.userDataProvider.getUserItemByNick(nickname);
		if (   (userItem!=null) && (userItem.getId()!=this.userSBean.getId()) ) //Considera que lo encuentre y adem�s no sea �l mismo.	
		throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
														Warnings.getString("newaccount.duplicated_nick"),
														null) );
	}
	
	
	/**
	 * Comprueba que el emai�l no est� duplicado.
	 * @param fc
	 * @param c
	 * @param value
	 */
	public void validateUniqueEmailAddress(FacesContext fc, UIComponent c, Object value) {
		String email=((String)value);
		UserItem userItem=this.userDataProvider.getUserItem(email);
		if ( (userItem!=null) && (userItem.getId()!=this.userSBean.getId()) )	//Considera que lo encuentre y adem�s no sea �l mismo.
		throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
														Warnings.getString("editaccount.duplicated_email"),
														null) );
	}
	
	
	//GETTERs y SETTERs
	public UserSBean getUserSBean() {
		return userSBean;
	}

	public void setUserSBean(UserSBean userSBean) {
		this.userSBean = userSBean;
	}


	public EditAccountBBean getEditAccountBBean() {
		return editAccountBBean;
	}


	public void setEditAccountBBean(EditAccountBBean editAccountBBean) {
		this.editAccountBBean = editAccountBBean;
	}


	public UserDataProvider getUserDataProvider() {
		return userDataProvider;
	}


	public void setUserDataProvider(UserDataProvider userDataProvider) {
		this.userDataProvider = userDataProvider;
	}	
	
	
	
	public ReservationDataProvider getRerservationDataProvider() {
		return rerservationDataProvider;
	}

	public void setRerservationDataProvider(
			ReservationDataProvider rerservationDataProvider) {
		this.rerservationDataProvider = rerservationDataProvider;
	}

	public CommunityDataProvider getCommunityDataProvider() {
		return communityDataProvider;
	}

	public void setCommunityDataProvider(CommunityDataProvider communityDataProvider) {
		this.communityDataProvider = communityDataProvider;
	}

	

	
	
}
