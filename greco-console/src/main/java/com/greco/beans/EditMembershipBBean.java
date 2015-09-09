package com.greco.beans;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;



//import org.primefaces.model.LazyDataModel;



import org.primefaces.model.SortOrder;

import com.greco.services.UserCommunityDataProvider;
import com.greco.services.helpers.CommunityItem;
//import com.greco.services.helpers.LazyMembersDataModel;
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
	
	//Criterios de búsqueda
	private String textCriterion;
	private boolean adminsSelectCriterion;
	private boolean pendingsSelectCriterion;

	private Date fromDateCriterion;
	private Date toDateCriterion;
	private String orderedByCriterion;
	private int sortOrderCriterion;
	
	
	
	
	

	@PostConstruct
	public void initialize() {
		//Obtengo la comunidad seleccionada de Communities
		CommunitiesSBean comms = (CommunitiesSBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("communitiesSBean");
		CommunityItem item=comms.getSelectedItem();
			
		
		
		//Cargo las fichas de pendientes de suscripción
		pendings=userCommunityDataProvider.findPendingMemberships(item);
		
		//Asigno valor inicial a los contadores.
		this.pendingsCounter=pendings.size();
		this.adminCounter=this.userCommunityDataProvider.getAdmins(item);
		this.membersCounter=this.userCommunityDataProvider.getMembers(item);
		
		
		//Asigno valor inicial a los criterios de búsqueda
		textCriterion="";
		adminsSelectCriterion=true;
		pendingsSelectCriterion=true;
		
		fromDateCriterion=null;
		toDateCriterion=item.getLocalTime().plusDays(1).toDate(); //Ponemos el día siguiente para que, por defecto, salgan todos. 
		orderedByCriterion="registerdate";
		sortOrderCriterion=0;
		
		//Inicializo el objeto que gestionará la búsqueda
		//members=new LazyMembersDataModel(item, userCommunityDataProvider);
		members=this.userCommunityDataProvider.findRangeOrder(item, 
				null, 
				0, 25, 
				orderedByCriterion, SortOrder.ASCENDING);
		
		
	}
	
	
	/**
	 * Actualiza la lista (no en base de datos), de miembros y, por tanto, las fichas visible en el tab inicial.
	 * @param memberItem Miebmbro
	 * @param delete Indica si hay que borrar el miembro indicado (true), o solo sustituir (false)
	 */
	public void updateMembersList(MemberItem memberItem, boolean delete){
		int i=members.indexOf(memberItem);
		if ( i >= 0){
			if ( delete ) 
				members.remove(i);
			else 
				members.set(i, memberItem);
		} 
		
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
		return adminCounter+membersCounter+pendingsCounter;
	}

	
	public String getTextCriterion() {
		return textCriterion;
	}



	public void setTextCriterion(String textCriterion) {
		this.textCriterion = textCriterion;
	}



	public boolean isAdminsSelectCriterion() {
		return adminsSelectCriterion;
	}



	public void setAdminsSelectCriterion(boolean adminsSelectCriterion) {
		this.adminsSelectCriterion = adminsSelectCriterion;
	}



	public boolean isPendingsSelectCriterion() {
		return pendingsSelectCriterion;
	}



	public void setPendingsSelectCriterion(boolean pendingsSelectCriterion) {
		this.pendingsSelectCriterion = pendingsSelectCriterion;
	}

	public Date getFromDateCriterion() {
		return fromDateCriterion;
	}



	public void setFromDateCriterion(Date fromDateCriterion) {
		this.fromDateCriterion = fromDateCriterion;
	}



	public Date getToDateCriterion() {
		return toDateCriterion;
	}



	public void setToDateCriterion(Date toDateCriterion) {
		this.toDateCriterion = toDateCriterion;
	}



	public String getOrderedByCriterion() {
		return orderedByCriterion;
	}



	public void setOrderedByCriterion(String orderedByCriterion) {
		this.orderedByCriterion = orderedByCriterion;
	}



	public int getSortOrderCriterion() {
		return sortOrderCriterion;
	}



	public void setSortOrderCriterion(int sortOrderCriterion) {
		this.sortOrderCriterion = sortOrderCriterion;
	}



	

	
	
	
}
