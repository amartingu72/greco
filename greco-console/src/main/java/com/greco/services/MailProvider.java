package com.greco.services;


import javax.mail.MessagingException;

import com.greco.services.helpers.CommunityItem;
import com.greco.services.helpers.MemberItem;
import com.greco.services.helpers.UserItem;


public interface MailProvider {
	/**
	 * Envía un correo de cambio de contraseña. Indica aquellas comunidades donde tiene efecto.
	 * @param userItem Item de usuario cuya contraseña cambia.
	 * @param newPwd Nueva contraseña.
	 * @throws MessagingException
	 */
	public abstract void sendNewPassword(UserItem userItem, String newPwd) throws MessagingException;
	
	/**
	 * Envía un correo de baja de una comunidad.
	 * @param userItem Usario que se da de baja
	 * @param communityItem Comunidad de la que es dado de baja
	 * @throws MessagingException
	 */
	public abstract void sendUnsubscribed(UserItem userItem, CommunityItem communityItem) throws MessagingException;
	
	/**
	 * Envía un correo de suscripción rechazada indicando, si el administrador lo ha indicado, el motivo de rechazo.
	 * @param memberItem Subscripción rechazada.
	 * @param communityItem Comunidad de la que se rechaza.
	 * @param rejectionMsg Mensaje de rechazo indicado por el administrador.
	 * @throws MessagingException
	 */
	public abstract void sendSubscriptionRejected(MemberItem memberItem, CommunityItem communityItem, String rejectionMsg) throws MessagingException;
}
