package com.greco.beans;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.greco.services.UserCommunityDataProvider;
import com.greco.services.except.user.NoCommunityAdminException;
import com.greco.services.helpers.MemberItem;
import com.greco.utils.MyLogger;
import com.greco.utils.Warnings;

public class EditMembershipCBean {
	
	private static final MyLogger log = MyLogger.getLogger(EditMembershipCBean.class.getName());
	
	UserCommunityDataProvider userCommunityDataProvider; //Inyectado
	EditCommunityBBean editCommunityBBean; //Inyectado
	
	
	/**
	 * Guarda el cambio de estado (miembro o pendiente) realizado sobre un miembro.
	 * @param memberItem Miembro (o candidato a miembro), afectado.
	 */
	public void saveMembershipStatus(MemberItem memberItem){
		userCommunityDataProvider.saveStatus(memberItem);
		//Generamos mensaje para el log.
		String msg="UserCommunityID (" + memberItem.getMemberId() + ") ";
		msg += "New status ("+ memberItem.getStatus() + ")";
		
		log.log("007001", msg );//007001=INFO|Cambio de estado:
	}
	
	/**
	 * Guarda el cambio de perfil (admin o usr), de un miembro.
	 * @param memberItem Miebro afectado
	 */
	public void saveMemberProfile(MemberItem memberItem){
		try {
			userCommunityDataProvider.saveMemberProfile(memberItem);
			//Generamos mensaje para el log.
			
			String msg="UserCommunityID (" + memberItem.getMemberId() + ") ";
			msg += "New profile ("+ memberItem.getMemberProfile().getName() + ")";
			log.log("007001", msg ); //007002=INFO|Cambio de perfil:
			
		} catch (NoCommunityAdminException e) {
			//Generamos mensaje para el usuario.
			FacesContext context = FacesContext.getCurrentInstance();
	         
	        context.addMessage(null,new FacesMessage(Warnings.getString("editcommunity.nocommunityadmin_msg"),  
	        				Warnings.getString("editcommunity.nocommunityadmin_detail" ) ) );
	        
		}
		
	}
	/**
	 * Da de baja de la comunidad al miembre seleccionado (atributo selectedMember)
	 */
	public void unsubscribe(){
		
		try {
			userCommunityDataProvider.removeMember(this.editCommunityBBean.getSelectedMember());
			//Grabamos el log
			String msg="userID (" + this.editCommunityBBean.getSelectedMember().getId() + ") ";
			log.log("007003", msg ); //007003=INFO|Baja de miembro:
			
		} catch (NoCommunityAdminException e) {
			//Generamos mensaje para el usuario.
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,new FacesMessage(Warnings.getString("editcommunity.nocommunityadmin_del_msg"),  
    				Warnings.getString("editcommunity.nocommunityadmin_del_detail" ) ) );
		}
		
		
	}
	
	//GETTERs y SETTERs
	
	public UserCommunityDataProvider getUserCommunityDataProvider() {
		return userCommunityDataProvider;
	}

	public void setUserCommunityDataProvider(
			UserCommunityDataProvider userCommunityDataProvider) {
		this.userCommunityDataProvider = userCommunityDataProvider;
	}

	public EditCommunityBBean getEditCommunityBBean() {
		return editCommunityBBean;
	}

	public void setEditCommunityBBean(EditCommunityBBean editCommunityBBean) {
		this.editCommunityBBean = editCommunityBBean;
	}
	
}
