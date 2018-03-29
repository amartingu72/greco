package com.greco.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the countries database table.
 * 
 */
@Entity
@Table(name="countries")
@NamedQuery(name="Country.findAll", query="SELECT c FROM Country c")
public class Country implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String name;

	//bi-directional many-to-one association to Community
	@OneToMany(mappedBy="country")
	private List<Community> communities;

	public Country() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Community> getCommunities() {
		return this.communities;
	}

	public void setCommunities(List<Community> communities) {
		this.communities = communities;
	}

	public Community addCommunity(Community community) {
		getCommunities().add(community);
		community.setCountry(this);

		return community;
	}

	public Community removeCommunity(Community community) {
		getCommunities().remove(community);
		community.setCountry(null);

		return community;
	}

}