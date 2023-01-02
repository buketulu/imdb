package com.innova.imdb.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Role implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "movie_id")
	private Movie movie;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "actor_id")
	private Actor actor;
	
	private String roleName;
	
	private String description;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		return Objects.equals(actor, other.actor) 
				&&  Objects.equals(movie, other.movie)
				&& Objects.equals(roleName, other.roleName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(actor, movie, roleName);
	}

	@Override
	public String toString() {
		return "Role [movie=" + movie.getTitle() + ", actor=" + actor + ", roleName=" + roleName + ", description=" + description
				+ "]";
	}
	
	
	

}