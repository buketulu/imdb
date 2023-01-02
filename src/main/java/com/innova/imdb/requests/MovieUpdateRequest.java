package com.innova.imdb.requests;

import java.util.Date;
import java.util.List;

import com.innova.imdb.entities.Multimedia;

import lombok.Data;

@Data
public class MovieUpdateRequest {
	String title;
	Date productionDate;
	String description;
	List<RoleRequest> actors;
	List<CrewRequest> crew;
    List<MultimediaRequest> multimedia;
}
