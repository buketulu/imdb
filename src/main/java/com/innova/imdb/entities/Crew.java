package com.innova.imdb.entities;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="crew")
@Data
public class Crew {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "movie_id")
	private Movie movie;
	
	private Position position;	
	
	public enum Position{
		DIRECTOR("Director"), WRITER("Writer");
		
		private String position;
		
		Position(String position){
			this.position=position;
		}
		
		public String toString() {
			return this.position;
		}
		
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Crew other = (Crew) obj;
		return Objects.equals(movie, other.movie) && Objects.equals(name, other.name)
				&& position == other.position;
	}

	@Override
	public int hashCode() {
		return Objects.hash(movie, name, position);
	}

	@Override
	public String toString() {
		return "Crew [name=" + name + ", position=" + position + "]";
	}
		
	
}
