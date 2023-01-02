package com.innova.imdb.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innova.imdb.entities.Actor;
import com.innova.imdb.entities.Movie;
import com.innova.imdb.entities.Multimedia;
import com.innova.imdb.entities.Role;
import com.innova.imdb.entities.User;
import com.innova.imdb.exceptions.ActorNotFoundException;
import com.innova.imdb.exceptions.InnovaImdbAuthenticationException;
import com.innova.imdb.exceptions.MovieNotFoundException;
import com.innova.imdb.exceptions.ParameterControlException;
import com.innova.imdb.exceptions.UserNotFoundException;
import com.innova.imdb.repository.ActorRepository;
import com.innova.imdb.repository.MovieRepository;
import com.innova.imdb.requests.ActorUpdateRequest;
import com.innova.imdb.requests.MovieRoleRequest;
import com.innova.imdb.requests.MultimediaRequest;
import com.innova.imdb.responses.ActorNameResponse;

@Service
public class ActorService {

	@Autowired
	private ActorRepository actorRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private UserService userService;

	public Actor findById(Long actorId) {
		Actor actor= actorRepository.getById(actorId);
		if (actor==null) {
			throw new ActorNotFoundException();
		}
		return actor;
	}
	
	public Actor getActorById(Optional<Long> actorId) {
		return findById(actorId.get());
	}

/*
	public Actor insertActor(ActorCreateRequest newActorRequest) {
		Actor actor=new Actor();
		User user=userService.getUser(newActorRequest.getUserId());
		actor.setActorUser(user);
		actor.setName(newActorRequest.getName());
		actor.setBirthdate(newActorRequest.getBirthdate());
		actor.setHeight(newActorRequest.getHeight());
		actor.setPersonalInfo(newActorRequest.getPersonalInfo());
		for(MovieRoleRequest role : newActorRequest.getRoles()) {
			Role newRole=new Role();
			newRole.setActor(actor);
			Movie movie=movieRepository.getById(role.getMovieId());
			newRole.setMovie(movie);
			newRole.setRoleName(role.getRoleName());
			newRole.setDescription(role.getDescription());
			actor.addRoles(newRole);
		}
		Actor newActor=actorRepository.save(actor);
		user.setActor(newActor);
		userService.saveUser(user);
		return newActor;
	}

*/
	public Actor updateActor(Optional<Long> userId, Optional<Long> actorId, ActorUpdateRequest newActorRequest) {
		if (actorId.isEmpty() || userId.isEmpty()) {
			throw new ParameterControlException("Requires userId and actorId parameters");
		}
		Actor actor=getActorById(actorId);
		User user = userService.getUser(userId.get());
		if (actor==null) {
			throw new ActorNotFoundException();
		}
		if (user==null) {
			throw new UserNotFoundException();
		}
		if (!actor.getActorUser().equals(user)) {
			throw new InnovaImdbAuthenticationException("You have no autority to change this actor info");
		}
		actor.setName(newActorRequest.getName());
		actor.setBirthdate(newActorRequest.getBirthdate());
		actor.setHeight(newActorRequest.getHeight());
		actor.setPersonalInfo(newActorRequest.getPersonalInfo());
		actor.clearRoles();
		for(MovieRoleRequest role : newActorRequest.getRoles()) {
			Role newRole=new Role();
			newRole.setActor(actor);
			Movie movie=movieRepository.getById(role.getMovieId());
			if (movie==null) {
				throw new MovieNotFoundException("Check the movie id:" + role.getMovieId());
			}
			newRole.setMovie(movie);
			newRole.setRoleName(role.getRoleName());
			newRole.setDescription(role.getDescription());
			actor.addRoles(newRole);
		}
		actor.clearMultimedia();
		for(MultimediaRequest m : newActorRequest.getMultimedia()) {
			Multimedia newMultimedia= new Multimedia(); 
			newMultimedia.setActor(actor);
			newMultimedia.setMovie(null);
			newMultimedia.setType(Multimedia.Type.valueOf(m.getType()));
			newMultimedia.setLink(m.getLink());
			actor.addMultimedia(newMultimedia);
		}
		return actorRepository.save(actor);
	}

	public Set<ActorNameResponse> getActors() {
		List<Actor> actorList=actorRepository.findAll();
		return actorList.stream().map(a -> new ActorNameResponse(a)).collect(Collectors.toSet());
	}
}
