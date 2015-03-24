package com.greco.beans;


import java.io.Serializable;

import com.greco.services.helpers.CommunityItem;

public class CommunitiesListCBean implements Serializable{
	private static final long serialVersionUID = 8155812939527574793L;

	private CommunitiesSBean communitiesSBean; //Inyectado
	private ResourcesSBean resourcesSBean; //Inyectado.

	/**
	 * Edita la comunidad seleccionada.
	 * @param communityItem
	 * @return
	 */
	public String edit(CommunityItem communityItem){
		communitiesSBean.setSelectedItem(communityItem);
		//Actualizamos la lista de recursos correspondientes
		resourcesSBean.setCommunityItem(communityItem);
		
		return "edit";
	}
	
	/**
	 * Crea una nueva comunidad
	 */
	public String addCommunity(){
		//La comunidad seleccionada es null por ser nueva.
		communitiesSBean.setSelectedItem(null);
		//Actualizamos la lista de recursos, creando una lista vacía.
		resourcesSBean.setCommunityItem(null);
		return "new";
	}

	
	//GETTERs y SETTERs
	public CommunitiesSBean getCommunitiesListSBean() {
		return communitiesSBean;
	}

	public void setCommunitiesSBean(CommunitiesSBean communitiesSBean) {
		this.communitiesSBean = communitiesSBean;
	}

	public ResourcesSBean getResourcesSBean() {
		return resourcesSBean;
	}

	public void setResourcesSBean(ResourcesSBean resourcesSBean) {
		this.resourcesSBean = resourcesSBean;
	}
		
}
