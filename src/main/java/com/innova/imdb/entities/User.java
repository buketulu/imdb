package com.innova.imdb.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String userName;
	@Column(nullable = false)
	private String password;

	private CustomerType customerType;

	public enum CustomerType {
		NORMAL, COMPANY, ACTOR
	}

	// for actors
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "actor_id", nullable = true)
	private Actor actor;

	// for companies
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		return Objects.equals(userName, other.userName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userName);
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", customerType=" + customerType + "]";
	}

	public boolean isCompany() {
		if (this.customerType.compareTo(CustomerType.COMPANY) == 0)
			return true;
		else
			return false;
	}

	public boolean isActor() {
		if (this.customerType.compareTo(CustomerType.ACTOR) == 0)
			return true;
		else
			return false;
	}

	public User(String userName, String password, CustomerType customerType) {
		super();
		this.userName = userName;
		this.password = password;
		this.customerType = customerType;
	}

	public User() {
		super();
	}

	public User(Long id, String userName, String password, CustomerType customerType, Actor actor, Company company) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.customerType = customerType;
		this.actor = actor;
		this.company = company;
	}
	
	
}
