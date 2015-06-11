package com.greco.services.impl;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.primefaces.component.resources.ResourcesRenderer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greco.entities.Community;
import com.greco.entities.Country;
import com.greco.entities.Resourcetype;
import com.greco.repositories.CommunityDAO;
import com.greco.repositories.CountryDAO;
import com.greco.repositories.ProfileDAO;
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
	
	/* (non-Javadoc)
	 * @see com.greco.services.CommunityDataProvider#getCommunityById(int)
	 */
	public CommunityItem getCommunityById(int id){
		CommunityItem communityItem=null;
		Community community=communityDAO.loadSelectedCommunity(id);
		if (community!=null)
			communityItem=new CommunityItem(community.getId(),(community.getAvailable()!=0), community.getName(),
				community.getZipcode(),null,null,community.getNotes(), community.getCountry().getName());
		return communityItem;
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.greco.services.CommunityDataProvider#save(com.greco.services.helpers.CommunityItem, java.util.List)
	 */
	@Override
	@Transactional
	public void save(CommunityItem communityItem, List<ResourceItem> resourceItemList){
		//Guardamos los cambios en la comunidad
		Community community=communityDAO.loadSelectedCommunity(communityItem.getId());
		//Guardamos los cambios modificables desde el formulario.
		community.setName(communityItem.getName());
		community.setZipcode(communityItem.getZipcode());
		community.setCountry(this.countryDAO.loadSelected(communityItem.getCountry()));
		community.setAvailable((byte)(communityItem.isAvailable()?1:0));
		community.setNotes(communityItem.getMyData());
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
				resource.setTimeunit1(timeUnitDAO.loadSelected(resourceItem.getTimeunit()));
				resource.setTimeunit2(timeUnitDAO.loadSelected(resourceItem.getBeforehandTU()));
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
				resourceDAO.addResource(resource);
				//Quitamos la marca de pendiente de actualizar.
				resourceItem.clearStatus();
			}
			
			
		}
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
					resource.setTimeunit1(timeUnitDAO.loadSelected(resourceItem.getTimeunit()));
					resource.setTimeunit2(timeUnitDAO.loadSelected(resourceItem.getBeforehandTU()));
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
