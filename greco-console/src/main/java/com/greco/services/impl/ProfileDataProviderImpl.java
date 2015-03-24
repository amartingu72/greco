package com.greco.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.greco.entities.Profile;
import com.greco.repositories.ProfileDAO;
import com.greco.services.ProfileDataProvider;
import com.greco.services.helpers.ProfileItem;


@Service("profileDataProvider")
public class ProfileDataProviderImpl implements ProfileDataProvider {
	
	@Resource(name="ProfilesRepository")
	private ProfileDAO profilesRepository;
	
	
	@Override
	public List<ProfileItem> getAllProfiles() {
		
		List<Profile>  entityList=profilesRepository.loadAll();
		List<ProfileItem> profileItemList=new ArrayList<ProfileItem>();
			
		Profile profile;
		Iterator<Profile> it=entityList.iterator();
		while ( it.hasNext())
		{ 	
			profile=it.next();
			profileItemList.add(new ProfileItem(profile.getId(),profile.getProfile(),profile.getDescription()));
		}
		return profileItemList;
		
	}
	
	

}
