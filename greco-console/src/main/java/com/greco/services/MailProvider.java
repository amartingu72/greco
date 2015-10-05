package com.greco.services;


import javax.mail.MessagingException;

import com.greco.services.helpers.CommunityItem;
import com.greco.services.helpers.UserItem;


public interface MailProvider {
	/**
	 * Env�a un correo de cambio de contrase�a. Indica aquellas comunidades donde tiene efecto.
	 * @param userItem Item de usuario cuya contrase�a cambia.
	 * @param newPwd Nueva contrase�a.
	 * @throws MessagingException
	 */
	public abstract void sendNewPassword(UserItem userItem, String newPwd) throws MessagingException;
	
	/**
	 * Env�a un correo de baja de una comunidad.
	 * @param userItem Usario que se da de baja
	 * @param communityItem Comunidad de la que es dado de baja
	 * @throws MessagingException
	 */
	public abstract void sendUnsubscribed(UserItem userItem, CommunityItem communityItem) throws MessagingException;
}
