package com.innova.imdb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.innova.imdb.entities.Actor;

public interface ActorRepository extends JpaRepository<Actor, Long> {
	

}
