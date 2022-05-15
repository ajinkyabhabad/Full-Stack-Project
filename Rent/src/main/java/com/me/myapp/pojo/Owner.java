package com.me.myapp.pojo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.UniqueElements;


@Entity
@Table(name="ownertable")
public class Owner implements Serializable{

	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	@Column(name="ownerId")
	private int id;
	
	@Column(name="ownername", nullable=false)
	@NotNull
	private String name;
	
	
	@Column(unique = true)
	private String email;
	
	
	private String password;
	
	@OneToMany(mappedBy = "owner",fetch = FetchType.EAGER,cascade=CascadeType.ALL)
	private Set<Category> venues = new HashSet<Category>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Category> getVenues() {
		return venues;
	}

	public void setVenues(Set<Category> venues) {
		this.venues = venues;
	}
	
	public void addVenue(Category venue) {
		venues.add(venue);
	}
	
}
