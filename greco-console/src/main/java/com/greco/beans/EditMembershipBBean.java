package com.greco.beans;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import org.primefaces.model.LazyDataModel;

import com.greco.services.UserCommunityDataProvider;
import com.greco.services.helpers.CommunityItem;
import com.greco.services.helpers.LazyMembersDataModel;
import com.greco.services.helpers.MemberItem;

public class EditMembershipBBean {
	private LazyDataModel<MemberItem> members;
	private UserCommunityDataProvider userCommunityDataProvider; //inyectado
	//Miembro seleccionado cuando se pulsa eliminar (o dar de baja).
	private MemberItem selectedMember;
	
	
	@PostConstruct
	public void initialize() {
		//Obtengo la comunidad seleccionada de Communities
		CommunitiesSBean comms = (CommunitiesSBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("communitiesSBean");
		CommunityItem item=comms.getSelectedItem();
			
		//Inicializo el objeto que gestionará la tabla de miembros.
		members=new LazyMembersDataModel(item, userCommunityDataProvider);
	}
	
	public int getWaitingMembers(){
		return 0;
	}
	
	public int getAdmins(){
		return 0;
	}
	
	public int getActiveMembers(){
		return 0;
	} 
	
	public int getTotalMembers(){
		return getWaitingMembers()+getAdmins()+getActiveMembers();
	} 
	
	//GETTERS y SETTERs


	public LazyDataModel<MemberItem> getMembers() {
		return members;
	}


	public void setMembers(LazyDataModel<MemberItem> members) {
		this.members = members;
	}


	public UserCommunityDataProvider getUserCommunityDataProvider() {
		return userCommunityDataProvider;
	}


	public void setUserCommunityDataProvider(
			UserCommunityDataProvider userCommunityDataProvider) {
		this.userCommunityDataProvider = userCommunityDataProvider;
	}

	public MemberItem getSelectedMember() {
		return selectedMember;
	}

	public void setSelectedMember(MemberItem selectedMember) {
		this.selectedMember = selectedMember;
	}
	
	
	
}
