package com.innova.imdb.controllers;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innova.imdb.entities.Actor;
import com.innova.imdb.requests.ActorCreateRequest;
import com.innova.imdb.requests.ActorUpdateRequest;
import com.innova.imdb.responses.ActorNameResponse;
import com.innova.imdb.responses.ActorResponse;
import com.innova.imdb.services.ActorService;


@RestController
@RequestMapping("/actors")
public class ActorController {
	
	@Autowired
	private ActorService actorService;
	
	
	@GetMapping
	public Set<ActorNameResponse> getActors(){
		return actorService.getActors();
	}
	
	@GetMapping("/{actorId}")
	public ActorResponse getActor(@PathVariable Optional<Long> actorId){
		Actor actor=actorService.getActorById(actorId);
		return new ActorResponse(actor);
	}
	
	 /* @PostMapping
	public ActorResponse insertActor(@RequestBody ActorCreateRequest newActorRequest) throws Exception {
		Actor actor=actorService.insertActor(newActorRequest);
		return new ActorResponse(actor);
	}*/
	
	@PutMapping
	public ActorResponse updateActor(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> actorId, @RequestBody ActorUpdateRequest newActor) {
		Actor actor=actorService.updateActor(userId, actorId, newActor);
		return new ActorResponse(actor);
	}



}

