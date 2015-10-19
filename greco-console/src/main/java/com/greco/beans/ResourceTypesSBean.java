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
 * Support managed bean. Su finalidad es mantener la lista de países disponibles.
 * @author Alberto Martín
 *
 */
public class ResourceTypesSBean {
	/**
	 * Colección de países en en un formato válido para un combo.
	 */
	private Collection<SelectItem> resourceTypes;
	ResourceTypeDataProvider resourceTypeDataProvider; //Inyectado.
	
	
	@PostConstruct
	public void initialize() {
		//Obtengo la lista de países.
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
