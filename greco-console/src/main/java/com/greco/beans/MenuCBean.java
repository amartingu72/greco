package com.greco.beans;



public class MenuCBean {
	
	private MenuBBean menuBBean; //Inyectado
	private UserSBean userSBean; //Inyectado
	
	
	/**
	 * Navega a la p�gina de edici�n de cuenta.
	 * @return URL de la p�gina de edici�n de cuenta.
	 */
	public String navigateEditAccount(){
		//Navegamos
		return "/sections/admin/editaccount.xhtml?faces-redirect=true";
	}
	/**
	 * Navega a la comunidad seleccionada. Si no hubiese ninguna, va la 
	 * selecci�n de comunidad.
	 * @param communityItem
	 * @return
	 */
	public String navigateCommunity() {
		String ret;
		if (this.menuBBean.getCommunitiesSBean().getSelectedItem()!=null )
			ret="/sections/admin/editcommunity.xhtml?faces-redirect=true";
		else ret="/sections/admin/selectcom?faces-redirect=true";
		
		return ret;
	}
	
	public String navigateMembers(){
		return "/sections/admin/editmembers.xhtml?faces-redirect=true";
	}
	
	/**
	 * Edita la comunidad seleccionada.
	 * @param communityItem
	 * @return
	 */
	/*public String navigateCommunity(CommunityItem communityItem){
		communitiesSBean.setSelectedItem(communityItem);
		//Actualizamos la lista de recursos correspondientes
		resourcesSBean.setCommunityItem(communityItem);
		//La opci�n seleccionada debe ser la comunidad elegida.
		menuBBean.setHome(LAST);
		menuBBean.setCommunities_menu(HAS_SUB + " " + ACTIVE);
		menuBBean.setCommunities_list(DISPLAYED);
		menuBBean.setAdd_community_item(LAST);
		menuBBean.setEditAccount(LAST);
		menuBBean.setAbout_menu(HAS_SUB);
		menuBBean.setAbout_list(NONE);
		menuBBean.setAbout_item(NONE);
		
		
		return "/sections/admin/editcommunity.xhtml?faces-redirect=true";
	}*/
	/**
	 * Navega a la p�gina de nueva comunidad.
	 * @return URL de la p�gina de nueva comunidad.
	 */
	public String navigateNewCommunity(){			
		return "/sections/admin/newcommunity.xhtml?faces-redirect=true";
	}
	
	/**
	 * Vuelve al login de la p�gina de administraci�n
	 * @return URL de login.
	 */
	public String navigateLogout(){		
		return "/sections/login/logout?faces-redirect=true";
	}
	
	/**
	 * Vuelve al login de la p�gina de site de la comunidad que corresponda
	 * @return URL de login.
	 */
	public String navigateSiteLogout(){
		
		return "/sections/login/logout?communityid="+ userSBean.getCommunityId() +"&faces-redirect=true";
	}
	
	
	/**
	 * Navega a la ventana de selecci�n de comunidad.
	 * @return
	 */
	public String navigateSelectCommunity(){
		return "/sections/admin/selectcom?faces-redirect=true";
	}
	
	
	//GETTERs y SETTERs

	public MenuBBean getMenuBBean() {
		
		return menuBBean;
	}

	public void setMenuBBean(MenuBBean menuBBean) {
		this.menuBBean = menuBBean;
	}

	public UserSBean getUserSBean() {
		return userSBean;
	}
	public void setUserSBean(UserSBean userSBean) {
		this.userSBean = userSBean;
	}
	
	
}
