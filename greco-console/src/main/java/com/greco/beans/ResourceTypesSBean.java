package com.greco.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import com.greco.services.ResourceTypeDataProvider;
import com.greco.services.helpers.ResourceTypeItem;

/**
 * Support managed bean. Su finalidad es mantener la lista de pa�ses disponibles.
 * @author Alberto Mart�n
 *
 */
public class ResourceTypesSBean {
	/**
	 * Colecci�n de pa�ses en en un formato v�lido para un combo.
	 */
	private Collection<SelectItem> resourceTypes;
	ResourceTypeDataProvider resourceTypeDataProvider; //Inyectado.
	
	
	@PostConstruct
	public void initialize() {
		//Obtengo la lista de pa�ses.
		resourceTypes = new ArrayList<SelectItem>();
		List<ResourceTypeItem> resourceTypeList=Arrays.asList(resourceTypeDataProvider.getResourceTypesArray());
		Iterator<ResourceTypeItem> it=resourceTypeList.iterator();
		ResourceTypeItem resourceTypeItem;
		while ( it.hasNext() ) {
			resourceTypeItem=it.next();
			resourceTypes.add(new SelectItem(resourceTypeItem,resourceTypeItem.getDescription() ));
		}
	}
	
	/**
	 * Devuelve la descripci�n asociada al nombre
	 * @param name nombre del tipo
	 * @return Descripci�n.
	 */
	public String getDescription(String name){
		Iterator<SelectItem> it=resourceTypes.iterator();
		boolean bFound=false;
		ResourceTypeItem selectItem;
		String ret="";
		while (it.hasNext() && !bFound ){
			selectItem=(ResourceTypeItem)it.next().getValue();
			bFound=selectItem.getName().equals(name);
			if (bFound) ret=selectItem.getDescription();
		}
		return ret;
		
	}
	
	/**
	 * Devuelve el item de tipo recurso asociado al nombre indicado.
	 * @param name Nombre del tipo de recurso.
	 * @return Objeto tipo de recurso asociado.
	 */
	public ResourceTypeItem getResourceTypeItem(String name) {
		Iterator<SelectItem> it=resourceTypes.iterator();
		SelectItem selectItem;
		ResourceTypeItem resourceTypeItem=null;
		boolean found=false;
		while ( it.hasNext() && !found ) {
			selectItem=it.next();
			if ( selectItem.getLabel().equals(name) || ((ResourceTypeItem)selectItem.getValue()).getName().equals(name) ) {
				resourceTypeItem=(ResourceTypeItem)selectItem.getValue();
				found=true;
			}
			
		}
		return resourceTypeItem;
	}

	//GETTERs y SETTERs
	public Collection<SelectItem> getResourceTypes() {
		return resourceTypes;
	}


	public void setResourceTypes(Collection<SelectItem> resourceTypes) {
		this.resourceTypes = resourceTypes;
	}


	public ResourceTypeDataProvider getResourceTypeDataProvider() {
		return resourceTypeDataProvider;
	}


	public void setResourceTypeDataProvider(
			ResourceTypeDataProvider resourceTypeDataProvider) {
		this.resourceTypeDataProvider = resourceTypeDataProvider;
	}
	
	



	
	
}
