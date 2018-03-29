package com.greco.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the users_communities database table.
 * 
 */
@Entity
@Table(name="users_communities")
@NamedQuery(name="UsersCommunity.findAll", query="SELECT u FROM UsersCommunity u")
public class UsersCommunity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String application;

	private Timestamp registerDate;

	private int status;

	//bi-directional many-to-one association to Community
	@ManyToOne
	private Community community;

	//bi-directional many-to-one association to Profile
	@ManyToOne
	private Profile profile;

	//bi-directional many-to-one association to User
	@ManyToOne
	private User user;

	public UsersCommunity() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getApplication() {
		return this.application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public Timestamp getRegisterDate() {
		return this.registerDate;
	}

	public void setRegisterDate(Timestamp registerDate) {
		this.registerDate = registerDate;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Community getCommunity() {
		return this.community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}

	public Profile getProfile() {
		return this.profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}