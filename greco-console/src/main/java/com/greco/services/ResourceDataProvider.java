package com.greco.services;


import java.util.Date;




import com.greco.engine.DailySchedule;
import com.greco.services.helpers.CommunityItem;
import com.greco.services.helpers.ResourceItem;
import com.greco.services.helpers.ResourceTypeItem;


public interface ResourceDataProvider {
	
	/**
	 * Indica si un nombre de recurso está duplicado para una comunidad y tipo.
	 * @param name Nombre del recurso
	 * @return Devuelve true si el nombre de recurso existe para la comunidad y tipo indicado.
	 */
	public abstract boolean isDuplicated(String name, 
				ResourceTypeItem resourceTypeItem, 
				CommunityItem communityItem);
	
	/**
	 * Indica si un nombre de recurso está duplicado para una comunidad y tipo. Se utiliza en alta y edición de recursos.
	 * @param rsrcId. Si es modo edición, es el id de recurso editado. Si es nuevo, será 0.
	 * @param rsrcName Nombre del recurso.
	 * @param rsrcTypeId Identificador del tipo de recurso.
	 * @param communityId Identificador de la comunidad.
	 * @return Devuelve true si el nombre de recurso existe para la comunidad y tipo indicado.
	 */
	public abstract boolean isDuplicated(int rsrcId, String rsrcName, int rsrcTypeId, int communityId);
	
	/**
	 * Devuelve los recursos asociados a la comunidad indicada en el parámetros.
	 * @param comm Comunidad.
	 * @return Lista de recursos.
	 */
	public abstract ResourceItem[] getResources(CommunityItem comm);
	
	/**
	 * Devuelve los recursos asociados a la comunidad indicada en el parámetros.
	 * @param comm Comunidad.
	 * @param rsrcTypeItem Tipo de recurso (pádel, tenis...)
	 * @return Lista de recursos.
	 */
	public abstract ResourceItem[] getResources(CommunityItem comm, ResourceTypeItem rsrcTypeItem);
	
	/**
	 * Devuelve una lista con la ocupación de cada recurso del tipo indicado de una comunidad.
	 * @param comm Comunidad
	 * @param rsrcTypeItem Tipo de recurso.
	 * @param dat Fecha (día), para el que se requiere conocer la ocupación.
	 * @param userId Identificador del usuario logado que solicita la reserva. Sirve para que el servicio pueda
	 * diferenciar entre recursos bloqueados por el usuario y los bloqueados por otros.
	 * @return Lista recursos con su ocupación;
	 */
	public abstract DailySchedule[] getOccupancy(CommunityItem comm, ResourceTypeItem rsrcTypeItem, Date date, int userId);
}
