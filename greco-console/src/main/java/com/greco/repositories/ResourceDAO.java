package com.greco.repositories;



import java.util.List;

import com.greco.entities.Resource;


public interface ResourceDAO {
	
	/**
	 * Devuelve el recurso de la comunidad y tipo indicado.
	 * @param communityId Identificador de comunidad.
	 * @param resourceTypeId Identificador de tipo de recurso.
	 * @param resourceName Nombre del recurso.
	 * @return Recurso indicado o null si no existe.
	 */
	public Resource loadSelected(int communityId, int resourceTypeId, String resourceName);
	
	/**
	 * Devuelve el recurso con el identificador pasado como parámetro.
	 * @param resourceId
	 * @return
	 */
	public Resource loadSelected(Integer resourceId);
	
	/**
	 * Guarda el recurso.
	 * @param rsrc
	 */
	public void saveResource(Resource rsrc);
	
	/**
	 * Crea un nuevo recurso en BD.
	 * @param rsrc Recurso
	 * @return	ID del nuevo recurso
	 */
	public int addResource(Resource rsrc);
	
	
	/**
	 * Recupera los recursos de la comunidad pasada como parámero.
	 * @param communityId Identificador de comunidad.
	 * @return 
	 */
	public List<Resource> loadResources(int communityId);
	
	/**
	 * Recupera los recursos de un tipo concreto de una comunidad.
	 * @param communityId Identificador de comunidad.
	 * @param resourceTypeId Identificador de tipo de recurso.
	 * @return 
	 */
	public List<Resource> loadResources(int communityId, int resourceTypeId);
}
