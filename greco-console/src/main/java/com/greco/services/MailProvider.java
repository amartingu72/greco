package com.greco.services;


import javax.mail.MessagingException;

import com.greco.services.helpers.UserItem;


public interface MailProvider {
	/**
	 * Envía un correo de cambio de contraseña. Indica aquellas comunidades donde tiene efecto.
	 * @param userItem Item de usuario cuya contraseña cambia.
	 * @param newPwd Nueva contraseña.
	 * @throws MessagingException
	 */
	public abstract void sendNewPassword(UserItem userItem, String newPwd) throws MessagingException;
}
