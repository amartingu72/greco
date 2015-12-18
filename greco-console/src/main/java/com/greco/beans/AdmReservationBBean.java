package com.greco.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.joda.time.DateTime;

import com.greco.services.helpers.CommunityItem;
import com.greco.services.helpers.ReservationItem;
import com.greco.services.helpers.ResourceItem;

public class AdmReservationBBean {
	
	private String userPattern;
	private Date fromDate;
	private Date toDate;
	private List<ReservationItem> reservations;
	private List<SelectItem> resources;    
    private String resourceSelected;
    private ResourceTypesSBean resourceTypesSBean; //Inyectado
    private ResourcesSBean resourcesSBean; //Inyectado
    private CommunityItem communityItem;
    private boolean searchLaunched; //Indica si se ha pulsado alguna vez el botón buscar.
    
	/**
	 * Devuelve el número de reservas recuperadas tras la búsqueda.
	 * @return
	 */
    public int getReservationsFound(){
    	int ret=0;
    	if ( reservations != null )
    		ret=reservations.size();
    	return ret;
    }
        
    private void loadResources(){
    	
    	
    	resources = new ArrayList<SelectItem>();
    	List<String> types=resourcesSBean.getTypes();
    	Iterator<String> it=types.iterator();
    	String type;
    	SelectItemGroup group=null;
    	//ResourceItem wildCard=new ResourceItem(0, "all", "all", "");
    	
    	resources.add(new SelectItem("type_all", "Todos")); //Por defecto, se buscan todos los tipos de recurso.
    	resourceSelected="type_all";
    	
    	List<ResourceItem> resourceItems;
    	ResourceItem resourceItem;
    	Iterator<ResourceItem> resourceIt;
    	
    	SelectItem[] selectItems;
    	
    	while (it.hasNext()){ //Por cada tipo.
    		type=it.next();
    		//Tipo
    		group = new SelectItemGroup(resourceTypesSBean.getResourceTypeItem(type).getDescription());
    		
    		//Recursos de ese tipo
    		resourceItems=resourcesSBean.getResources(type);
    		resourceIt=resourceItems.iterator();
    		
    		
    		int i=0;
    		
    		if ( resourceItems.size()>1 ){
    			selectItems=new SelectItem[resourceItems.size()+1];
    			selectItems[i]=new SelectItem("type_"+type, "Todos");
    			i++;
    		} else selectItems=new SelectItem[resourceItems.size()];
    		
    		while ( resourceIt.hasNext() ){
    			resourceItem=resourceIt.next();
    			selectItems[i]=new SelectItem("rsrc_"+resourceItem.getId(), resourceItem.getName());
    			i++;
    		}
    		group.setSelectItems(selectItems);
    		resources.add(group);
    	}
    	
    	
        
    	
    }
	
	

	@PostConstruct
	public void initialize() {
		
		CommunitiesSBean communitiesSBean= (CommunitiesSBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("communitiesSBean");
		communityItem=communitiesSBean.getSelectedItem();
		userPattern="";
		fromDate=(new DateTime(communityItem.getDateTimeZone())).toDate();
		toDate=fromDate;
		loadResources();
		reservations=null;
		searchLaunched=false;
		
		
		
	}

	public boolean isReservationsEmpty() {
		//Si es null es que nunca he pulsado buscar y no que esté vacío.
		return (this.reservations != null && this.reservations.isEmpty()); 
	}
	/**
	 * Devuelve el item de reserva correspondiente al identificador, si está en la consulta o null si no lo encuentra.
	 * @param reservationId Identificador de reserva
	 * @return Item de reserva.
	 */
	public ReservationItem getReservationItem(int reservationId){
		ReservationItem reservationItem=null;
		boolean found=false;
		Iterator<ReservationItem> it=reservations.iterator();
		while ( it.hasNext() && !found ){
			reservationItem=it.next();
			found=(reservationItem.getId()==reservationId);
		}
		if (!found) reservationItem=null;
		return reservationItem;
	}
	
	/**
	 * Elimina un item de reserva de la lista de reservas.
	 * @param item
	 */
	public void removeReservationItem(ReservationItem item){
		int i=this.reservations.indexOf(item);
		if ( i>=0) this.reservations.remove(i);  //Borro si lo encuentro.
	}
	
	
	
	//GETTERs y SETTERs
	public String getUserPattern() {
		return userPattern;
	}


	public void setUserPattern(String userPattern) {
		this.userPattern = userPattern;
	}


	public Date getFromDate() {
		return fromDate;
	}


	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}


	public Date getToDate() {
		return toDate;
	}


	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}


	public List<ReservationItem> getReservations() {
		return reservations;
	}


	public void setReservations(List<ReservationItem> reservations) {
		this.reservations = reservations;
	}
	public List<SelectItem> getResources() {
		return resources;
	}

	public void setResources(List<SelectItem> resources) {
		this.resources = resources;
	}

	



	public String getResourceSelected() {
		return resourceSelected;
	}



	public void setResourceSelected(String resourceSelected) {
		this.resourceSelected = resourceSelected;
	}



	public ResourceTypesSBean getResourceTypesSBean() {
		return resourceTypesSBean;
	}



	public void setResourceTypesSBean(ResourceTypesSBean resourceTypesSBean) {
		this.resourceTypesSBean = resourceTypesSBean;
	}



	public ResourcesSBean getResourcesSBean() {
		return resourcesSBean;
	}



	public void setResourcesSBean(ResourcesSBean resourcesSBean) {
		this.resourcesSBean = resourcesSBean;
	}



	public CommunityItem getCommunityItem() {
		return communityItem;
	}



	public void setCommunityItem(CommunityItem communityItem) {
		this.communityItem = communityItem;
	}

	public boolean isSearchLaunched() {
		return searchLaunched;
	}

	public void setSearchLaunched(boolean searchLaunched) {
		this.searchLaunched = searchLaunched;
	}
	
	
	
	
	

}
