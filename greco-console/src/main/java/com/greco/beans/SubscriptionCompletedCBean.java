package com.greco.beans;

public class SubscriptionCompletedCBean {
	
	SubscriptionCompletedBBean subscriptionCompletedBBean; //Inyectado.
	
	public String submit(){
		String ret;
		ret="/sections/login/logout?";
		ret+="communityid="+this.subscriptionCompletedBBean.getCommunityId();
		ret+="&faces-redirect=true";
		return ret;
	}
	
	//GETTERs y SETTERs.

	public SubscriptionCompletedBBean getSubscriptionCompletedBBean() {
		return subscriptionCompletedBBean;
	}

	public void setSubscriptionCompletedBBean(
			SubscriptionCompletedBBean subscriptionCompletedBBean) {
		this.subscriptionCompletedBBean = subscriptionCompletedBBean;
	}
	
}
