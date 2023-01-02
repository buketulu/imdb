package com.innova.imdb.responses;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import com.innova.imdb.entities.Crew;
import com.innova.imdb.entities.Role;

import lombok.Data;

@Data
public class CrewResponse {
	Long id;
	String name;
	String position;
	public CrewResponse(Long id, String name, String position) {
		super();
		this.id = id;
		this.name = name;
		this.position = position;
	}
	

	public CrewResponse(Crew crew) {
		super();
		this.id = crew.getId();
		this.name = crew.getName();
		this.position = crew.getPosition().toString();

	}
}
