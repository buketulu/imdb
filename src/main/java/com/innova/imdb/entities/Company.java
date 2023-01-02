package com.innova.imdb.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "companies")
@Data
public class Company implements Serializable {
 
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		
		@OneToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "company_userId")
		private User companyUser;
		
		private String name;
		
		@OneToMany(fetch = FetchType.LAZY)
		@JoinColumn(name = "movie", nullable = true)
		private Set<Movie> movieSet = new HashSet<>();
		
		public void addMovie(Movie movie) {
			this.movieSet.add(movie);
		}
		
		public void clearMovies() {
			this.movieSet.clear();
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Company other = (Company) obj;
			return Objects.equals(this.getName(), other.getName());
		}

		@Override
		public int hashCode() {
			return Objects.hash(name);
		}

		@Override
		public String toString() {
			return "Company [name=" + name + "]";
		}
		
		
}