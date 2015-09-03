package com.greco.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.greco.services.UserCommunityDataProvider;
import com.greco.services.helpers.CommunityItem;
import com.greco.services.helpers.LazyMembersDataModel;
import com.greco.services.helpers.MemberItem;

public class EditMembershipBBean {
	
	//Para la pestaña de consulta avanzado
	//private LazyDataModel<MemberItem> members;
	List<MemberItem> members;
	
	//Suscripciones pendientes de aprobación. Para la pestaña de inicio.
	private List<MemberItem> pendings; 
	
	private UserCommunityDataProvider userCommunityDataProvider; //inyectado
	
	//Miembro seleccionado cuando se pulsa eliminar (o dar de baja).
	private MemberItem selectedMember;
	

	
	//Contadores
	private int adminCounter;
	private int pendingsCounter;
	private int membersCounter;
	private int summary;   //Suma de los anteriores.
	
	
	

	@PostConstruct
	public void initialize() {
		//Obtengo la comunidad seleccionada de Communities
		CommunitiesSBean comms = (CommunitiesSBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("communitiesSBean");
		CommunityItem item=comms.getSelectedItem();
			
		//Inicializo el objeto que gestionará la tabla de miembros.
		//members=new LazyMembersDataModel(item, userCommunityDataProvider);
		members=userCommunityDataProvider.findRangeOrder(item, null, 0, 500, "nickname", SortOrder.ASCENDING);
		
		//Cargo las fichas de pendientes de suscripción
		pendings=userCommunityDataProvider.findPendingMemberships(item);
		
		//Asigno valor inicial a los contadores.
		this.pendingsCounter=pendings.size();
		this.adminCounter=this.userCommunityDataProvider.getAdmins(item);
		this.membersCounter=this.userCommunityDataProvider.getMembers(item);
		this.summary=this.adminCounter+this.membersCounter+this.pendingsCounter;
		
		
		
	}
	
	
	
	//GETTERS y SETTERs


	public List<MemberItem> getMembers() {
		return members;
	}


	public void setMembers(List<MemberItem> members) {
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

	public List<MemberItem> getPendings() {
		return pendings;
	}

	public void setPendings(List<MemberItem> pendings) {
		this.pendings = pendings;
	}

	public int getAdminCounter() {
		return adminCounter;
	}

	public void setAdminCounter(int adminCounter) {
		this.adminCounter = adminCounter;
	}

	public int getPendingsCounter() {
		return pendingsCounter;
	}

	public void setPendingsCounter(int pendingsCounter) {
		this.pendingsCounter = pendingsCounter;
	}

	public int getMembersCounter() {
		return membersCounter;
	}

	public void setMembersCounter(int membersCounter) {
		this.membersCounter = membersCounter;
	}

	public int getSummary() {
		return summary;
	}

	public void setSummary(int summary) {
		this.summary = summary;
	}



	
	
	
}
