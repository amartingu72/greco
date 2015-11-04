package com.greco.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private byte adds;

	private String email;

	private String mydata;

	private String nickname;

	private String password;

	private String profile;

	//bi-directional many-to-one association to Reservation
	@OneToMany(mappedBy="user")
	private List<Reservation> reservations;

	//bi-directional many-to-many association to Community
	@ManyToMany
	@JoinTable(
		name="users_communities"
		, joinColumns={
			@JoinColumn(name="user_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="community_id")
			}
		)
	private List<Community> communities;

	//bi-directional many-to-one association to UsersCommunity
	@OneToMany(mappedBy="user")
	private List<UsersCommunity> usersCommunities;

	public User() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte getAdds() {
		return this.adds;
	}

	public void setAdds(byte adds) {
		this.adds = adds;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMydata() {
		return this.mydata;
	}

	public void setMydata(String mydata) {
		this.mydata = mydata;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProfile() {
		return this.profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public List<Reservation> getReservations() {
		return this.reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public Reservation addReservation(Reservation reservation) {
		getReservations().add(reservation);
		reservation.setUser(this);

		return reservation;
	}

	public Reservation removeReservation(Reservation reservation) {
		getReservations().remove(reservation);
		reservation.setUser(null);

		return reservation;
	}

	public List<Community> getCommunities() {
		return this.communities;
	}

	public void setCommunities(List<Community> communities) {
		this.communities = communities;
	}

	public List<UsersCommunity> getUsersCommunities() {
		return this.usersCommunities;
	}

	public void setUsersCommunities(List<UsersCommunity> usersCommunities) {
		this.usersCommunities = usersCommunities;
	}

	public UsersCommunity addUsersCommunity(UsersCommunity usersCommunity) {
		getUsersCommunities().add(usersCommunity);
		usersCommunity.setUser(this);

		return usersCommunity;
	}

	public UsersCommunity removeUsersCommunity(UsersCommunity usersCommunity) {
		getUsersCommunities().remove(usersCommunity);
		usersCommunity.setUser(null);

		return usersCommunity;
	}

}