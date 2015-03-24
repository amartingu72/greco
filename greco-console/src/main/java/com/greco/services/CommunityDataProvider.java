package com.greco.services;

import java.util.List;

import com.greco.services.helpers.CommunityItem;
import com.greco.services.helpers.ResourceItem;
import com.greco.services.helpers.ResourceTypeItem;
import com.greco.services.helpers.UserItem;


public interface CommunityDataProvider {

	/**
	 * Crea una nueva comunidad.
	 * @param userItem Usuario que crea la comunidad. Quedar� asociado como ROLE_ADMIN.
	 * @param communityItem Item de comunidad.
	 * @param resourceItemList Lista de recursos asociados.
	 * @return Identificador con que se ha creado la comunidad.
	 */
	public abstract int add(UserItem userItem, CommunityItem communityItem, List<ResourceItem> resourceItemList);
	
	/**
	 * Actualiza en BD la comunidad indicada y sus recursos asociados.
	 * @param communityItem
	 * @param resourceItemList
	 */
	public abstract void save(CommunityItem communityItem, List<ResourceItem> resourceItemList);
	
	/**
	 * Devuelve el item de comunidad asociado al id de usuario. 
	 * @param id Identificador de comunidad.
	 * @return Item de comunidad.
	 */
	public abstract CommunityItem getCommunityById(int id);
	
	/**
	 * Devuelve los tipos de recursos que tiene la comunidad pasada como par�metro.
	 * @param communityItem Comunidad.
	 * @return Array de tipos de recurso.
	 */
	public abstract ResourceTypeItem[] getResourceTypes(CommunityItem communityItem);
}
