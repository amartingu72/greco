package com.greco.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the exceptions database table.
 * 
 */
@Entity
@Table(name="exceptions")
@NamedQuery(name="Exception.findAll", query="SELECT e FROM Exception e")
public class Exception implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;
	@Column(name="fromdate")
	private Timestamp fromDate;
	@Column(name="todate")
	private Timestamp toDate;

	//bi-directional many-to-one association to Resource
	@ManyToOne
	@JoinColumn(name="resources_id")
	private Resource resource;

	public Exception() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getFromDate() {
		return this.fromDate;
	}

	public void setFromDate(Timestamp fromDate) {
		this.fromDate = fromDate;
	}

	public Timestamp getToDate() {
		return this.toDate;
	}

	public void setToDate(Timestamp toDate) {
		this.toDate = toDate;
	}

	public Resource getResource() {
		return this.resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

}