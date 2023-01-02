package com.innova.imdb.requests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class MovieSearchRequest {
	String movieTitle;
	String companyName;
	String actorName;
	String actorRoleName;
	String crewName;
	//Date productionDate;	
}
