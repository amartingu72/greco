package com.greco.repositories;

import com.greco.entities.Community;
import com.greco.entities.Profile;
import com.greco.entities.User;


public interface CommunityDAO {
	/**
	 * Guarda la comunidad pasada como parámetro.
	 * @param newCommunity
	 */
	public void saveCommunity(Community newCommunity);
	/**
	 * Crea una nueva comunidad.
	 * @param newCommunity
	 */
	public void addCommunity(Community newCommunity);
	/**
	 * Devuevle la comunidad asociada al id pasado como parámetro.
	 * @param communityId Id de comunidad.
	 * @return Comunidad.
	 */
	public Community loadSelectedCommunity(Integer communityId);
	/**
	 * Caraf la comunidad con el nombre y el código postar indicado.
	 * @param communityName Nombre de la comunidad.
	 * @param zipcode Código postal
	 * @return Comunidad.
	 */
	public Community loadSelectedCommunity(String communityName, String zipcode, int countryId);
	
	/**
	 * Añade un usuario a una comunidad con el perfil inficado.
	 * @param comunity Comunidad en al que se añade.
	 * @param user Usuario a añadir
	 * @param profile Perfil
	 */
	public void addUser(Community comunity, User user, Profile profile);
}
