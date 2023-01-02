package com.innova.imdb.responses;

import java.util.Date;

import com.innova.imdb.entities.Actor;

import lombok.Data;

@Data
public class ActorNameResponse {
	Long id;
	String name;
	
	public ActorNameResponse(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public ActorNameResponse(Actor actor) {
		super();
		this.id=actor.getId();
		this.name=actor.getName();
	}
	
	
	

}
