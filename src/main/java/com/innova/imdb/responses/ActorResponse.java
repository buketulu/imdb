package com.innova.imdb.responses;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import com.innova.imdb.entities.Actor;
import com.innova.imdb.entities.Multimedia;
import com.innova.imdb.entities.Role;

import lombok.Data;

@Data
public class ActorResponse {
	Long id;
	String name;
	Date birthdate;
	int height;
	String personalInfo;
    Set<RoleResponse> roles = new HashSet<>();
    Set<MultimediaResponse> multimedia = new HashSet<>();
    public void addRoles(RoleResponse role) {
    	this.roles.add(role);
    }
    public void addMultimedia(MultimediaResponse m) {
    	this.multimedia.add(m);
    }
    public ActorResponse(Long id, String name, Date birthdate, int height, String personalInfo) {
		super();
		this.id = id;
		this.name = name;
		this.birthdate = birthdate;
		this.height = height;
		this.personalInfo = personalInfo;
	}

    public ActorResponse(Actor actor) {
    	super();
    	this.id=actor.getId();
    	this.name = actor.getName();
		this.birthdate =actor.getBirthdate();
		this.height = actor.getHeight();
		this.personalInfo = actor.getPersonalInfo();
		if (actor.getRoles()!=null) {
			for(Role role : actor.getRoles()) {
				this.addRoles(new RoleResponse(role));
			}
		}
		if(actor.getMultimedia()!=null) {
			for(Multimedia m: actor.getMultimedia()) {
				this.addMultimedia(new MultimediaResponse(m));
			}
		}
    }
	
	
	
	

}
