package com.greco.services.helpers;


/**
 * 
 * Gestiona la lista de tipos de recurso de una comunidad.
 * @author AMG
 *
 */
public class ResourceItemGroup extends ResourceTypeItem {
	
	ResourceItem[] resourceItems;

	public ResourceItemGroup(int id, String name, String description) {
		super(id, name, description);
		resourceItems=null;
	}
	
	public ResourceItemGroup(ResourceTypeItem resourceItemType) {
		super(resourceItemType.getId(), resourceItemType.getName(), resourceItemType.getDescription());
		resourceItems=null;
	}
	
	//GETTERS y SETTERS
	public ResourceItem[] getResourceItems() {
		return resourceItems;
	}

	public void setResourceItems(ResourceItem[] resourceItems) {
		this.resourceItems = resourceItems;
	}
	
}
