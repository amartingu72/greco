package com.greco.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.primefaces.model.SortOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greco.entities.Community;
import com.greco.entities.Profile;
import com.greco.entities.UsersCommunity;
import com.greco.repositories.ProfileDAO;
import com.greco.repositories.UserCommunitiesDAO;
import com.greco.services.UserCommunityDataProvider;
import com.greco.services.except.user.NoCommunityAdminException;
import com.greco.services.except.user.NoMemberException;
import com.greco.services.helpers.CommunityItem;
import com.greco.services.helpers.MemberItem;
import com.greco.services.helpers.ProfileItem;
import com.greco.services.helpers.StatusItem;
import com.greco.services.helpers.UserItem;

@Service("userCommunityDataProvider")
public class UserCommunityDataProviderImpl implements UserCommunityDataProvider{
	
	@Resource(name="UserCommunitiesRepository")
	private UserCommunitiesDAO userCommunitiesDAO;
	
	@Resource(name="ProfilesRepository")
	private ProfileDAO profileDAO;
	
	/**
	 * Convierte una entidad en un objeto MemberItem
	 * @param uc Entidad
	 * @return MemberItem
	 */
	private MemberItem convert(UsersCommunity uc){
		MemberItem memberItem=new MemberItem();
    	memberItem.setMemberId(uc.getId());
    	
    	CommunityItem communityItem=new CommunityItem(uc.getCommunity().getId(),
    			uc.getCommunity().getAvailable()!=0,
    			uc.getCommunity().getName(),
    			uc.getCommunity().getZipcode(),
    			uc.getCommunity().getNotes(),
    			uc.getCommunity().getCountry().getName()
    			);
    	
    	memberItem.setCommunityItem(communityItem);
    	
    	memberItem.setEmail(uc.getUser().getEmail());
    	memberItem.setId(uc.getUser().getId()); 
    	memberItem.setJoinningDate(uc.getRegisterDate());
    	memberItem.setApplication(uc.getApplication());
    	memberItem.setMydata(uc.getUser().getMydata());
    	memberItem.setNickname(uc.getUser().getNickname());
    	memberItem.setProfile(uc.getUser().getProfile());
    	memberItem.setAdds(uc.getUser().getAdds()!=0);
    	
    	memberItem.setStatus(new StatusItem(uc.getStatus()));
    	
    	Profile profile=uc.getProfile();
    	ProfileItem profileItem=new ProfileItem(profile.getId(), 
    			profile.getProfile(),profile.getDescription());
    	memberItem.setMemberProfile(profileItem);
    	return memberItem;
		
	}
	
	public List<MemberItem> findRangeOrder(CommunityItem communityItem, Map<String,Object> criteria,int start, int max, String sortField, SortOrder sortOrder) {
		
		List<UsersCommunity> data = userCommunitiesDAO.findRangeOrder(communityItem.getId(), criteria,start,max, sortField,sortOrder);
        Iterator<UsersCommunity> it=data.iterator();
        MemberItem memberItem;
        UsersCommunity uc;
        List<MemberItem> members=new ArrayList<MemberItem>();
        while ( it.hasNext() ) {
        	uc=it.next();
        	memberItem=convert(uc);
        	members.add(memberItem);
        	
        }
        return members;
	}

	@Override
	@Transactional
	public void saveStatus(MemberItem memberItem) throws NoMemberException {
		userCommunitiesDAO.saveStatus(memberItem.getMemberId(), memberItem.getStatusId());
	}

	@Override
	@Transactional
	public void saveMemberProfile(MemberItem memberItem) throws NoCommunityAdminException, NoMemberException{		
		Profile profile=profileDAO.loadSelected(memberItem.getMemberProfile().getId());
		userCommunitiesDAO.saveProfile(memberItem.getMemberId(), profile);
	}

	@Override
	@Transactional
	public void removeMember(MemberItem memberItem) throws NoCommunityAdminException, NoMemberException{ 	
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
        	memberItem=convert(uc);
        	members.add(memberItem);
        	
        }
        return members;
	}

	@Override
	public int getAdmins(CommunityItem communityItem) {		
		return this.userCommunitiesDAO.adminCount(communityItem.getId());
	}
	
	@Override
	public List<MemberItem> getAdmindList(CommunityItem communityItem){
		
	 List<UsersCommunity> ucList=this.userCommunitiesDAO.getAdmins(communityItem.getId());
	 List<MemberItem> memberItemList=new ArrayList<MemberItem>();
	 Iterator<UsersCommunity> it=ucList.iterator();
	 UsersCommunity uc=null;
	
	 while ( it.hasNext() ){
		 uc=it.next();
		 memberItemList.add(convert(uc));
	 }
	 
	 return memberItemList;
	 
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
			memberItem=convert(uc);
		}
		return memberItem;
	}

	@Override
	public List<MemberItem> getMyCommunities(UserItem userItem) {
		List<UsersCommunity> data = userCommunitiesDAO.getSubscriptions(userItem.getId());
        Iterator<UsersCommunity> it=data.iterator();
        MemberItem memberItem;
        UsersCommunity uc;
        List<MemberItem> members=new ArrayList<MemberItem>();
        while ( it.hasNext() ) {
        	uc=it.next();
        	memberItem=convert(uc);
        	members.add(memberItem);
        	
        }
        return members;
	}

	@Override
	public List<CommunityItem> getMyAdministeredCommunities(UserItem userItem) {
		List<UsersCommunity> data = userCommunitiesDAO.getSubscriptions(userItem.getId(),ProfileItem.ADMIN);
        Iterator<UsersCommunity> it=data.iterator(); 
        
        CommunityItem communityItem;
        Community community;
       
        List<CommunityItem> communities=new ArrayList<CommunityItem>();
        while ( it.hasNext() ) {
        	community=it.next().getCommunity();
        	communityItem=new CommunityItem(community.getId(),(community.getAvailable()!=0), community.getName(),
    				community.getZipcode(),null,null,community.getNotes(), community.getCountry().getName());
        	communityItem.setMembercheck(community.getMembercheck()!=0);        	
        	communities.add(communityItem);
        	
        }
        return communities;
		
	}

	@Override
	public MemberItem find(UserItem userItem, int communityId) {
		UsersCommunity uc=userCommunitiesDAO.getSubscription(userItem.getId(), communityId);
		MemberItem memberItem=null;
		if (uc != null) memberItem=convert(uc);
		return memberItem;
	}
	
	
}
