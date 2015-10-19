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
	
    
    private void loadResources(){
    	
    	
    	resources = new ArrayList<SelectItem>();
    	List<String> types=resourcesSBean.getTypes();
    	Iterator<String> it=types.iterator();
    	String type;
    	SelectItemGroup group=null;
    	
    	resources.add(new SelectItem("Todos"));
    	
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
    		selectItems=new SelectItem[resourceItems.size()+1];
    		selectItems[0]=new SelectItem("all", "Todos");
    		int i=1;
    		
    		while ( resourceIt.hasNext() ){
    			resourceItem=resourceIt.next();
    			selectItems[i]=new SelectItem(resourceItem, resourceItem.getName());
    			i++;
    		}
    		group.setSelectItems(selectItems);
    		resources.add(group);
    	}
    	
    	
        
    	
    }
	
	

	@PostConstruct
	public void initialize() {
		
		CommunitiesSBean communitiesSBean= (CommunitiesSBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("communitiesSBean");
		CommunityItem item=communitiesSBean.getSelectedItem();
		userPattern="";
		fromDate=(new DateTime(item.getDateTimeZone())).toDate();
		toDate=fromDate;
		loadResources();
		
		
		
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
	
	
	
	
	

}
