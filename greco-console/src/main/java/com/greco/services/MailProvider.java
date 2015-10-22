package com.greco.services;


import java.util.List;

import javax.mail.MessagingException;

import com.greco.services.helpers.CommunityItem;
import com.greco.services.helpers.MemberItem;
import com.greco.services.helpers.ReservationItem;
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
	
	/**
	 * Env�a un correo de suscripci�n rechazada indicando, si el administrador lo ha indicado, el motivo de rechazo.
	 * @param memberItem Subscripci�n rechazada.
	 * @param communityItem Comunidad de la que se rechaza.
	 * @param rejectionMsg Mensaje de rechazo indicado por el administrador.
	 * @throws MessagingException
	 */
	public abstract void sendSubscriptionRejected(MemberItem memberItem, CommunityItem communityItem, String rejectionMsg) throws MessagingException;
	
	/**
	 * Env�a un correo de suscripci�n aprobada por el administrador.
	 * @param userItem Subscripci�n aprobada
	 * @param communityItem Comunidad donde se aprueba.
	 * @throws MessagingException
	 */
	public abstract void sendSubscriptionApproved(UserItem userItem, CommunityItem communityItem) throws MessagingException;
	
	/**
	 * Env�a un correo indicando que la suscripci�n ha sido puesta en espera.
	 * @param userItem Subscripci�.
	 * @param communityItem Comunidad.
	 * @throws MessagingException
	 */
	public abstract void sendSubscriptionPending(UserItem userItem, CommunityItem communityItem) throws MessagingException;
	
	/**
	 * Env�a un correo indicando que el miembro es, desde ahora, adminsitrador de la comunidad.
	 * @param userItem Subscripci�n.
	 * @param communityItem Comunidad.
	 * @throws MessagingException
	 */
	public abstract void sendYouAreAdmin(UserItem userItem, CommunityItem communityItem) throws MessagingException;
	
	/**
	 * Env�a un correo indicando que ha dejado de ser administrador de la comunidad.
	 * @param userItem Subscripci�n.
	 * @param communityItem Comunidad.
	 * @throws MessagingException
	 */
	public abstract void sendYouAreNotAdmin(UserItem userItem, CommunityItem communityItem) throws MessagingException;
	
	/**
	 * Env�a un correo de confirmaci�n de reserva con las reservas confirmadas.
	 * @param userItem Usuario
	 * @param communityItem Comunidad
	 * @param reservations Lista de reservas
	 * @throws MessagingException
	 */
	public abstract void sendReservConfirmation(UserItem userItem, CommunityItem communityItem, List<ReservationItem> reservations) throws MessagingException;
	
	/**
	 * Env�a un correo indicando que una de sus reservas han sido canceladas por un administrador de su comunidad.
	 * @param userItem Usuario
	 * @param communityItem Comunidad
	 * @param reservations Reserva
	 * @throws MessagingException
	 */
	public abstract void sendCancelReservation(UserItem userItem, CommunityItem communityItem, ReservationItem reservation) throws MessagingException;
}
