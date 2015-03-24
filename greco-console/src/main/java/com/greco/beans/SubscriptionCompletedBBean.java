package com.greco.beans;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

public class SubscriptionCompletedBBean {
	int communityId; 

	@PostConstruct
	public void initialize(){
		String sCom=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("communityid");
		communityId=Integer.parseInt(sCom);
		
	}
	
	//GETTERs y SETTERs
	public int getCommunityId() {
		return communityId;
	}

	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}
	
	
}
