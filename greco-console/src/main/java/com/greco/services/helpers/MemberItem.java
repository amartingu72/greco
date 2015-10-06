package com.greco.services.helpers;

import java.sql.Timestamp;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.greco.beans.ProfilesSBean;
import com.greco.utils.Warnings;



public class MemberItem extends UserItem{
	
	
	private int memberId;
	
		
	private CommunityItem communityItem;
	
	private Timestamp joinningDate;
	
	private ProfileItem memberProfile;
	/**
	 * Estado: sus valores posibles son 'PENDING' o 'MEMBER'
	 */
	private StatusItem status;
	
	/**
	 * Datos relacionados con la suscripción. Dotará de criterio el adminstrador para admitirle en la comunidad o no.
	 */
	private String application;
	
	/**
	 * Causa por la que se rechaza (solo en el caso de que la solicitud de suscripción sera rechazada).
	 */
	private String rejectionReason;
	/**
	 * Se diferencia del getter normal (getApplicacion), en que, si no se facilita info de suscripción,
	 * devulve un mensaje indicándolo
	 * @return
	 */
	public String getApplicationNotNull() {
		String ret=application;
		if ( application == null){
			ret=Warnings.getString("editmembership.no_application");
		}
			
		return ret;
	}
	
	/**
	 * Indica si el miembro tiene perfil de administrador.
	 * @return Si(true) o no (false).
	 */
	public boolean isAdmin() {
		return memberProfile.isAdmin();
	}
	
		
	/**
	 * Asigna un perfil de usuario o de administrador al miembro.
	 * @param isAdmin true (de administrador) o false (de miembro).
	 */
	public void setAdmin(boolean isAdmin) {
		
		if (isAdmin) this.memberProfile=ProfilesSBean.getAdminProfile();
		else this.memberProfile=ProfilesSBean.getUserProfile();
		
	}
	
	/**
	 * Indica si tiene o no pendiente la aceptación como miembro.
	 * @return Si(true) o no (false).
	 */
	public boolean isPendingMembership() {
		return !status.isMember();
	}
	
		
	/**
	 * Asigna la condición de miembro o pone en situación de pendiente.
	 * @param isPending False (asignar como miembro) o true (asignar como pendiente de aprobación).
	 */
	public void setPendingMembership(boolean isPendingMembership) {
		if (isPendingMembership) status.setStatus(StatusItem.PENDING);
		else status.setStatus(StatusItem.MEMBER);
		
	}
	
	
	
	@Override
	public boolean equals(Object obj) {
		return ((MemberItem)obj).getMemberId()==this.memberId;
	}

	//GETTERs y SETTERs	
	public int getCommunityId() {
		return this.communityItem.getId();
	}
	
	
	public String getJoinningDate() {
		DateTime jodatime = new DateTime(joinningDate,communityItem.getDateTimeZone());
		DateTimeFormatter dtfOut = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
		return dtfOut.print(jodatime);
	}
	public void setJoinningDate(Timestamp joinningDate) {
		this.joinningDate = joinningDate;
	}
	public String getStatus() {
		
		return status.toString();
	}
	public void setStatus(StatusItem status) {
		this.status = status;
	}
	
	public int getStatusId(){
		return status.getStatus();
	}
	
	public ProfileItem getMemberProfile() {
		return memberProfile;
	}
	public void setMemberProfile(ProfileItem memberProfile) {
		this.memberProfile = memberProfile;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}


	public String getRejectionReason() {
		return rejectionReason;
	}


	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public CommunityItem getCommunityItem() {
		return communityItem;
	}

	public void setCommunityItem(CommunityItem communityItem) {
		this.communityItem = communityItem; 
	}
	
	

	
}
