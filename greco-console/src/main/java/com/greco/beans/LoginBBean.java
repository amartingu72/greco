package com.greco.beans;


import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import com.greco.services.CommunityDataProvider;
import com.greco.services.helpers.CommunityItem;
import com.greco.services.helpers.ResourceTypeItem;



public class LoginBBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String email;
	private String password;
	private int communityId; 
	private CommunityItem communityItem;
	private ResourceTypeItem[] resourceTypeList;
	
	private CommunityDataProvider communityDataProvider; //Inyectado
	private boolean accessDisabled; //Solo para accesos de comunidad. Se habilita solo el acceso si hay algún recurso configurado.


	/**
	 * Valida que el parámetro communityid (GET request), tiene una comunidad reconocida.
	 * Solo se invoca cuando se hace login desde una comunidad y nunca desde la página de administración.
	 * @return
	 */
	public String checkParam(){
		String ret=null;
		accessDisabled=false;
		//Si el ID de comunidad pasasdo como parámetro (GET), tiene un valor válido, le buscamos.
		if (communityId != -1)
			communityItem=communityDataProvider.getCommunityById(communityId);
		if (communityItem == null) ret="notfound";
		else{
			//Cargamos los tipos de recurso que la comunidad tiene configurados. 
			//De esto dependerá las imágenes que se mostrarán en la página de 
			resourceTypeList=communityDataProvider.getResourceTypes(communityItem);
			//Si no tiene recursos, ponemos uno para que la página indique que aún no se han creado recursos.
			if (resourceTypeList.length == 0) {
				ResourceTypeItem rtItem=new ResourceTypeItem(0, "", "");
				resourceTypeList=new ResourceTypeItem[1];
				resourceTypeList[0]=rtItem;
				accessDisabled=true; //Desabilitamos el botón de acceso.
			}
		}
		
		return ret;
	}
	
	@PostConstruct
	public void init(){
		//Si la comunidad viene en un parámetro de la URL la utilizo.
		Map<String,String> params =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String comId=params.get("communityid");
		if ( (comId != null) && (comId != "0")   ) {
			try{
				communityId= Integer.parseInt(comId);
			} catch (NumberFormatException e){
				//Asigno -1 para indicar que el parámetro tiene un valor erroneo.
				communityId=-1;
			}
			
				
		}
			
	}
	
	
	
	/**
	 * Igual que getCommunity pero no verifica si ha venido un parámetro por URL.
	 * @return
	 */
	public int getSelectedCommunity(){
		return communityId;
	}
	
	
	public int getCommunityId(){
		return communityId;
		
	}
	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email=email;
	}
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password=password;
	}



	public CommunityItem getCommunityItem() {
		return communityItem;
	}



	public void setCommunityItem(CommunityItem communityItem) {
		this.communityItem = communityItem;
	}



	public CommunityDataProvider getCommunityDataProvider() {
		return communityDataProvider;
	}



	public void setCommunityDataProvider(CommunityDataProvider communityDataProvider) {
		this.communityDataProvider = communityDataProvider;
	}

	public ResourceTypeItem[] getResourceTypeList() {
		return resourceTypeList;
	}

	public void setResourceTypeList(ResourceTypeItem[] resourceTypeList) {
		this.resourceTypeList = resourceTypeList;
	}

	public boolean isAccessDisabled() {
		return accessDisabled;
	}

	public void setAccessDisabled(boolean accessDisabled) {
		this.accessDisabled = accessDisabled;
	}
	
	
		
	
}
