package com.greco.beans;

import java.util.HashMap;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.model.SortOrder;
import com.greco.repositories.UserCommunitiesDAO;
import com.greco.services.except.user.NoCommunityAdminException;
import com.greco.services.helpers.CommunityItem;
import com.greco.services.helpers.MemberItem;
import com.greco.services.helpers.ProfileItem;
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
		//Actualizamos contadores
		if (memberItem.isPendingMembership() ){
			//Ha pasado a estar pendiente.
			editMembershipBBean.setMembersCounter(editMembershipBBean.getMembersCounter()-1);
			editMembershipBBean.setPendingsCounter(editMembershipBBean.getPendingsCounter()+1);
			
			
		} 
		else { //Ha pasado a ser miembro activo
			editMembershipBBean.setMembersCounter(editMembershipBBean.getMembersCounter()+1);
			editMembershipBBean.setPendingsCounter(editMembershipBBean.getPendingsCounter()-1);
		}
			
		
		//Generamos mensaje para el log.
		String msg="UserCommunityID (" + memberItem.getMemberId() + ") ";
		msg += "New status ("+ memberItem.getStatus() + ")";
		
		log.log("007001", msg );//007001=INFO|Cambio de estado:
	}
	
	/**
	 *  Aprueba la suscripción (desde tab inicial)
	 */
	public void approve(MemberItem memberItem){
		
		memberItem.setStatus(new StatusItem(StatusItem.MEMBER));
		saveMembershipStatus(memberItem);
		
		
    	//Mostramos mensaje de éxito.
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,  
				memberItem.getNickname(),Warnings.getString("editmembership.approved_detail")); 
		FacesContext.getCurrentInstance().addMessage("editMembersForm:membership_msgs", fm);
	}
	
	/**
	 * Rechaza una petición de suscripción (desde tab inicial).
	 */
	public void reject(MemberItem memberItem){
		
		try {
			//Borrar de la tabla user_communities.
			editMembershipBBean.getUserCommunityDataProvider().removeMember(memberItem);
			
			//Actualizamos contadores.
			int count=editMembershipBBean.getPendingsCounter();
			editMembershipBBean.setPendingsCounter(count-1);
			
			//Grabamos el log
			String msg="userID (" + memberItem.getId() + ") COMMUNITYID(" + memberItem.getCommunityId() + ")";
			log.log("007004", msg ); //007004=INFO|Suscripción rechazada:
			
			
			//Enviamos correo al suscriptor rechazado.
			
			//Mostramos mensaje de suscripción rechazada.
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,  
					memberItem.getNickname(),Warnings.getString("editmembership.rejected_detail")); 
			FacesContext.getCurrentInstance().addMessage("editMembersForm:membership_msgs", fm);
			
		} catch (NoCommunityAdminException e) {
			//Generamos mensaje para el usuario.
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,new FacesMessage(Warnings.getString("editcommunity.nocommunityadmin_del_msg"),  
    				Warnings.getString("editcommunity.nocommunityadmin_del_detail" ) ) );
		}
	
	}
	
	
	/**
	 * Guarda el cambio de perfil (admin o usr), de un miembro.
	 * @param memberItem Miebro afectado
	 */
	public void saveMemberProfile(MemberItem memberItem){
		try {
			editMembershipBBean.getUserCommunityDataProvider().saveMemberProfile(memberItem);
			//Actualizamos contadores
			if (memberItem.isAdmin() ){
				//Ha pasado a ser administrador.
				editMembershipBBean.setAdminCounter(editMembershipBBean.getAdminCounter()+1);
				editMembershipBBean.setMembersCounter(editMembershipBBean.getMembersCounter()-1);
			} 
			else { //Ha dejado de ser administrador..
				editMembershipBBean.setAdminCounter(editMembershipBBean.getAdminCounter()-1);
				editMembershipBBean.setMembersCounter(editMembershipBBean.getMembersCounter()+1);
			}
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
	 * Da de baja de la comunidad al miembro seleccionado 
	 */
	public String unsubscribe(){
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String txtProperty = request.getParameter("selected");
                        
        int memberId=Integer.parseInt(txtProperty);
        MemberItem memberItem=editMembershipBBean.getUserCommunityDataProvider().find(memberId);
        if (memberItem!=null) {
			try {
				editMembershipBBean.getUserCommunityDataProvider().removeMember(memberItem);
				
				//Actualizamos contadores
				if (memberItem.isPendingMembership() ) //Aún no estaba suscrito
					editMembershipBBean.setMembersCounter(editMembershipBBean.getMembersCounter()-1);
				else //Era un miembro activo
					editMembershipBBean.setMembersCounter(editMembershipBBean.getMembersCounter()+1);
				
				//Enviamos correo.
					
		
				//Grabamos el log
				String msg="userID (" + memberItem.getId() + ") COMMUNITYID(" + memberItem.getCommunityId() + ")";
				log.log("007003", msg ); //007003=INFO|Baja de miembro:
				
			} catch (NoCommunityAdminException e) {
				//Generamos mensaje para el usuario.
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null,new FacesMessage(Warnings.getString("editcommunity.nocommunityadmin_del_msg"),  
	    				Warnings.getString("editcommunity.nocommunityadmin_del_detail" ) ) );
			}
        }
		return null;
	}
	
	/**
	 * Busca con el criterio fijado.
	 */
	public String search(){
		
		//Obtengo la comunidad seleccionada de Communities
		CommunitiesSBean comms = (CommunitiesSBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("communitiesSBean");
		CommunityItem communityItem=comms.getSelectedItem();
		
		//Genero el criterio
		
		Map<String, Object> criteria=new HashMap<String, Object>();
		String text=this.editMembershipBBean.getTextCriterion();
		if ( text!=null && !text.trim().equals("") ) 
			criteria.put(UserCommunitiesDAO.TEXT, this.editMembershipBBean.getTextCriterion() );
			
		
		//Perfil: administrador o simple miembro.
		if ( !this.editMembershipBBean.isAdminsSelectCriterion() )
			criteria.put(UserCommunitiesDAO.PROFILE, ProfileItem.USER);
		
		if ( !this.editMembershipBBean.isPendingsSelectCriterion() )
			criteria.put(UserCommunitiesDAO.STATUS, StatusItem.MEMBER );
		
		//Rango de fechas.
		//Desde
		if ( this.editMembershipBBean.getFromDateCriterion() != null) {
			criteria.put(UserCommunitiesDAO.SUBSCRIPTION_FROM_DATE, this.editMembershipBBean.getFromDateCriterion() );
		}
		//Hasta
		if ( this.editMembershipBBean.getToDateCriterion() != null) {
			criteria.put(UserCommunitiesDAO.SUBSCRIPTION_TO_DATE, this.editMembershipBBean.getToDateCriterion() );
		}
		
		
		//Ordenación
		SortOrder sortOrder;
		switch (this.editMembershipBBean.getSortOrderCriterion()){
		case 0:
			sortOrder=SortOrder.ASCENDING;
			break;
		case 1:
			sortOrder=SortOrder.DESCENDING;
			break;
		default:
			sortOrder=SortOrder.ASCENDING;
		}
		
		//Lanzamos la búsqueda
		editMembershipBBean.setMembers(this.editMembershipBBean.getUserCommunityDataProvider().findRangeOrder(communityItem, 
				criteria, 
				0, 25, 
				this.editMembershipBBean.getOrderedByCriterion(), sortOrder));
		
		return null;
		
	}
	
	
	

	//GETTERs y SETTERs
	public EditMembershipBBean getEditMembershipBBean() {
		return editMembershipBBean;
	}

	public void setEditMembershipBBean(EditMembershipBBean editMembershipBBean) {
		this.editMembershipBBean = editMembershipBBean;
	}
	
	
	
	

	
}
