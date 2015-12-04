package com.greco.services;

import java.util.List;






import com.greco.services.helpers.CommunityItem;
import com.greco.services.helpers.ResourceItem;
import com.greco.services.helpers.ResourceItemGroup;
import com.greco.services.helpers.ResourceTypeItem;
import com.greco.services.helpers.UserItem;


public interface CommunityDataProvider {

	/**
	 * Crea una nueva comunidad.
	 * @param userItem Usuario que crea la comunidad. Quedará asociado como ROLE_ADMIN.
	 * @param communityItem Item de comunidad.
	 * @param resourceItemList Lista de recursos asociados.
	 * @return Identificador con que se ha creado la comunidad.
	 */
	public abstract int add(UserItem userItem, CommunityItem communityItem, List<ResourceItem> resourceItemList);
	
	/**
	 * Actualiza en BD la comunidad indicada y sus recursos asociados.
	 * @param communityItem
	 * @param resourceItemList
	 * @return Lista de recursos cuya actualización no ha podido realizarse.
	 */
	public abstract List<ResourceItem> save(CommunityItem communityItem, List<ResourceItem> resourceItemList);
	
	/**
	 * Devuelve el item de comunidad asociado al id de usuario. 
	 * @param id Identificador de comunidad.
	 * @return Item de comunidad.
	 */
	public abstract CommunityItem getCommunityById(int id);
	
	/**
	 * Devuelve los tipos de recursos que tiene la comunidad pasada como parámetro.
	 * @param communityItem Comunidad.
	 * @return Array de tipos de recurso.
	 */
	public abstract ResourceTypeItem[] getResourceTypes(CommunityItem communityItem);
	
	/**
	 * Devuelve una lista con los recursos de comunidad agrupados por tipo.
	 * @param communityItem Item de la comunidad de la que se desea recuperar los recursos.
	 * @return Lista de recursos ordenada por tipos.
	 */
	public abstract ResourceItemGroup[] getResources(CommunityItem communityItem);
	
	/**
	 * Se invoca en caso de alta o actualizaciones de comunidades para saber si los valores introducidos están ducplicados.
	 * @param myId Id de la comunidad de referencia. Corresponde con el id de la comunidad editada. Si se trata de un alta, es 0.
	 * @param name Nombre de la comunidad.
	 * @param zipcode Código postal.
	 * @param countryId Id de país.
	 * @return True (duplicada), false (única).
	 */
	public abstract boolean isDuplicated(int myId, String name, String zipcode, int countryId);
	
}
