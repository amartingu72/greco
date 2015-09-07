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
	
	//Para la pesta�a de consulta avanzado
	//private LazyDataModel<MemberItem> members;
	List<MemberItem> members;
	
	//Suscripciones pendientes de aprobaci�n. Para la pesta�a de inicio.
	private List<MemberItem> pendings; 
	
	private UserCommunityDataProvider userCommunityDataProvider; //inyectado
	
	//Miembro seleccionado cuando se pulsa eliminar (o dar de baja).
	private MemberItem selectedMember;
	

	
	//Contadores
	private int adminCounter;
	private int pendingsCounter;
	private int membersCounter;
	private int summary;   //Suma de los anteriores.
	
	//Criterios de b�squeda
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
			
		
		
		//Cargo las fichas de pendientes de suscripci�n
		pendings=userCommunityDataProvider.findPendingMemberships(item);
		
		//Asigno valor inicial a los contadores.
		this.pendingsCounter=pendings.size();
		this.adminCounter=this.userCommunityDataProvider.getAdmins(item);
		this.membersCounter=this.userCommunityDataProvider.getMembers(item);
		this.summary=this.adminCounter+this.membersCounter+this.pendingsCounter;
		
		//Asigno valor inicial a los criterios de b�squeda
		textCriterion="";
		adminsSelectCriterion=true;
		pendingsSelectCriterion=true;
		
		fromDateCriterion=null;
		toDateCriterion=item.getLocalTime().toDate();
		orderedByCriterion="registerdate";
		sortOrderCriterion=0;
		
		//Inicializo el objeto que gestionar� la tabla de miembros.
		//members=new LazyMembersDataModel(item, userCommunityDataProvider);
		members=userCommunityDataProvider.findRangeOrder(item, null, 0, 25, "nickname", SortOrder.ASCENDING);
		
		
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
