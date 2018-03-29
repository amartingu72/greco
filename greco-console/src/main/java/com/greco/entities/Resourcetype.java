package com.greco.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the resourcetypes database table.
 * 
 */
@Entity
@Table(name="resourcetypes")
@NamedQuery(name="Resourcetype.findAll", query="SELECT r FROM Resourcetype r")
public class Resourcetype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String description;

	private String name;

	//bi-directional many-to-one association to Resource
	@OneToMany(mappedBy="resourcetype")
	private List<Resource> resources;

	public Resourcetype() {
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Resource> getResources() {
		return this.resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	public Resource addResource(Resource resource) {
		getResources().add(resource);
		resource.setResourcetype(this);

		return resource;
	}

	public Resource removeResource(Resource resource) {
		getResources().remove(resource);
		resource.setResourcetype(null);

		return resource;
	}

}