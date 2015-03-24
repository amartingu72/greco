package com.greco.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;

public class MenuBBean implements Serializable{

	private static final long serialVersionUID = 6544194731841167295L;
	
	
	private String home;
	private String communities_menu;
	private String communities_list;
	private String communities_item;
	private String add_community_item;
	private String about_menu;
	private String about_list;
	private String about_item;
	private String editAccount;
	
	
	@PostConstruct
	public void initialize(){
		home=MenuCBean.ACTIVE;
		communities_menu=MenuCBean.HAS_SUB;
		communities_list=MenuCBean.NONE;
		communities_item=MenuCBean.NONE;
		add_community_item=MenuCBean.LAST;
		editAccount=MenuCBean.LAST;
		about_menu=MenuCBean.HAS_SUB;
		about_list=MenuCBean.NONE; //o DISPLAYED
		about_item=MenuCBean.NONE;
		
	}
	
	
	
	//GETTERs y SETTERs

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getCommunities_menu() {
		return communities_menu;
	}

	public void setCommunities_menu(String communities_menu) {
		this.communities_menu = communities_menu;
	}

	public String getCommunities_list() {
		return communities_list;
	}

	public void setCommunities_list(String communities_list) {
		this.communities_list = communities_list;
	}

	public String getCommunities_item() {
		return communities_item;
	}

	public void setCommunities_item(String communities_item) {
		this.communities_item = communities_item;
	}

	public String getAbout_menu() {
		return about_menu;
	}

	public void setAbout_menu(String about_menu) {
		this.about_menu = about_menu;
	}

	public String getAbout_list() {
		return about_list;
	}

	public void setAbout_list(String about_list) {
		this.about_list = about_list;
	}

	public String getAbout_item() {
		return about_item;
	}

	public void setAbout_item(String about_item) {
		this.about_item = about_item;
	}

	public String getEditAccount() {
		return editAccount;
	}

	public void setEditAccount(String editAccount) {
		this.editAccount = editAccount;
	}

	public String getAdd_community_item() {
		return add_community_item;
	}

	public void setAdd_community_item(String add_community_item) {
		this.add_community_item = add_community_item;
	}
	
}
