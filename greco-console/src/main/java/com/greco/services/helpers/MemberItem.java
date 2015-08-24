package com.greco.services.helpers;

import java.sql.Timestamp;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import com.greco.beans.ProfilesSBean;



public class MemberItem extends UserItem{
	
	
	private int memberId;
	
	private int communityId;
	
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
	
	
	
	//GETTERs y SETTERs	
	public int getCommunityId() {
		return communityId;
	}
	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}
	
	public String getJoinningDate() {
		DateTime jodatime = new DateTime(joinningDate);
		DateTimeFormatter dtfOut = DateTimeFormat.forPattern("dd/MM/yyyy");
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

	
}
