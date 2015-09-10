package com.greco.repositories;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import com.greco.entities.Profile;
import com.greco.entities.UsersCommunity;
import com.greco.services.except.user.NoCommunityAdminException;
import com.greco.services.except.user.NoMemberException;

public interface UserCommunitiesDAO {
	
	/**
	 * Estados posibles de una suscripci�n
	 */
	
	public static int PENDING_STATUS=1; //Pendiente de aprobaci�n. El usuario ha pedido suscribirse pero a�n no est� aprobado.
	public static int SUBSCRIBED_STATUS=0;  //Suscrito. Puede realizar reservas.
	
	/**
	 * Campos por los que se puede establecer criterio ("like"), para hacer una consulta o para establecer un criterio de ordenaci�n..
	 */
	public static final String TEXT="text"; //Alias o application
	public static final String ALIAS="nickname";
	public static final String PROFILE="memberProfile";
	public static final String STATUS="status";
	public static final String SUBSCRIPTION_FROM_DATE="fromDate";
	public static final String SUBSCRIPTION_TO_DATE="toDate";
	public static final String SUBSCRIPTION="registerdate";
	public static final String APPLICATION="application";
	
		
	/**
	 * Devuelve la entidad correspondiente al Id de miembro (no de usuario), pasado como par�metro.
	 * @param ucId Id de miembro (id en usercommunities)
	 * @return Entidad miembro
	 */
	public UsersCommunity load(int userCommunity_id);
	
	/**
	 * Recupera un subconjunto de miembros de una comunidad dada de base de datos seg�n un criterio.
	 * @param criteria Pares columna [(uno de los valores de arriba), like value]. Null si no se establece. 
	 * @param start Inicio
	 * @param max Fin
	 * @param sortField Campo de ordenaci�n: debe coincidir con uno de lo valores indicados arriba como constantes. Null si no se establece.
	 * @param sortOrder ASC, DESC, UNSORTED
	 * @return Lista de entidades
	 */
	public List<UsersCommunity> findRangeOrder(int communityID, Map<String,Object> criteria,int start, int max, String sortField, SortOrder sortOrder);
	
	
	/**
	 * Recupera las suscripciones pendintes de aprovaci�n de una comunidad.
	 * @param communityID
	 * @return 
	 */
	public List<UsersCommunity> findPendingMemberships(int communityID);

	
	/**
	 * Acualiza el estado en la relaci�n UserCommunities
	 * @param userCommunity_id Id de la tabla UserCommunities
	 * @param status Estado
	 * @throws El miembro indicado no es miembro de la comunidad.
	 */
	public void saveStatus(int userCommunity_id, int status_id) throws NoMemberException;
	
	/**
	 * Acualiza el perfil en la relaci�n UserCommunities
	 * @param userCommunity_id
	 * @param profile_id
	 * @throws El miembro indicado no es miembro de la comunidad.
	 */
	public void saveProfile(int userCommunity_id, Profile profile) throws NoCommunityAdminException, NoMemberException;
	
	/**
	 * Borra la fila indicada.
	 * @param ucId Id
	 * @throws El miembro indicado no es miembro de la comunidad.
	 */
	public void remove(int ucId) throws NoCommunityAdminException, NoMemberException;
	
	//Crea un nuevo miembro.
	public void add(UsersCommunity uc);
	
	/**
	 * Devuelve el estado de una suscripci�n
	 * @param userId Identificado del usuario que realiza la suscripci�n.
	 * @param communityId Identificador de la comunidad por cuya suscripci�n pregunta.
	 * @return Estado de la suscriptici�n (ver contantes m�s arriba): PENDING, SUBSCRIBED.
	 * @throws NoMemberException El usuario no es miembro ni ha solicitado suscribirse a esta comunidad.
	 */
	public int getSubscriptionStatus(int userId, int communityId)  throws NoMemberException ;
	
	/**
	 * Indica el n�mero usuarios administradores para una comunidad dada.
	 * @param communityId Identificador de comunidad.
	 * @return N�mero de administradores de la comunidad.
	 */
	public int adminCount(int communityId);
	
	/**
	 * Devuelve el n�mero de miembros suscritos a una comunidad: status=SUBSCRIBED_STATUS
	 * @param communityID
	 * @return N�mero de miembros.
	 */
	public int membersCount(int communityId);
	
}
