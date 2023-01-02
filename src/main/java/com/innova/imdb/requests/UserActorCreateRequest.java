package com.innova.imdb.requests;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import com.innova.imdb.entities.Actor;
import com.innova.imdb.entities.Role;

import lombok.Data;

@Data
public class UserActorCreateRequest {
	String userName;
	String password;
	String customerType;
	String name;
	Date birthdate;
	int height;
	String personalInfo;
	List<MovieRoleRequest> roles=new ArrayList<MovieRoleRequest>();
	List<MultimediaRequest> multimedia=new ArrayList<MultimediaRequest>();
}
