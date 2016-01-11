package com.greco.repositories;

import com.greco.entities.User;


public interface UserDAO {
	
	
	/**
	 * Devuelve el usuario indicado por el 
	 * @param userId
	 * @return
	 */
	public User loadSelectedUser(Integer userId);
	/**
	 * 	Devuelve el usuario indicado por el parámetro.
	 * @param userName Nombre de usuario (nickname o alias)
	 * @return Usuario
	 */
	public User loadSelectedUser(String userName);
	/**
	 * Devuelve el usuario indicado por el parámetro.
	 * @param email email
	 * @return Usuario
	 */
	public User loadSelectedUserByMail(String email);
	/**
	 * Modifica los datos de usuario.
	 * @param newUser Usuario.
	 */
	public void saveUser(User newUser);
	/**
	 * Crea un nuevo usuario.
	 * @param newUser Usuario.
	 * @return
	 */
	public User newUser(User newUser);
	/**
	 * Activa (actcode=null) el usuario indicado.
	 * @param userId
	 */
	public void activate(int userId);
}
