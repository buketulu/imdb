package com.innova.imdb.responses;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import com.innova.imdb.entities.Crew;
import com.innova.imdb.entities.Multimedia;
import com.innova.imdb.entities.Role;

import lombok.Data;

@Data
public class MultimediaResponse {
	String type;
	String link;
	public MultimediaResponse(String type, String link) {
		super();
		this.type=type;
		this.link=link;
	}
	

	public MultimediaResponse(Multimedia m) {
		super();
		this.link=m.getLink();
		this.type=m.getType().toString();
	}
}
