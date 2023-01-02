package com.innova.imdb.responses;

import java.math.BigInteger;
import java.util.Date;

import javax.print.attribute.standard.DateTimeAtCompleted;

import com.innova.imdb.entities.Movie;

import lombok.Data;

@Data
public class MovieTitleResponse {
	Long id;
	String title;
	Date productionDate;
	
	public MovieTitleResponse(Long id, String title, Date productionDate) {
		super();
		this.id = id;
		this.title = title;
		this.productionDate = productionDate;
	}
	
	public MovieTitleResponse(BigInteger id, String title, Date productionDate) {
		super();
		this.id = id.longValue();
		this.title = title;
		this.productionDate = productionDate;
	}
	
	public MovieTitleResponse(Movie movie) {
		super();
		this.id = movie.getId();
		this.title = movie.getTitle();
		this.productionDate = movie.getProductionDate();
	}
	

}
