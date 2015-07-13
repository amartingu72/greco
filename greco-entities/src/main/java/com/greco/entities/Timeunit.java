package com.greco.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the timeunit database table.
 * 
 */
@Entity
@NamedQuery(name="Timeunit.findAll", query="SELECT t FROM Timeunit t")
public class Timeunit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String name;

	//bi-directional many-to-one association to Resource
	@OneToMany(mappedBy="timeunit1")
	private List<Resource> resources1;

	//bi-directional many-to-one association to Resource
	@OneToMany(mappedBy="timeunit2")
	private List<Resource> resources2;

	public Timeunit() {
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

	public List<Resource> getResources1() {
		return this.resources1;
	}

	public void setResources1(List<Resource> resources1) {
		this.resources1 = resources1;
	}

	public Resource addResources1(Resource resources1) {
		getResources1().add(resources1);
		resources1.setTimeunit1(this);

		return resources1;
	}

	public Resource removeResources1(Resource resources1) {
		getResources1().remove(resources1);
		resources1.setTimeunit1(null);

		return resources1;
	}

	public List<Resource> getResources2() {
		return this.resources2;
	}

	public void setResources2(List<Resource> resources2) {
		this.resources2 = resources2;
	}

	public Resource addResources2(Resource resources2) {
		getResources2().add(resources2);
		resources2.setTimeunit2(this);

		return resources2;
	}

	public Resource removeResources2(Resource resources2) {
		getResources2().remove(resources2);
		resources2.setTimeunit2(null);

		return resources2;
	}

}