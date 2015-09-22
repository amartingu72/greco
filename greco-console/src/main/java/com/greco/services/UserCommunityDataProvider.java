package com.greco.services;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import com.greco.services.except.user.NoCommunityAdminException;
import com.greco.services.except.user.NoMemberException;
import com.greco.services.helpers.CommunityItem;
import com.greco.services.helpers.MemberItem;
import com.greco.services.helpers.UserItem;


public interface UserCommunityDataProvider{
	
	
	/**
	 * Recupera un grupo de miembros pertenecientes a una communidad.
	 * @param communityItem Comunidad
	 * @param criteria Criterios para filtrar qué miembros de la comunidad se recuperan.
	 * @param start Inicio (se trata de una recuperación "lazy" o por tandas)
	 * @param max Número máximo de miembros a recuperar (se trata de una recuperación "lazy" o por tandas)
	 * @param sortField Campo de ordenación (si es null, no se ordena).
	 * @param sortOrder Tipo de ordenación: ASC, DESC 
	 * @return Lista de suscripciones.
	 */
	public abstract List<MemberItem> findRangeOrder(CommunityItem communityItem, Map<String,Object> criteria,int start, int max, String sortField, SortOrder sortOrder);
	
	/**
	 * Recupera las suscriciones pendientes de ser aprovadas de una comunidad.
	 * @param communityItem Comunidad.
	 * @return Lista de suscripciones
	 */
	public abstract List<MemberItem> findPendingMemberships(CommunityItem communityItem);
	
	/**
	 * Devulve la lista de comunidades en la que el usuario pasado como parámetro está suscrito (o pendiente de aprobación de su suscripción), o es administrador.
	 * @param userItem
	 * @return Lista de suscripciones.
	 */
	public abstract List<MemberItem> getMyCommunities(UserItem userItem);
	
	/**
	 * Devulve la lista de comunidades administradas por el usuario pasado como parámetro.
	 * @param userItem
	 * @return Lista de comunidades.
	 */
	public abstract List<CommunityItem> getMyAdministeredCommunities(UserItem userItem);
	
	
	/**
	 * Cambia el estado de un miembro: pendiente a miembro o viceversa.
	 * @param memberItem Item que define al miembro y su estado.
	 * @throws NoMemberException Se intenta guardar el estado de un miembro no suscrito.
	 */
	public abstract void saveStatus(MemberItem memberItem) throws NoMemberException;
	
	/**
	 * Cambia el perfil de un miembro: administrador a usuario o viceversa.
	 * @param memberItem Item que define al miembro y su perfil.
	 * @throws NoCommunityAdminException Se ha intentado eliminar al único usuario administrador.
	 * @throws NoMemberException Se intenta guardar el estado de un miembro no suscrito.
	 */
	public abstract void saveMemberProfile(MemberItem memberItem)  throws NoCommunityAdminException, NoMemberException;
	
	/**
	 * Da de baja de la comunidad al miembro pasado como parámetro.
	 * @param memberItem Item de miembro.
	 * @throws NoCommunityAdminException Se ha intentado eliminar al único usuario administrador.
	 * @throws NoMemberException Se intenta guardar el estado de un miembro no suscrito.
	 */
	public abstract void removeMember(MemberItem memberItem) throws NoCommunityAdminException, NoMemberException;
	
	/**
	 * Devuelve el número de administradores.
	 * @param communityItem Comunidad.
	 * @return Número de administradores de una comunidad.
	 */
	public abstract int getAdmins(CommunityItem communityItem);
	
	/**
	 * Devuelve el número de miembros suscritos a una comunidad (¡Ojo! No incluye a los pendientes de suscripción).
	 * @param communityItem Comunidad.
	 * @return Número de miembros de una comunidad.
	 */
	public abstract int getMembers(CommunityItem communityItem);
	
	/**
	 * Obtiene el item de miembro de un usuario en una comunidad concreta.
	 * @param userItem Item de usuario.
	 * @param communityId Id de comunidad.
	 * @return Item de miembro.
	 */
	public abstract MemberItem find(UserItem userItem, int communityId);
	
	
	/**
	 * Recurpera el miembro por su clave en user-communities.
	 * @param memberItemId Id de miembro (no de usuario)
	 * @return Miembro.
	 */
	public MemberItem find(int memberItemId);
	
	
	
}
