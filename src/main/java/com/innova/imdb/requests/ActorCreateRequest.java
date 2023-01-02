package com.innova.imdb.requests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ActorCreateRequest {
	String name;
	Date birthdate;
	int height;
	String personalInfo;
	List<MovieRoleRequest> roles=new ArrayList<MovieRoleRequest>();
	List<MultimediaRequest> multimedia=new ArrayList<MultimediaRequest>();
}
