package com.example.restservice;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tutorials")
public class Rental {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(name = "title")
	private String owner;
	@Column(name = "description")
	private String description;
	@Column(name = "published")
	private boolean available;

	public Rental() {
	}

	public Rental(String owner, String description, boolean available) {
		this.owner = owner;
		this.description = description;
		this.available = available;
	}

	public long getId() {
		return id;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean isAvailable) {
		this.available = isAvailable;
	}

	@Override
	public String toString() {
		return "Rental [id=" + id + ", owner=" + owner + ", desc=" + description + ", published=" + available + "]";
	}
}
