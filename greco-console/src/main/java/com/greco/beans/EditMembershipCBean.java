package com.greco.beans;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.greco.services.except.user.NoCommunityAdminException;
import com.greco.services.helpers.MemberItem;
import com.greco.services.helpers.StatusItem;
import com.greco.utils.MyLogger;
import com.greco.utils.Warnings;

public class EditMembershipCBean {
	
	private static final MyLogger log = MyLogger.getLogger(EditMembershipCBean.class.getName());
	
	
	EditMembershipBBean editMembershipBBean; //Inyectado
	
	
	/**
	 * Guarda el cambio de estado (miembro o pendiente) realizado sobre un miembro.
	 * @param memberItem Miembro (o candidato a miembro), afectado.
	 */
	public void saveMembershipStatus(MemberItem memberItem){
		editMembershipBBean.getUserCommunityDataProvider().saveStatus(memberItem);
		//Generamos mensaje para el log.
		String msg="UserCommunityID (" + memberItem.getMemberId() + ") ";
		msg += "New status ("+ memberItem.getStatus() + ")";
		
		log.log("007001", msg );//007001=INFO|Cambio de estado:
	}
	
	/**
	 * Rechaza la suscripción.
	 */
	public void approved(MemberItem memberItem){
		
		memberItem.setStatus(new StatusItem(StatusItem.MEMBER));
		saveMembershipStatus(memberItem);
		int count=editMembershipBBean.getPendingsCounter();
		editMembershipBBean.setPendingsCounter(count-1);
		count=editMembershipBBean.getMembersCounter();
		editMembershipBBean.setMembersCounter(count+1);
		
    	//Mostramos mensaje de éxito.
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,  
				memberItem.getNickname(),Warnings.getString("editmembership.approved_detail")); 
		FacesContext.getCurrentInstance().addMessage("editMembersForm:membership_msgs", fm);
	}
	
	
	
	
	/**
	 * Guarda el cambio de perfil (admin o usr), de un miembro.
	 * @param memberItem Miebro afectado
	 */
	public void saveMemberProfile(MemberItem memberItem){
		try {
			editMembershipBBean.getUserCommunityDataProvider().saveMemberProfile(memberItem);
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
			editMembershipBBean.getUserCommunityDataProvider().removeMember(this.editMembershipBBean.getSelectedMember());
			//Grabamos el log
			String msg="userID (" + this.editMembershipBBean.getSelectedMember().getId() + ") ";
			log.log("007003", msg ); //007003=INFO|Baja de miembro:
			
		} catch (NoCommunityAdminException e) {
			//Generamos mensaje para el usuario.
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,new FacesMessage(Warnings.getString("editcommunity.nocommunityadmin_del_msg"),  
    				Warnings.getString("editcommunity.nocommunityadmin_del_detail" ) ) );
		}
		
		
	}

	//GETTERs y SETTERs
	public EditMembershipBBean getEditMembershipBBean() {
		return editMembershipBBean;
	}

	public void setEditMembershipBBean(EditMembershipBBean editMembershipBBean) {
		this.editMembershipBBean = editMembershipBBean;
	}
	
	
	
	

	
}
