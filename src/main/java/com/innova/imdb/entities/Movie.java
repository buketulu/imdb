package com.innova.imdb.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.innova.imdb.responses.MovieTitleResponse;

import lombok.Data;

@NamedNativeQuery(name = "Movie.getMoviesPageByPage", query = "select id, title, production_date from movies order by production_date desc limit :pageNo, :total", resultSetMapping = "MovieTitleResponse")

@NamedNativeQuery(name = "Movie.findAllByCompany", query = "select id, title, production_date from movies where company_id= :companyId", resultSetMapping = "MovieTitleResponse")

@SqlResultSetMapping(name = "MovieTitleResponse", classes = @ConstructorResult(targetClass = MovieTitleResponse.class, columns = {
		@ColumnResult(name = "id"), @ColumnResult(name = "title"), @ColumnResult(name = "production_date") }))

@Entity
@Table(name = "movies", uniqueConstraints = {
		@UniqueConstraint(name = "MovieNameDate", columnNames = { "title", "productionDate" }) })
@Data
public class Movie implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "companyId")
	private Company company;

	@Lob
	@Column(columnDefinition = "text")
	private String description;

	private Date productionDate;

	@OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Role> actors = new HashSet<>();

	@OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Crew> crew = new HashSet<>();

	@OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Multimedia> multimedia = new HashSet<>();

	public void addActor(Role actor) {
		this.actors.add(actor);
	}

	public void addCrew(Crew crew) {
		this.crew.add(crew);
	}

	public void clearActors() {
		this.actors.clear();
	}

	public void clearCrew() {
		this.crew.clear();
	}

	public void addMultimedia(Multimedia newMultimedia) {
		this.multimedia.add(newMultimedia);
	}

	public void clearMultimedia() {
		this.multimedia.clear();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movie other = (Movie) obj;
		return Objects.equals(company, other.company) && Objects.equals(productionDate, other.productionDate)
				&& Objects.equals(title, other.title);
	}

	@Override
	public int hashCode() {
		return Objects.hash(company, productionDate, title);
	}

	@Override
	public String toString() {
		return "Movie [title=" + title + ", company=" + company + ", description=" + description + ", productionDate="
				+ productionDate + ", actors=" + actors + ", crew=" + crew + "]";
	}

}
