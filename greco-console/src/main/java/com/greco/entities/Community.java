package com.greco.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the communities database table.
 * 
 */
@Entity
@Table(name="communities")
@NamedQuery(name="Community.findAll", query="SELECT c FROM Community c")
public class Community implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private byte available;

	private byte membercheck;

	private String name;

	private String notes;

	private String zipcode;

	//bi-directional many-to-one association to Country
	@ManyToOne
	@JoinColumn(name="countrie_id")
	private Country country;

	//bi-directional many-to-one association to Resource
	@OneToMany(fetch = FetchType.EAGER, mappedBy="community")
	private List<Resource> resources;

	//bi-directional many-to-many association to User
	@ManyToMany(mappedBy="communities")
	private List<User> users;

	//bi-directional many-to-one association to UsersCommunity
	@OneToMany(mappedBy="community")
	private List<UsersCommunity> usersCommunities;

	public Community() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte getAvailable() {
		return this.available;
	}

	public void setAvailable(byte available) {
		this.available = available;
	}

	public byte getMembercheck() {
		return this.membercheck;
	}

	public void setMembercheck(byte membercheck) {
		this.membercheck = membercheck;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public List<Resource> getResources() {
		return this.resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	public Resource addResource(Resource resource) {
		getResources().add(resource);
		resource.setCommunity(this);

		return resource;
	}

	public Resource removeResource(Resource resource) {
		getResources().remove(resource);
		resource.setCommunity(null);

		return resource;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<UsersCommunity> getUsersCommunities() {
		return this.usersCommunities;
	}

	public void setUsersCommunities(List<UsersCommunity> usersCommunities) {
		this.usersCommunities = usersCommunities;
	}

	public UsersCommunity addUsersCommunity(UsersCommunity usersCommunity) {
		getUsersCommunities().add(usersCommunity);
		usersCommunity.setCommunity(this);

		return usersCommunity;
	}

	public UsersCommunity removeUsersCommunity(UsersCommunity usersCommunity) {
		getUsersCommunities().remove(usersCommunity);
		usersCommunity.setCommunity(null);

		return usersCommunity;
	}

}