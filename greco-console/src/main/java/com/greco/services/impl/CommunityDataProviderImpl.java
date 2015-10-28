package com.greco.services.impl;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;






import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greco.entities.Community;
import com.greco.entities.Country;
import com.greco.entities.Resourcetype;
import com.greco.repositories.CommunityDAO;
import com.greco.repositories.CountryDAO;
import com.greco.repositories.ProfileDAO;
import com.greco.repositories.ReservationDAO;
import com.greco.repositories.ResourceDAO;
import com.greco.repositories.ResourceTypeDAO;
import com.greco.repositories.TimeUnitDAO;

import com.greco.repositories.UserDAO;
import com.greco.services.CommunityDataProvider;
import com.greco.services.ResourceDataProvider;
import com.greco.services.helpers.CommunityItem;
import com.greco.services.helpers.ResourceItem;
import com.greco.services.helpers.ResourceItemGroup;
import com.greco.services.helpers.ResourceTypeItem;
import com.greco.services.helpers.UserItem;

@Service("communityDataProvider")
public class CommunityDataProviderImpl implements CommunityDataProvider {
	@Resource(name="CommunityRepository")
	private CommunityDAO communityDAO;

	@Resource(name="resourceDataProvider")
	private ResourceDataProvider resourceDataProvider;
	
	@Resource(name="ResourcesRepository")
	private ResourceDAO resourceDAO;
	
	@Resource(name="ResourceTypeRepository")
	private ResourceTypeDAO resourceTypeDAO;
	
	@Resource(name="TimeUnitRepository")
	private TimeUnitDAO timeUnitDAO;
	
	@Resource(name="CountryRepository")
	private CountryDAO countryDAO;
	
	@Resource(name="UsersRepository")
	private UserDAO userDAO;
	
	@Resource(name="ProfilesRepository")
	private ProfileDAO profileDAO;
	
