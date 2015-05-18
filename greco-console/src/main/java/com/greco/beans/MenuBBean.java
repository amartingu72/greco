package com.greco.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;

public class MenuBBean implements Serializable{

	private static final long serialVersionUID = 6544194731841167295L;
	
	
	/**
	 * Indica si se debe haber opción de selección de comunidad.
	 * La hay cuando el usuario tiene más de una comunidad.
	 */
	private boolean showSelectCommunity;
	/**
	 * Indica si debe haber opción de mostrar miembros. Requiere una 
	 * comunidad seleccionada.
	 */
	private boolean showMembers;
	private CommunitiesSBean communitiesSBean;  //Inyectado
	
	
	@PostConstruct
	public void initialize(){
		showMembers=communitiesSBean.getSelectedItem() != null;
		showSelectCommunity=communitiesSBean.getMyCommunities().size()>1;	
	}

	//GETTERs y SETTERs


	public boolean isShowSelectCommunity() {
		return showSelectCommunity;
	}




	public void setShowSelectCommunity(boolean showSelectCommunity) {
		this.showSelectCommunity = showSelectCommunity;
	}




	public boolean isShowMembers() {
		return showMembers;
	}




	public void setShowMembers(boolean showMembers) {
		this.showMembers = showMembers;
	}




	public CommunitiesSBean getCommunitiesSBean() {
		return communitiesSBean;
	}




	public void setCommunitiesSBean(CommunitiesSBean communitiesSBean) {
		this.communitiesSBean = communitiesSBean;
	}

	
		
}
