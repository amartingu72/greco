package com.greco.beans;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;



import com.greco.services.helpers.CommunityItem;


public class CommunitiesListBBean implements Serializable{
	
	private static final long serialVersionUID = -2492929364241535103L;
	

	
	//Atributo utilizado únicamente para ordenar la lista de comunidades.
	private List<CommunityItem> myCommunitiesArrayList;
	
	private List<CommunityItem> myCommunities; 
	//Comunidad seleccionada
	private CommunityItem selectedItem=null;
		
	
	public CommunityItem getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(CommunityItem selectedItem) {
		this.selectedItem = selectedItem;
	}

	
	/**
	 * Carga la tabla de comunidades
	 */
	@PostConstruct
	public void initialize() {
		
		
		//Ordeno la lista por código postal.
		myCommunitiesArrayList = new ArrayList<CommunityItem>(myCommunities);
		Collections.sort(myCommunitiesArrayList, new Comparator<CommunityItem>() {
			@Override
			public int compare(CommunityItem comm1, CommunityItem comm2) {
				return comm1.getName().compareTo(comm2.getName());
			}
		});
		
	}
	

	public List<CommunityItem> getMyCommunities() { 
		return myCommunitiesArrayList; 
	}

	public void setMyCommunities(List<CommunityItem> myCommunities) {
		this.myCommunities = myCommunities;
	}
	/**
	 * Asigna el id de la comunidad seleccionada y, por el id, carga el item de comunidad seleccionada.
	 * @param id
	 */
	public void setSelectedCommunityId(int id){
		Iterator<CommunityItem> it= myCommunities.iterator();
		boolean bfound=false;
		CommunityItem communityItem=null;
		while (!bfound && it.hasNext() ){
			communityItem=it.next();
			bfound=communityItem.getId()==id;
		}
		if (!bfound) communityItem=null;
		else this.selectedItem=communityItem;
	}
	/**
	 * Devuelve el id de item de comunidad seleccionado o 0 si no se ha seleccionado ninguno.
	 * @return Id de la comunidad seleccionada.
	 */
	public int getSelectedCommunityId(){
		int ret=1;
		if (this.selectedItem!=null)
			ret=selectedItem.getId();
		return ret;
	}

	public boolean isClear() {
		
			return this.myCommunities.isEmpty();
		
	}

	
	
	

	

	
	
}
