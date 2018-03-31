package com.greco.beans;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import com.greco.services.helpers.CommunityItem;
import com.greco.services.helpers.ResourceItem;

public class EditCommunityBBean implements Serializable{
	
	private static final long serialVersionUID = 8805026487804632813L;
	
	private int id;
	private String name;
	private String country;
	private String zipcode;
	private String mydata;
	private boolean available;
	private boolean membercheck;
	private String joiningDate;
	private String profiles;
	private String site;  //URL del la comunidad.
	private boolean saveBtnVisible;
	
	
	private List <ResourceItem> myResources; //inyectado	
	
	
	
	//Recurso seleccionado cuando se pulsa editar.
	private ResourceItem selectedItem;
	
	//Miembro seleccionado cuando se pulsa eliminar (o dar de baja).
	//private MemberItem selectedMember;
	
	
	@PostConstruct
	public void initialize() {
		//Obtengo la comunidad seleccionada de Communities
		CommunitiesSBean comms = (CommunitiesSBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("communitiesSBean");
		CommunityItem item=comms.getSelectedItem();
		
		//Inicializo el objeto que gestionará la tabla de miembros.
		//members=new LazyMembersDataModel(item, userCommunityDataProvider);
						
		this.id=item.getId();
		this.name=item.getName();
		this.country=item.getCountry();
		this.zipcode=item.getZipcode();
		this.mydata=item.getMyData();
		this.available=item.isAvailable();
		this.membercheck=item.isMembercheck();
		this.joiningDate=item.getJoiningDate();
		this.profiles=item.getProfiles();
		
		//Monto la URL de site.
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String[] url = req.getRequestURL().toString().split("sections");
		this.site=url[0] + "sites?communityid=" + item.getId();
		
		//Botón 'Guardar' visible.
		this.saveBtnVisible=true;
	}
	
	public ResourceItem getResourceItem(int resourceId){
		boolean bFound=false;
		ResourceItem resourceItem=null;
		Iterator<ResourceItem> it=this.myResources.iterator();
		while (it.hasNext() && !bFound){
			resourceItem=it.next();
			bFound=(resourceItem.getId()==resourceId);
		}
		return resourceItem;
	}
	
	//GETTERs y SETTERs	

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getZipcode() {
		return zipcode;
	}
	
	public List<ResourceItem> getMyResources() { 
		return myResources; 
	}
		
	public void setMyResources(List<ResourceItem> myResources) {
		this.myResources = myResources;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	public String getMydata() {
		return mydata;
	}
	
	public void setMydata(String mydata) {
		this.mydata = mydata;
	}

	public ResourceItem getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(ResourceItem selectedItem) {
		this.selectedItem = selectedItem;
	}

	public String getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}

	public String getProfiles() {
		return profiles;
	}

	public void setProfiles(String profiles) {
		this.profiles = profiles;
	}

	

	public boolean isSaveBtnVisible() {
		return saveBtnVisible;
	}

	public void setSaveBtnVisible(boolean saveBtnVisible) {
		this.saveBtnVisible = saveBtnVisible;
	}

	

	public String getSite() {
		return site;
	}

	public boolean isMembercheck() {
		return membercheck;
	}

	public void setMembercheck(boolean membercheck) {
		this.membercheck = membercheck;
	}
	
	
	
}
