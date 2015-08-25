package com.greco.services;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import com.greco.services.except.user.NoCommunityAdminException;
import com.greco.services.helpers.CommunityItem;
import com.greco.services.helpers.MemberItem;


public interface UserCommunityDataProvider{
	
	
	/**
	 * Recupera un grupo de miembros pertenecientes a una communidad.
	 * @param communityItem Comunidad
	 * @param criteria Criterios para filtrar qu� miembros de la comunidad se recuperan.
	 * @param start Inicio (se trata de una recuperaci�n "lazy" o por tandas)
	 * @param max N�mero m�ximo de miembros a recuperar (se trata de una recuperaci�n "lazy" o por tandas)
	 * @param sortField Campo de ordenaci�n (si es null, no se ordena).
	 * @param sortOrder Tipo de ordenaci�n: ASC, DESC 
	 * @return
	 */
	public abstract List<MemberItem> findRangeOrder(CommunityItem communityItem, Map<String,Object> criteria,int start, int max, String sortField, SortOrder sortOrder);
	
	/**
	 * Recupera las suscriciones pendientes de ser aprovadas de una comunidad.
	 * @param communityItem Comunidad.
	 * @return Lista de suscripciones
	 */
	public abstract List<MemberItem> findPendingMemberships(CommunityItem communityItem);
	
	
	/**
	 * Cambia el estado de un miembro: pendiente a miembro o viceversa.
	 * @param memberItem Item que define al miembro y su estado.
	 */
	public abstract void saveStatus(MemberItem memberItem);
	
	/**
	 * Cambia el perfil de un miembro: administrador a usuario o viceversa.
	 * @param memberItem Item que define al miembro y su perfil.
	 * @throws NoCommunityAdminException Se ha intentado eliminar al �nico usuario administrador.
	 */
	public abstract void saveMemberProfile(MemberItem memberItem)  throws NoCommunityAdminException;
	
	/**
	 * Da de baja de la comunidad al miembro pasado como par�metro.
	 * @param memberItem Item de miembro.
	 * @throws NoCommunityAdminException Se ha intentado eliminar al �nico usuario administrador.
	 */
	public abstract void removeMember(MemberItem memberItem) throws NoCommunityAdminException;
	
	/**
	 * Devuelve el n�mero de administradores.
	 * @param communityItem Comunidad.
	 * @return N�mero de administradores de una comunidad.
	 */
	public abstract int getAdmins(CommunityItem communityItem);
	
	/**
	 * Devuelve el n�mero de miembros suscritos a una comunidad (�Ojo! No incluye a los pendientes de suscripci�n).
	 * @param communityItem Comunidad.
	 * @return N�mero de miembros de una comunidad.
	 */
	public abstract int getMembers(CommunityItem communityItem);
}
