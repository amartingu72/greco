package com.greco.beans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

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
		
		userDataProvider.save(userItem, editAccountBBean.isPwdUpdated());
		
		//Actualizamos bean de soporte.
		userSBean.setEmail(editAccountBBean.getEmail());
		userSBean.setMydata(editAccountBBean.getMydata());
		userSBean.setNickname(editAccountBBean.getNickname());
				
		//Preparamos el mensaje para el log.
		String msg="NICKNAME(" + userSBean.getNickname() + " > " + editAccountBBean.getNickname() + ") ";
		msg+="EMAIL(" + userSBean.getEmail() + " > " + editAccountBBean.getEmail() + ") ";
		msg+="MYDATA("+ userSBean.getMydata() + " > " + editAccountBBean.getMydata() +")";
		//Grabamos log
		logger.log("005000",msg);//INFO|Cuenta modificada:
		
		//Mostramos mensaje informativo.
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, Warnings.getString("editaccount.updated"), null);
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
				
				//Invalidamos la sesión.
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
	 * Búsqueda de reservas según criterio (página de site)
	 */
	public String searchLocalReservations(){
		switch (editAccountBBean.getSearchCriteria()) {
		case ReservationDataProvider.LAST_30:
			editAccountBBean.setOldReservations(this.rerservationDataProvider.getOldReservations(
						editAccountBBean.getId(), 
						communityDataProvider.getCommunityById(userSBean.getCommunityId())));
			break;
		case ReservationDataProvider.THIS_MONTH:
			break;
		case ReservationDataProvider.LAST_MONTH:
				
		}
		return null;
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
