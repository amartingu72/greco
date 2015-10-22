package com.greco.services;


import java.util.List;

import javax.mail.MessagingException;

import com.greco.services.helpers.CommunityItem;
import com.greco.services.helpers.MemberItem;
import com.greco.services.helpers.ReservationItem;
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
	
	/**
	 * Envía un correo de suscripción aprobada por el administrador.
	 * @param userItem Subscripción aprobada
	 * @param communityItem Comunidad donde se aprueba.
	 * @throws MessagingException
	 */
	public abstract void sendSubscriptionApproved(UserItem userItem, CommunityItem communityItem) throws MessagingException;
	
	/**
	 * Envía un correo indicando que la suscripción ha sido puesta en espera.
	 * @param userItem Subscripció.
	 * @param communityItem Comunidad.
	 * @throws MessagingException
	 */
	public abstract void sendSubscriptionPending(UserItem userItem, CommunityItem communityItem) throws MessagingException;
	
	/**
	 * Envía un correo indicando que el miembro es, desde ahora, adminsitrador de la comunidad.
	 * @param userItem Subscripción.
	 * @param communityItem Comunidad.
	 * @throws MessagingException
	 */
	public abstract void sendYouAreAdmin(UserItem userItem, CommunityItem communityItem) throws MessagingException;
	
	/**
	 * Envía un correo indicando que ha dejado de ser administrador de la comunidad.
	 * @param userItem Subscripción.
	 * @param communityItem Comunidad.
	 * @throws MessagingException
	 */
	public abstract void sendYouAreNotAdmin(UserItem userItem, CommunityItem communityItem) throws MessagingException;
	
	/**
	 * Envía un correo de confirmación de reserva con las reservas confirmadas.
	 * @param userItem Usuario
	 * @param communityItem Comunidad
	 * @param reservations Lista de reservas
	 * @throws MessagingException
	 */
	public abstract void sendReservConfirmation(UserItem userItem, CommunityItem communityItem, List<ReservationItem> reservations) throws MessagingException;
	
	/**
	 * Envía un correo indicando que una de sus reservas han sido canceladas por un administrador de su comunidad.
	 * @param userItem Usuario
	 * @param communityItem Comunidad
	 * @param reservations Reserva
	 * @throws MessagingException
	 */
	public abstract void sendCancelReservation(UserItem userItem, CommunityItem communityItem, ReservationItem reservation) throws MessagingException;
}
