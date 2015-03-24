package com.greco.beans;

import com.greco.services.helpers.CommunityItem;

public class MenuCBean {

	public static final String NONE="";
	public static final String LAST="last";
	public static final String ACTIVE="active";
	public static final String HAS_SUB="has-sub";
	public static final String SELECTED="selected";
	public static final String DISPLAYED="display: block;";
	
	
	private CommunitiesSBean communitiesSBean;  //Inyectado
	private ResourcesSBean resourcesSBean;  //Inyectado
	private MenuBBean menuBBean; //Inyectado
	
	/**
	 * Navegación a la página home	
	 * @return URL de la página home.
	 */
	public String navigateHome(){
		//La opción activa es HOME.
		menuBBean.setHome(ACTIVE);
		menuBBean.setCommunities_menu(HAS_SUB);
		menuBBean.setCommunities_list(NONE);
		menuBBean.setCommunities_item(NONE);
		menuBBean.setAdd_community_item(LAST);
		menuBBean.setEditAccount(LAST);
		menuBBean.setAbout_menu(HAS_SUB);
		menuBBean.setAbout_list(NONE);
		menuBBean.setAbout_item(NONE);
		//Anulamos la comunidad seleccionada.
		communitiesSBean.unsetSelectedItem();
		//Navegamos
		return "/sections/admin/welcome.xhtml?faces-redirect=true";
	}
	/**
	 * Navega a la página de edición de cuenta.
	 * @return URL de la página de edición de cuenta.
	 */
	public String navigateEditAccount(){
		//La opción activa es "Mi cuenta"
		menuBBean.setHome(LAST);
		menuBBean.setCommunities_menu(HAS_SUB);
		menuBBean.setCommunities_list(NONE);
		menuBBean.setCommunities_item(NONE);
		menuBBean.setAdd_community_item(LAST);
		menuBBean.setEditAccount(ACTIVE);
		menuBBean.setAbout_menu(HAS_SUB);
		menuBBean.setAbout_list(NONE);
		menuBBean.setAbout_item(NONE);
		//Anulamos la comunidad seleccionada.
		communitiesSBean.unsetSelectedItem();
		//Navegamos
		return "/sections/admin/editaccount.xhtml?faces-redirect=true";
	}
	/**
	 * Edita la comunidad seleccionada.
	 * @param communityItem
	 * @return
	 */
	public String navigateCommunity(CommunityItem communityItem){
		communitiesSBean.setSelectedItem(communityItem);
		//Actualizamos la lista de recursos correspondientes
		resourcesSBean.setCommunityItem(communityItem);
		//La opción seleccionada debe ser la comunidad elegida.
		menuBBean.setHome(LAST);
		menuBBean.setCommunities_menu(HAS_SUB + " " + ACTIVE);
		menuBBean.setCommunities_list(DISPLAYED);
		menuBBean.setAdd_community_item(LAST);
		menuBBean.setEditAccount(LAST);
		menuBBean.setAbout_menu(HAS_SUB);
		menuBBean.setAbout_list(NONE);
		menuBBean.setAbout_item(NONE);
		
		
		return "/sections/admin/editcommunity.xhtml?faces-redirect=true";
	}
	/**
	 * Navega a la página de nueva comunidad.
	 * @return URL de la página de nueva comunidad.
	 */
	public String navigateNewCommunity(){
		//La comunidad seleccionada es null por ser nueva.
		communitiesSBean.setSelectedItem(null);
		//Actualizamos la lista de recursos, creando una lista vacía.
		resourcesSBean.setCommunityItem(null);
		menuBBean.setHome(LAST);
		menuBBean.setCommunities_menu(HAS_SUB + " " + ACTIVE);
		menuBBean.setCommunities_list(DISPLAYED);
		menuBBean.setAdd_community_item(SELECTED);
		menuBBean.setEditAccount(LAST);
		menuBBean.setAbout_menu(HAS_SUB);
		menuBBean.setAbout_list(NONE);
		menuBBean.setAbout_item(NONE);
				
		return "/sections/admin/newcommunity.xhtml?faces-redirect=true";
	}
	
	/**
	 * Vuelve al login.
	 * @return URL de login.
	 */
	public String navigateLogout(){		
		return "/sections/login/logout?faces-redirect=true";
	}
	
	
	//GETTERs y SETTERs

	public MenuBBean getMenuBBean() {
		
		return menuBBean;
	}

	public void setMenuBBean(MenuBBean menuBBean) {
		this.menuBBean = menuBBean;
	}

	public CommunitiesSBean getCommunitiesSBean() {
		return communitiesSBean;
	}

	public void setCommunitiesSBean(CommunitiesSBean communitiesSBean) {
		this.communitiesSBean = communitiesSBean;
	}

	public ResourcesSBean getResourcesSBean() {
		return resourcesSBean;
	}

	public void setResourcesSBean(ResourcesSBean resourcesSBean) {
		this.resourcesSBean = resourcesSBean;
	}
	/**
	 * Asigna estilo a cada comunidad, destacando en negrita la seleccionada.
	 * @param community
	 * @return
	 */
	public String styleClass(CommunityItem community) {
		String ret="";
		CommunityItem selectedCommunity =this.communitiesSBean.getSelectedItem();
		if ( selectedCommunity!= null ) {
			String name=selectedCommunity.getName();
			String zipcode=selectedCommunity.getZipcode();
			if (name != null)
				if ( name.equals(community.getName()) && zipcode.equals(community.getZipcode())) ret=SELECTED;
		}
		return ret;
	}
	
	/**
	 * Asigna estilo a la opción de nueva comunidad.
	 * @return 
	 */
	public String getStyleClassNewComunity() {
		return this.menuBBean.getAdd_community_item();
	}

	
}
