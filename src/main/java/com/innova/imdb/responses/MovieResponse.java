package com.innova.imdb.responses;

import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.innova.imdb.entities.Movie;

import lombok.Data;

@Data
public class MovieResponse {
	Long id;
	String title;
	Date productionDate;
	String companyName;
	String description;
	List<ActorNameResponse> actors;
	List<CrewResponse> crew;
	List<MultimediaResponse> multimedia;

	public MovieResponse(Long id, String title, Date productionDate, String companyName, String description,
			List<ActorNameResponse> actors, List<CrewResponse> crew, List<MultimediaResponse> multimedia) {
		super();
		this.id = id;
		this.title = title;
		this.productionDate = productionDate;
		this.companyName = companyName;
		this.description = description;
		this.actors = actors;
		this.crew = crew;
		this.multimedia = multimedia;
	}

	public MovieResponse(Movie movie) {
		super();
		this.id = movie.getId();
		this.title = movie.getTitle();
		this.productionDate = movie.getProductionDate();
		this.companyName = movie.getCompany().getName();
		this.description = movie.getDescription();
		this.actors = movie.getActors().stream().map(role -> new ActorNameResponse(role.getActor()))
				.collect(Collectors.toList());
		this.crew = movie.getCrew().stream().map(c -> new CrewResponse(c)).collect(Collectors.toList());
		this.multimedia = movie.getMultimedia().stream().map(m -> new MultimediaResponse(m))
				.collect(Collectors.toList());
	}

}
