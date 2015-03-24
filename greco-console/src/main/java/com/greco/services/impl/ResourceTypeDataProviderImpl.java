package com.greco.services.impl;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.greco.entities.Resourcetype;
import com.greco.repositories.ResourceTypeDAO;
import com.greco.services.ResourceTypeDataProvider;
import com.greco.services.helpers.ResourceTypeItem;

@Service("resourceTypeDataProvider")
public class ResourceTypeDataProviderImpl implements ResourceTypeDataProvider {
	@Resource(name="ResourceTypeRepository")
	private ResourceTypeDAO resourceTypesRepository;
	
	@Override
	public ResourceTypeItem[] getResourceTypesArray() {
		List<Resourcetype>  resourceTypeList=resourceTypesRepository.loadAllResourceTypes();
		ResourceTypeItem[] resourceTypes=new ResourceTypeItem[resourceTypeList.size()];
			
		Resourcetype resourceType;
		Iterator<Resourcetype> it=resourceTypeList.iterator();
		int i=0;
		while ( it.hasNext())
		{ 	
			resourceType=it.next();
			resourceTypes[i]=new ResourceTypeItem(resourceType.getId(), resourceType.getName(), resourceType.getDescription());
			i++;
		}
		return resourceTypes;
		
	}

}
