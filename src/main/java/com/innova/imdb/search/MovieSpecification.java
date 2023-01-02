package com.innova.imdb.search;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.innova.imdb.entities.Actor;
import com.innova.imdb.entities.Crew;
import com.innova.imdb.entities.Movie;
import com.innova.imdb.entities.Role;

import lombok.Data;

@Data
public class MovieSpecification implements Specification {

	public static Specification<Movie> hasTitleLike(String title) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.<String>get("title"), "%" + title + "%");
	}

	public static Specification<Movie> hasActorLike(String actorName) {
		return (root, query, criteriaBuilder) -> {
			Join<Role, Movie> roles = root.join("actors");
			Join<Actor, Role> actors = roles.join("actor");

			return criteriaBuilder.like(actors.<String>get("name"), '%'+ actorName + '%');
		};
	}

	public static Specification<Movie> hasActorRoleLike(String roleName) {
		return (root, query, criteriaBuilder) -> {
			Join<Role, Movie> roles = root.join("actors");
			
			return criteriaBuilder.like(roles.<String>get("roleName"), '%' + roleName + '%');
		};
	}

	public static Specification<Movie> hasCrewLike(String crewName) {
		return (root, query, criteriaBuilder) -> {
			Join<Movie, Crew> crew = root.join("crew");

			return criteriaBuilder.like(crew.<String>get("name"), '%' + crewName + '%');
		};
	}

	public MovieSpecification() {
		super();
	}

	@Override
	public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
		// TODO Auto-generated method stub
		return null;
	}

}