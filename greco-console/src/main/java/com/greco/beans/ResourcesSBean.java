package com.greco.beans;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.greco.services.ResourceDataProvider;
import com.greco.services.helpers.CommunityItem;
import com.greco.services.helpers.ResourceItem;
/**
 * Bean de soporte.
 * @author Alberto Martín
 *
 */
public class ResourcesSBean implements Serializable {
	
	private static final long serialVersionUID = 6944498142528394204L;
	
	
	private List<ResourceItem> myResources;
	private ResourceItem selectedResource;
	
	private CommunityItem communityItem;  
	private ResourceDataProvider resourceDataProvider; //Inyectado.
	
	//Creamos este índice para llevar una cuenta de los recursos nuevos sin persistencia aún en BD. Su numeración és negativa: -1, -2 ....
	private int index;
	
	/**
	 * Marca como actualizado el recurso en la lista de recursos.
	 * @param resourceItem Identifica el recurso actualizado por el ID (el ID no cambia), y contiene los cambios.
	 */
	public void save(ResourceItem resourceItem){
		
		boolean found=false;
		int i=0;
		ResourceItem currentResourceItem;
		while ( !found && i<myResources.size())
		{
			currentResourceItem=myResources.get(i);
			if ( currentResourceItem.getId() == resourceItem.getId() )
				found=true;
			else i++;
		}
		if (found) {
			if ( myResources.get(i).isAdded() ) resourceItem.setAdded_Updated();
			else resourceItem.setUpdated();
			myResources.set(i, resourceItem);
		}
		selectedResource=resourceItem;
	}
	/**
	 * Marca como eliminado el recurso en la lista de recursos.
	 * @param resourceItem Identifica el recurso actualizado por el ID (el ID no cambia), y contiene los cambios.
	 */
	public void remove(ResourceItem resourceItem){
		
		boolean found=false;
		int i=0;
		ResourceItem currentResourceItem;
		while ( !found && i<myResources.size())
		{
			currentResourceItem=myResources.get(i);
			if ( currentResourceItem.getId() == resourceItem.getId() )
				found=true;
			else i++;
		}
		if (found) {
			
			resourceItem.setDeleted();
			myResources.set(i, resourceItem);
		}
		selectedResource=resourceItem;
	}
	
	
	
	/**
	 * Añade un nuevo recurso a la lsita de recursos
	 * @param resourceItem nuevo recurso.
	 */
	public void add(ResourceItem resourceItem){
		//Asigno índice negativo porque es un recurso pendiente de persistencia en BD.
		resourceItem.setId(index);
		index--;
		resourceItem.setAdded();
		//Esta lista no soporta el método add. 
		myResources.add(resourceItem);
		selectedResource=resourceItem;
	}
	
	/**
	 * Devuelve true si la lista de recursos contiene el recurso pasado como
	 * parámetro. La validación se hace por coincidencia de los campos id, name y type.
	 * @param resourceItem Contiene el criterio de búsqueda
	 * @return Si (true) o no (false).
	 */
	public boolean contains (ResourceItem resourceItem) {
		return this.myResources.contains(resourceItem);
	}
	
	
	
	/**
	 * Devuelve true si la lista de recursos contiene el recurso con nombre y tipo pasado como
	 * parámetro. La validación se hace por coincidencia de los campos name y type, para un id distinto a si mismo.
	 * @param id: Id de recurso.
	 * @param name: nombre de recurso.
	 * @param type: tipo de recurso.
	 * @return Si (true) o no (false).
	 */
	public boolean contains (int id, String name, String type) {
		ResourceItem resourceItem=new ResourceItem(id, name, type, null);
		return this.myResources.contains(resourceItem);
	}
	
	
	
	public List<ResourceItem> getMyResources() {
		return myResources;
	}
	public ResourceItem getSelectedResource() {
		return selectedResource;
	}
	public void setSelectedResource(ResourceItem selectedResource) {
		this.selectedResource = selectedResource;
	}
	public CommunityItem getCommunityItem() {
		return communityItem;
	}
	/**
	 * Actualizo la lista de recursos cada vez que cambia la comunidad.
	 * Si la comunidad es null, entiende que se está creando una nueva comunidad.
	 * @param community
	 */
	public void setCommunityItem(CommunityItem community) {
		communityItem = community;
		if ( community != null)
			//Creo una lista enlazada para que permita add y remove a partir de la lista de recursos de la comunidad indicada en el parámetro.
			myResources= new LinkedList<ResourceItem>(Arrays.asList(resourceDataProvider.getResources(communityItem)));
		else
			//Creo una lista enlazada para que permita add y remove vacía ya que es una nueva comunidad.
			myResources= new LinkedList<ResourceItem>();
		//Reinicio el índice para recursos nuevos sin persistir.
		index=-1;
	}
	public ResourceDataProvider getResourceDataProvider() {
		return resourceDataProvider;
	}
	public void setResourceDataProvider(ResourceDataProvider resourceDataProvider) {
		this.resourceDataProvider = resourceDataProvider;
	}
}
