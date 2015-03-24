package com.greco.services.helpers;


import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import com.greco.services.UserCommunityDataProvider;


/**
 * Este servicio no tiene interfaz propio ya que utiliza el LazyDataModel.
 * @author Alberto Martín
 *
 */

public class LazyMembersDataModel extends LazyDataModel<MemberItem> {

	private static final long serialVersionUID = 1L;
	/**
	 * Comunidad cuyos miembros se recuperan.
	 */
	private CommunityItem communityItem;
	
	
	private UserCommunityDataProvider userCommunityDataProvider;
	
	public LazyMembersDataModel (CommunityItem communityItem, UserCommunityDataProvider userCommunityDataProvider) {
		this.communityItem=communityItem;
		this.userCommunityDataProvider=userCommunityDataProvider;
	}
		
	@Override
    public Object getRowKey(MemberItem member) {
        return member.getId();
    }

	@Override
    public List<MemberItem> load(int first, 
							int pageSize, 
							String sortField, 
							SortOrder sortOrder, 
							Map<String,Object> filters) {
		
		
		List<MemberItem> data = userCommunityDataProvider.findRangeOrder(communityItem,filters,first,pageSize, sortField,sortOrder);
        
        setRowCount(data.size());
        return data;
	}
	
	//GETTERs y SETTERs
	public CommunityItem getCommunityItem() {
		return communityItem;
	}

	public void setCommunityItem(CommunityItem communityItem) {
		this.communityItem = communityItem;
	}

	public UserCommunityDataProvider getUserCommunityDataProvider() {
		return userCommunityDataProvider;
	}

	public void setUserCommunityDataProvider(
			UserCommunityDataProvider userCommunityDataProvider) {
		this.userCommunityDataProvider = userCommunityDataProvider;
	}
	
	
	
}
