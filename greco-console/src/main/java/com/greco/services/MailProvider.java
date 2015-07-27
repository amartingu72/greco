package com.greco.services;


import javax.mail.MessagingException;

import com.greco.services.helpers.UserItem;


public interface MailProvider {
	/**
	 * Env�a un correo de cambio de contrase�a. Indica aquellas comunidades donde tiene efecto.
	 * @param userItem Item de usuario cuya contrase�a cambia.
	 * @param newPwd Nueva contrase�a.
	 * @throws MessagingException
	 */
	public abstract void sendNewPassword(UserItem userItem, String newPwd) throws MessagingException;
}
