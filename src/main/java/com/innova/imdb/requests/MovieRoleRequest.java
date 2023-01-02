package com.innova.imdb.requests;

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
public class MovieRoleRequest {
	Long movieId;
	String roleName;
	String description;

}
