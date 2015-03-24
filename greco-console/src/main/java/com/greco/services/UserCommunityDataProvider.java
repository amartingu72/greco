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
	 * @param criteria Criterios para filtrar qué miembros de la comunidad se recuperan.
	 * @param start Inicio (se trata de una recuperación "lazy" o por tandas)
	 * @param max Número máximo de miembros a recuperar (se trata de una recuperación "lazy" o por tandas)
	 * @param sortField Campo de ordenación (si es null, no se ordena).
	 * @param sortOrder Tipo de ordenación: ASC, DESC 
	 * @return
	 */
	public abstract List<MemberItem> findRangeOrder(CommunityItem communityItem, Map<String,Object> criteria,int start, int max, String sortField, SortOrder sortOrder);
	
	/**
	 * Cambia el estado de un miembro: pendiente a miembro o viceversa.
	 * @param memberItem Item que define al miembro y su estado.
	 */
	public abstract void saveStatus(MemberItem memberItem);
	
	/**
	 * Cambia el perfil de un miembro: administrador a usuario o viceversa.
	 * @param memberItem Item que define al miembro y su perfil.
	 * @throws NoCommunityAdminException Se ha intentado eliminar al único usuario administrador.
	 */
	public abstract void saveMemberProfile(MemberItem memberItem)  throws NoCommunityAdminException;
	
	/**
	 * Da de baja de la comunidad al miembro pasado como parámetro.
	 * @param memberItem Item de miembro.
	 * @throws NoCommunityAdminException Se ha intentado eliminar al único usuario administrador.
	 */
	public abstract void removeMember(MemberItem memberItem) throws NoCommunityAdminException;
	
}
