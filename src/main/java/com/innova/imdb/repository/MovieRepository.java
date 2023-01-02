package com.innova.imdb.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.innova.imdb.entities.Movie;
import com.innova.imdb.responses.MovieResponse;
import com.innova.imdb.responses.MovieTitleResponse;

public interface MovieRepository extends JpaRepository<Movie, Long> {
	
	@Query(nativeQuery = true)
	List<MovieTitleResponse> findAllByCompany(@Param("companyId") Long companyId);
	
	@Query(nativeQuery = true)
	List<MovieTitleResponse> getMoviesPageByPage(int total, int pageNo);
	
	List<Movie> findAll(Specification movieSpecification);

	

}
