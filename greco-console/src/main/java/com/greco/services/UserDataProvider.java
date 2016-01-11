package com.greco.services;

import com.greco.beans.UserSBean;
import com.greco.services.except.user.NoMemberException;
import com.greco.services.except.user.PendingException;
import com.greco.services.except.user.UnavailableCommunity;
import com.greco.services.except.user.UnknownCommunityException;
import com.greco.services.helpers.CommunityItem;
import com.greco.services.helpers.UserItem;

public interface UserDataProvider {
	
	/**
	 * Genera una nueva contraseña de 8 caracteres y se la asigna al usuario indicado.
	 * @param userId Identificador de usuario.
	 * @return Contraseña generada.
	 */
	public abstract String changePassword(int userId);
	
	/**
	 * Indica si existen usuarios con el nickname pasado como parámetro.
	 * @param nickname nickname
	 * @return Si(true) o no (false).
	 */
	public abstract boolean isDuplicated(String nickname);
	
	/**
	 * Crea un nuevo usuario.
	 * @param UserIte
	 * @return ID del usuario creado
	 */
	public abstract int addUser(UserItem userItem);
	
	/**
	 * Suscribe un usuario a una comunidad..
	 * @param userItem Item de usuario
	 * @param communityId Id de comunidad.
	 * @param application Texto en base al cual el administrador decidirá si acepta la suscripción.
	 */
	public abstract void subscribe(int userId, int communityId, String application);
	
	/**
	 * Guarda el usuario pasado como parámetro.
	 * @param userItem Item de usuario.
	 * @para pwdUpdated Indicador de si la contraseña fue modificada y, por tanto, hay que actualizarla.
	 */
	public abstract void save(UserItem userItem, boolean pwdUpdated);
	
	/**
	 * Completa los datos de usuario a partir del correo.
	 * @param email
	 * @return El bean o null si el usuario no exisite.
	 */
	public abstract UserSBean loadAdminCredentials(String email);
	
	/**
	 * Carga las credenciales del usuario
	 * @param email Correo del usuario.
	 * @param communityId Identificador de comunidad. 
	 * @return Usuario correspondiente a las credenciales o null si las credenciales no son buenas. 
	 * @throws NoMemberException El usuario no es miembro de la comunidad señalada. 
	 * @throws UnknownCommunityException El id de comunidad indicado no existe.
	 */
	public abstract UserSBean loadUserCredentials(String email, int communityId)
			throws NoMemberException, UnknownCommunityException, UnavailableCommunity, PendingException;
	/**
	 * 
	 * @param user Identifica al usuario del que se quieren recuperar las comunidades.
	 * @return Lista de Comunidades.
	 */
	public abstract CommunityItem[] getMyComunities(UserSBean userBean); 
	
	/**
	 * Devuelve el ítem asocidado al email.
	 * @param email
	 * @return Item de usuario.
	 */
	public abstract UserItem getUserItem(String email);
	
	/**
	 * Devuelve el ítem asocidado al nickname o alias.
	 * @param nickname
	 * @return Item de usuario.
	 */
	public abstract UserItem getUserItemByNick(String nickname);
	
	/**
	 * Activación de usuario.
	 * @param userItem Item de usuario a activar.
	 * @param code Código de activación 
	 * @return True (usuario activado); false (código de activación no válido)
	 */
	public abstract boolean activate(UserItem userItem, String code);
	
}
