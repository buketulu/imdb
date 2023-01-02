package com.innova.imdb.responses;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import com.innova.imdb.entities.Role;

import lombok.Data;

@Data
public class RoleResponse {
	String movieTitle;
	String actorName;
	String roleName;
	String description;
	
	
public RoleResponse(Role role) {
	super();
	this.movieTitle=role.getMovie().getTitle();
	this.actorName=role.getActor().getName();
	this.roleName=role.getRoleName();
	this.description=role.getDescription();
}
	
    
	
	
	
	

}
