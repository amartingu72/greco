package com.greco.repositories;

import java.util.List;

import com.greco.entities.Profile;


public interface ProfileDAO {
	//ID en base de datos.
	public static final int ROLE_ADMIN_ID=1;
	public static final int ROLE_USER_ID=2;
	
	/**
	 * Devuelve una lista con todos lo perfiles en BD.
	 * @return Lista de perfiles.
	 */
	public List<Profile> loadAll();
	/**
	 * Devuelve el perfíl con ID indicado.
	 * @param profileId
	 * @return perfil.
	 */
	public Profile loadSelected(Integer profileId);
	
	/**
	 * Devulve el perfil con el nombre indicado.
	 * @param profileName Nombre del perfil.
	 * @return Perfil.
	 */
	public Profile loadSelected(String profileName);
}