	@Resource(name="ReservationRepository")
	private ReservationDAO reservationDAO;
	
	
	/* (non-Javadoc)
	 * @see com.greco.services.CommunityDataProvider#getCommunityById(int)
	 */
	public CommunityItem getCommunityById(int id){
		CommunityItem communityItem=null;
		Community community=communityDAO.loadSelectedCommunity(id);
		if (community!=null){
			communityItem=new CommunityItem(community.getId(),(community.getAvailable()!=0), community.getName(),
				community.getZipcode(),null,null,community.getNotes(), community.getCountry().getName());
			communityItem.setMembercheck(community.getMembercheck()!=0);
		}
		return communityItem;
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.greco.services.CommunityDataProvider#save(com.greco.services.helpers.CommunityItem, java.util.List)
	 */
	@Override
	@Transactional
	public List<ResourceItem> save(CommunityItem communityItem, List<ResourceItem> resourceItemList){
		List<ResourceItem> failures=new ArrayList<ResourceItem>();
		//Guardamos los cambios en la comunidad
		Community community=communityDAO.loadSelectedCommunity(communityItem.getId());
		//Guardamos los cambios modificables desde el formulario.
		community.setName(communityItem.getName());
		community.setZipcode(communityItem.getZipcode());
		community.setCountry(this.countryDAO.loadSelected(communityItem.getCountry()));
		community.setAvailable((byte)(communityItem.isAvailable()?1:0));
		community.setNotes(communityItem.getMyData());
		community.setMembercheck((byte)(communityItem.isMembercheck()?1:0));
		communityDAO.saveCommunity(community);
		
		
		//Guardarmos los cambios en los recursos
		Iterator<ResourceItem> it=resourceItemList.iterator();
		ResourceItem resourceItem;
		com.greco.entities.Resource resource=null;
		
		
		while ( it.hasNext() ) {
			resourceItem=it.next();
			
			if ( resourceItem.isAdded() || resourceItem.isUpdated() ){
				//Añadimos un nuevo recurso
				resource=new com.greco.entities.Resource();
				resource.setAvailableFromTime(resourceItem.getAvailableFromTime());
				resource.setAvailableToTime(resourceItem.getAvailableToTime());
				resource.setBeforehand(resourceItem.getBeforehand());
				resource.setCommunity(community);
				resource.setDescription(resourceItem.getDescription());
				resource.setMaxTime(resourceItem.getMaxtime());
				resource.setMinTime(resourceItem.getMintime());
				resource.setName(resourceItem.getName());
				resource.setResourcetype(resourceTypeDAO.loadSelected(resourceItem.getType()));
				resource.setTimeunit1(timeUnitDAO.loadSelected(Integer.parseInt(resourceItem.getTimeunit())));
				resource.setTimeunit2(timeUnitDAO.loadSelected(Integer.parseInt(resourceItem.getBeforehandTU())));
				resource.setWeeklyAvailability(resourceItem.getWeeklyAvailabilityString());
			} 
			if ( resourceItem.isUpdated() ) {
				//Modificamos el recurso
				resource.setId(resourceItem.getId());
				resourceDAO.saveResource(resource);
				//Quitamos la marca de pendiente de actualizar.
				resourceItem.clearStatus();
				
			}
			else if ( resourceItem.isAdded()) {
				//Añadimos recurso.
				int id=resourceDAO.addResource(resource);
				resourceItem.setId(id);
				//Quitamos la marca de pendiente de actualizar.
				resourceItem.clearStatus();
				
			}
			else if ( resourceItem.isDeleted() ) {
				//Si es un recurso sin id es que lo ha creado y borrado ahora, a antes de dar a guardar y, por tanto, no tiene persistencia en BD
				int i=resourceItem.getId();
				if ( i <= 0) 
					it.remove();
				else { //El item tenía persistencia en base de datos.
					//Comprobamos que no haya ninguna reserva realizada.
					if (!reservationDAO.hasReservations(resourceItem.getId()) ){
						//Borramos de la base de datos.
						resourceDAO.removeResource(resourceItem.getId());
						//y de la lista.
						it.remove();
					}
					else
						failures.add(resourceItem);  //Añadimos a la lista de fallidos.
				}
			}			
		}
		return failures;
	}
	
	/* (non-Javadoc)
	 * @see com.greco.services.CommunityDataProvider#add(com.greco.services.helpers.CommunityItem, java.util.List)
	 */
	@Override
	@Transactional
	public int add(UserItem userItem, CommunityItem communityItem, List<ResourceItem> resourceItemList){
		
		//Guardamos los cambios en la comunidad
		Community community=new Community();
		//Guardamos solo los cambios modificables desde es formulario
		Country country=countryDAO.loadSelected(communityItem.getCountry());
		community.setName(communityItem.getName());
		community.setCountry(country);
		community.setZipcode(communityItem.getZipcode());
		community.setAvailable((byte)(communityItem.isAvailable()?1:0));
		community.setNotes(communityItem.getMyData());
		community.setMembercheck((byte)(communityItem.isMembercheck()?1:0));

		communityDAO.addCommunity(community);
		
		
		//Vinculamos la comunidad a un usuario.
		Community comAdded=communityDAO.loadSelectedCommunity(communityItem.getName(),
				communityItem.getZipcode(), country.getId()	);

		communityDAO.addUser(comAdded, userDAO.loadSelectedUser(userItem.getId()), profileDAO.loadSelected(ProfileDAO.ROLE_ADMIN_ID));
			

		//Guardarmos los recursos
		if (resourceItemList!=null)
		{
			Iterator<ResourceItem> it=resourceItemList.iterator();
			ResourceItem resourceItem;
			com.greco.entities.Resource resource=null;
			while ( it.hasNext() ) {
				resourceItem=it.next();
	
				if ( resourceItem.isAdded() || resourceItem.isUpdated() ){
	
					//Añadimos un nuevo recurso
					resource=new com.greco.entities.Resource();
					resource.setAvailableFromTime(resourceItem.getAvailableFromTime());
					resource.setAvailableToTime(resourceItem.getAvailableToTime());
					resource.setBeforehand(resourceItem.getBeforehand());
					resource.setCommunity(comAdded);
					resource.setDescription(resourceItem.getDescription());
					resource.setMaxTime(resourceItem.getMaxtime());
					resource.setMinTime(resourceItem.getMintime());
					resource.setName(resourceItem.getName());
					resource.setResourcetype(resourceTypeDAO.loadSelected(resourceItem.getType()));
					resource.setTimeunit1(timeUnitDAO.loadSelected(Integer.parseInt(resourceItem.getTimeunit())));
					resource.setTimeunit2(timeUnitDAO.loadSelected(Integer.parseInt(resourceItem.getBeforehandTU())));
					resource.setWeeklyAvailability(resourceItem.getWeeklyAvailabilityString());
				} 
				if ( resourceItem.isUpdated() ) {
					//Modificamos el recurso
					resource.setId(resourceItem.getId());
					resourceDAO.saveResource(resource);;
				}
				else if ( resourceItem.isAdded()) {
					//Añadimos recurso.
					resourceDAO.addResource(resource);
				}

			}
		}
		return comAdded.getId();
	}


	@Override
	public ResourceTypeItem[] getResourceTypes(CommunityItem communityItem) {
		Community community=this.communityDAO.loadSelectedCommunity(communityItem.getId());
		List<com.greco.entities.Resource> rsrcList=community.getResources();
		Iterator<com.greco.entities.Resource> it=rsrcList.iterator();
		List<ResourceTypeItem> rsrcTypeList=new ArrayList<ResourceTypeItem>();
		Resourcetype rsrcType;
		ResourceTypeItem rsrcTypeItem;
		while ( it.hasNext() ){
			rsrcType=it.next().getResourcetype();
			rsrcTypeItem=new ResourceTypeItem(rsrcType.getId(),
					rsrcType.getName(), rsrcType.getDescription());
			if ( !rsrcTypeList.contains(rsrcTypeItem) )  rsrcTypeList.add(rsrcTypeItem);
		}
		return rsrcTypeList.toArray(new ResourceTypeItem[rsrcTypeList.size()]);
	}



	@Override
	public ResourceItemGroup[] getResources(CommunityItem communityItem) {
		//Recupero los tipos de recurso existentes en la comunidad.
		ResourceTypeItem[] resourceTypeItems=getResourceTypes(communityItem);
		ResourceItemGroup[] resourceItemGroups=new ResourceItemGroup[resourceTypeItems.length];
		ResourceItem[] resourceItems;
		ResourceItemGroup resourceItemGroup;
		for (int i=0;i<resourceTypeItems.length;i++){
			resourceItems=resourceDataProvider.getResources(communityItem, resourceTypeItems[i]);
			resourceItemGroup=new ResourceItemGroup(resourceTypeItems[i]);
			resourceItemGroup.setResourceItems(resourceItems);
			resourceItemGroups[i]=resourceItemGroup;
		}
		return resourceItemGroups;
	}



	
	
}
