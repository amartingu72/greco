package com.greco.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the resources database table.
 * 
 */
@Entity
@Table(name="resources")
@NamedQuery(name="Resource.findAll", query="SELECT r FROM Resource r")
public class Resource implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String availableFromTime;

	private String availableToTime;

	private int beforehand;

	private String description;

	private int maxTime;

	private int minTime;

	private String name;

	//bi-directional many-to-one association to Exception
	@OneToMany(mappedBy="resource")
	private List<Exception> exceptions;

	//bi-directional many-to-one association to Reservation
	@OneToMany(mappedBy="resource")
	private List<Reservation> reservations;

	//bi-directional many-to-one association to Community
	@ManyToOne
	private Community community;

	//bi-directional many-to-one association to Resourcetype
	@ManyToOne
	private Resourcetype resourcetype;

	//bi-directional many-to-one association to Timeunit
	@ManyToOne
	@JoinColumn(name="beforehandtu_id")
	private Timeunit timeunit1;

	//bi-directional many-to-one association to Timeunit
	@ManyToOne
	@JoinColumn(name="timeunit_id")
	private Timeunit timeunit2;

	public Resource() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAvailableFromTime() {
		return this.availableFromTime;
	}

	public void setAvailableFromTime(String availableFromTime) {
		this.availableFromTime = availableFromTime;
	}

	public String getAvailableToTime() {
		return this.availableToTime;
	}

	public void setAvailableToTime(String availableToTime) {
		this.availableToTime = availableToTime;
	}

	public int getBeforehand() {
		return this.beforehand;
	}

	public void setBeforehand(int beforehand) {
		this.beforehand = beforehand;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getMaxTime() {
		return this.maxTime;
	}

	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}

	public int getMinTime() {
		return this.minTime;
	}

	public void setMinTime(int minTime) {
		this.minTime = minTime;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Exception> getExceptions() {
		return this.exceptions;
	}

	public void setExceptions(List<Exception> exceptions) {
		this.exceptions = exceptions;
	}

	public Exception addException(Exception exception) {
		getExceptions().add(exception);
		exception.setResource(this);

		return exception;
	}

	public Exception removeException(Exception exception) {
		getExceptions().remove(exception);
		exception.setResource(null);

		return exception;
	}

	public List<Reservation> getReservations() {
		return this.reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public Reservation addReservation(Reservation reservation) {
		getReservations().add(reservation);
		reservation.setResource(this);

		return reservation;
	}

	public Reservation removeReservation(Reservation reservation) {
		getReservations().remove(reservation);
		reservation.setResource(null);

		return reservation;
	}

	public Community getCommunity() {
		return this.community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}

	public Resourcetype getResourcetype() {
		return this.resourcetype;
	}

	public void setResourcetype(Resourcetype resourcetype) {
		this.resourcetype = resourcetype;
	}

	public Timeunit getTimeunit1() {
		return this.timeunit1;
	}

	public void setTimeunit1(Timeunit timeunit1) {
		this.timeunit1 = timeunit1;
	}

	public Timeunit getTimeunit2() {
		return this.timeunit2;
	}

	public void setTimeunit2(Timeunit timeunit2) {
		this.timeunit2 = timeunit2;
	}

}