package com.innova.imdb.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innova.imdb.entities.Movie;
import com.innova.imdb.requests.MovieCreateRequest;
import com.innova.imdb.requests.MovieSearchRequest;
import com.innova.imdb.requests.MovieUpdateRequest;
import com.innova.imdb.responses.MovieResponse;
import com.innova.imdb.responses.MovieTitleResponse;
import com.innova.imdb.services.MovieService;

@RestController
@RequestMapping("/movies")
public class MovieController {

	@Autowired
	private MovieService movieService;

	@GetMapping("/company/{companyId}")
	public List<MovieTitleResponse> getAllMoviesByCompany(@PathVariable Optional<Long> companyId) {
		return movieService.getAllMoviesByCompany(companyId);
	}

	@GetMapping("/search")
	public List<MovieResponse> dynamicSearch(@RequestBody MovieSearchRequest movieSearchRequest) {
		return movieService.findMoviesWithPredicate(movieSearchRequest);
	}

	@GetMapping
	public List<MovieTitleResponse> getLatest10Movies() {
		return movieService.getMoviesPageByPage(Optional.of(Integer.valueOf(1)), Optional.of(Integer.valueOf(10)));
	}

	@GetMapping("/page")
	public List<MovieTitleResponse> getMoviesPageByPage(@RequestParam Optional<Integer> page,
			@RequestParam Optional<Integer> total) {
		return movieService.getMoviesPageByPage(page, total);
	}

	@GetMapping("/{movieId}")
	public MovieResponse getMovie(@PathVariable Long movieId) {
		Movie movie = movieService.getMovieById(movieId);
		return new MovieResponse(movie);
	}

	@PostMapping
	public MovieResponse insertMovie(@RequestParam Optional<Long> userId,
			@RequestBody MovieCreateRequest newMovieRequest) throws Exception {
		Movie movie = movieService.insertMovie(userId, newMovieRequest);
		return new MovieResponse(movie);
	}

	@PutMapping
	public MovieResponse updateMovie(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> movieId,
			@RequestBody MovieUpdateRequest newMovie) {
		Movie movie = movieService.updateMovie(userId, movieId, newMovie);
		return new MovieResponse(movie);
	}

	// Delete movie by company
	// and return the movie set of company
	@DeleteMapping
	public List<MovieTitleResponse> deleteMovie(@RequestParam Optional<Long> userId,
			@RequestParam Optional<Long> movieId) {
		return movieService.deleteMovie(userId, movieId);
	}

}
