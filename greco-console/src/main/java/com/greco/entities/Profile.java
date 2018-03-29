package com.greco.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the profiles database table.
 * 
 */
@Entity
@Table(name="profiles")
@NamedQuery(name="Profile.findAll", query="SELECT p FROM Profile p")
public class Profile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String description;

	private String profile;

	//bi-directional many-to-one association to UsersCommunity
	@OneToMany(mappedBy="profile")
	private List<UsersCommunity> usersCommunities;

	public Profile() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProfile() {
		return this.profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public List<UsersCommunity> getUsersCommunities() {
		return this.usersCommunities;
	}

	public void setUsersCommunities(List<UsersCommunity> usersCommunities) {
		this.usersCommunities = usersCommunities;
	}

	public UsersCommunity addUsersCommunity(UsersCommunity usersCommunity) {
		getUsersCommunities().add(usersCommunity);
		usersCommunity.setProfile(this);

		return usersCommunity;
	}

	public UsersCommunity removeUsersCommunity(UsersCommunity usersCommunity) {
		getUsersCommunities().remove(usersCommunity);
		usersCommunity.setProfile(null);

		return usersCommunity;
	}

}