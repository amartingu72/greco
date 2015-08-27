package com.greco.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.primefaces.model.SortOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greco.entities.Profile;
import com.greco.entities.UsersCommunity;
import com.greco.repositories.ProfileDAO;
import com.greco.repositories.UserCommunitiesDAO;
import com.greco.services.UserCommunityDataProvider;
import com.greco.services.except.user.NoCommunityAdminException;
import com.greco.services.helpers.CommunityItem;
import com.greco.services.helpers.MemberItem;
import com.greco.services.helpers.ProfileItem;
import com.greco.services.helpers.StatusItem;

@Service("userCommunityDataProvider")
public class UserCommunityDataProviderImpl implements UserCommunityDataProvider{
	
	@Resource(name="UserCommunitiesRepository")
	private UserCommunitiesDAO userCommunitiesDAO;
	
	@Resource(name="ProfilesRepository")
	private ProfileDAO profileDAO;
	
	
	public List<MemberItem> findRangeOrder(CommunityItem communityItem, Map<String,Object> criteria,int start, int max, String sortField, SortOrder sortOrder) {
		
		List<UsersCommunity> data = userCommunitiesDAO.findRangeOrder(communityItem.getId(), criteria,start,max, sortField,sortOrder);
        Iterator<UsersCommunity> it=data.iterator();
        MemberItem memberItem;
        UsersCommunity uc;
        List<MemberItem> members=new ArrayList<MemberItem>();
        while ( it.hasNext() ) {
        	uc=it.next();
        	memberItem=new MemberItem();
        	memberItem.setMemberId(uc.getId());
        	memberItem.setCommunityId(communityItem.getId());
        	memberItem.setEmail(uc.getUser().getEmail());
        	memberItem.setId(uc.getUser().getId()); 
        	memberItem.setJoinningDate(uc.getRegisterDate());
        	memberItem.setApplication(uc.getApplication());
        	memberItem.setMydata(uc.getUser().getMydata());
        	memberItem.setNickname(uc.getUser().getNickname());
        	memberItem.setProfile(uc.getUser().getProfile());
        	
        	memberItem.setStatus(new StatusItem(uc.getStatus()));
        	members.add(memberItem);
        	Profile profile=uc.getProfile();
        	ProfileItem profileItem=new ProfileItem(profile.getId(), 
        			profile.getProfile(),profile.getDescription());
        	memberItem.setMemberProfile(profileItem);
        }
        return members;
	}

	@Override
	@Transactional
	public void saveStatus(MemberItem memberItem) {
		userCommunitiesDAO.saveStatus(memberItem.getMemberId(), memberItem.getStatusId());
	}

	@Override
	@Transactional
	public void saveMemberProfile(MemberItem memberItem) throws NoCommunityAdminException{		
		Profile profile=profileDAO.loadSelected(memberItem.getMemberProfile().getId());
		userCommunitiesDAO.saveProfile(memberItem.getMemberId(), profile);
	}

	@Override
	@Transactional
	public void removeMember(MemberItem memberItem) throws NoCommunityAdminException{ 	
		userCommunitiesDAO.remove(memberItem.getMemberId());
	}

	@Override
	public List<MemberItem> findPendingMemberships(CommunityItem communityItem) {
		List<UsersCommunity> data = userCommunitiesDAO.findPendingMemberships(communityItem.getId());
        Iterator<UsersCommunity> it=data.iterator();
        MemberItem memberItem;
        UsersCommunity uc;
        List<MemberItem> members=new ArrayList<MemberItem>();
        while ( it.hasNext() ) {
        	uc=it.next();
        	memberItem=new MemberItem();
        	memberItem.setMemberId(uc.getId());
        	memberItem.setCommunityId(communityItem.getId());
        	memberItem.setEmail(uc.getUser().getEmail());
        	memberItem.setId(uc.getUser().getId()); 
        	memberItem.setJoinningDate(uc.getRegisterDate());
        	memberItem.setApplication(uc.getApplication());
        	memberItem.setMydata(uc.getUser().getMydata());
        	memberItem.setNickname(uc.getUser().getNickname());
        	memberItem.setProfile(uc.getUser().getProfile());
        	
        	memberItem.setStatus(new StatusItem(uc.getStatus()));
        	members.add(memberItem);
        	Profile profile=uc.getProfile();
        	ProfileItem profileItem=new ProfileItem(profile.getId(), 
        			profile.getProfile(),profile.getDescription());
        	memberItem.setMemberProfile(profileItem);
        }
        return members;
	}

	@Override
	public int getAdmins(CommunityItem communityItem) {		
		return this.userCommunitiesDAO.adminCount(communityItem.getId());
	}

	@Override
	public int getMembers(CommunityItem communityItem) {	
		return this.userCommunitiesDAO.membersCount(communityItem.getId());
	}

	@Override
	public MemberItem find(int memberItemId) {
		UsersCommunity uc=this.userCommunitiesDAO.load(memberItemId);
		MemberItem memberItem=null;
		if ( uc!=null) { 
			memberItem=new MemberItem();
			memberItem=new MemberItem();
	    	memberItem.setMemberId(uc.getId());
	    	memberItem.setCommunityId(uc.getCommunity().getId());
	    	memberItem.setEmail(uc.getUser().getEmail());
	    	memberItem.setId(uc.getUser().getId()); 
	    	memberItem.setJoinningDate(uc.getRegisterDate());
	    	memberItem.setApplication(uc.getApplication());
	    	memberItem.setMydata(uc.getUser().getMydata());
	    	memberItem.setNickname(uc.getUser().getNickname());
	    	memberItem.setProfile(uc.getUser().getProfile());
	    	
	    	memberItem.setStatus(new StatusItem(uc.getStatus()));
	    	Profile profile=uc.getProfile();
	    	ProfileItem profileItem=new ProfileItem(profile.getId(), 
	    			profile.getProfile(),profile.getDescription());
	    	memberItem.setMemberProfile(profileItem);
		}
		return memberItem;
	}
	
	
}
