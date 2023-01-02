package com.innova.imdb.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.innova.imdb.entities.Actor;
import com.innova.imdb.entities.Company;
import com.innova.imdb.entities.Crew;
import com.innova.imdb.entities.Movie;
import com.innova.imdb.entities.Multimedia;
import com.innova.imdb.entities.Role;
import com.innova.imdb.entities.User;
import com.innova.imdb.exceptions.ActorNotFoundException;
import com.innova.imdb.exceptions.CompanyNotFoundException;
import com.innova.imdb.exceptions.InnovaImdbAuthenticationException;
import com.innova.imdb.exceptions.MovieNotFoundException;
import com.innova.imdb.exceptions.ParameterControlException;
import com.innova.imdb.exceptions.UserNotFoundException;
import com.innova.imdb.repository.MovieRepository;
import com.innova.imdb.requests.CrewRequest;
import com.innova.imdb.requests.MovieCreateRequest;
import com.innova.imdb.requests.MovieSearchRequest;
import com.innova.imdb.requests.MovieUpdateRequest;
import com.innova.imdb.requests.MultimediaRequest;
import com.innova.imdb.requests.RoleRequest;
import com.innova.imdb.responses.MovieResponse;
import com.innova.imdb.responses.MovieTitleResponse;
import com.innova.imdb.search.MovieSpecification;

@Service
public class MovieService {

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private ActorService actorService;

	@Autowired 
	private CompanyService companyService;
	
	public Movie getMovieById(Long movieId) {
		Movie movie = movieRepository.getById(movieId);
		if (movie == null) {
			throw new MovieNotFoundException("MovieId:" + movieId);
		}
		return movie;
	}

	public List<MovieTitleResponse> getAllMoviesByCompany(Optional<Long> companyId) {
		List<MovieTitleResponse> movieList;
		if (companyId.isPresent()) {
			Company company = companyService.findById(companyId.get());
			movieList = movieRepository.findAllByCompany(company.getId());
			return movieList;
		} else {
			return null;
		}
	}

	public List<MovieTitleResponse> getMoviesPageByPage(Optional<Integer> pageNumber, Optional<Integer> total) {
		Integer pageNo = pageNumber.get();
		Integer totalEntry = total.get();
		if (pageNo == 1) {
		} else {
			pageNo = (pageNo - 1) * totalEntry + 1;
		}
		return movieRepository.getMoviesPageByPage(totalEntry, pageNo - 1);
	}

	public List<MovieResponse> findMoviesWithPredicate(MovieSearchRequest movieSearchRequest) {

		Specification<Movie> movieSpecification = new MovieSpecification();
		/*
		 * String movieTitle; String companyName; String actorName; String
		 * actorRoleName; String crewName; Date productionDate;
		 */
		boolean present = false;
		if (movieSearchRequest.getMovieTitle() != null && !movieSearchRequest.getMovieTitle().equals("")) {
			Specification<Movie> specification = MovieSpecification.hasTitleLike(movieSearchRequest.getMovieTitle());
			movieSpecification = specification;
			present = true;
		}
		if (movieSearchRequest.getActorName() != null && !movieSearchRequest.getActorName().equals("")) {
			Specification<Movie> specification = MovieSpecification.hasActorLike(movieSearchRequest.getActorName());
			if (present) {
				movieSpecification = movieSpecification.and(specification);
			} else {
				movieSpecification = specification;
				present = true;
			}
		}
		if (movieSearchRequest.getActorRoleName() != null && !movieSearchRequest.getActorRoleName().equals("")) {
			Specification<Movie> specification = MovieSpecification
					.hasActorRoleLike(movieSearchRequest.getActorRoleName());
			if (present) {
				movieSpecification = movieSpecification.and(specification);
			} else {
				movieSpecification = specification;
				present = true;
			}
		}

		if (movieSearchRequest.getCrewName() != null && !movieSearchRequest.getCrewName().equals("")) {
			Specification<Movie> specification = MovieSpecification.hasCrewLike(movieSearchRequest.getCrewName());
			if (present) {
				movieSpecification = movieSpecification.and(specification);
			} else {
				movieSpecification = specification;
				present = true;
			}
		}
		List<Movie> movieList = movieRepository.findAll(movieSpecification);
		List<MovieResponse> movieResponseList = new ArrayList<MovieResponse>();
		for (Movie movie : movieList) {
			movieResponseList.add(new MovieResponse(movie));
		}
		return movieResponseList;

	}

	public Movie insertMovie(Optional<Long> userId, MovieCreateRequest newMovieRequest) {
		boolean found = false;
		Movie movie = new Movie();
		if (userId.isEmpty()) {
			throw new ParameterControlException("Requires userId parameters");
		}
		User user = userService.getUser(userId.get());
		if (user==null) {
			throw new UserNotFoundException();
		}
		if (!user.isCompany() && !user.isActor()) {
			throw new InnovaImdbAuthenticationException("Only companies and actors can insert a movie!");
		}
		Company company= companyService.findById(newMovieRequest.getCompanyId());
		if (company == null) {
			throw new CompanyNotFoundException();
		}
		if (user.isCompany()) {
			if (user.getId() == company.getCompanyUser().getId()) {
				found = true;
			} else {
				throw new CompanyNotFoundException("Every company can enter their own movies");
			}
		}
		movie.setCompany(company);
		movie.setTitle(newMovieRequest.getTitle());
		movie.setProductionDate(newMovieRequest.getProductionDate());
		movie.setDescription(newMovieRequest.getDescription());

		for (RoleRequest role : newMovieRequest.getActors()) {
			Role newRole = new Role();
			newRole.setMovie(movie);
			newRole.setRoleName(role.getRoleName());
			newRole.setDescription(role.getDescription());
			Actor actor = actorService.findById(role.getActorId());
			if (actor == null) {
				throw new ActorNotFoundException();
			}
			if (!found && user.isActor() && actor.getActorUser().getId().equals(user.getId())) {
				found = true;
			}
			newRole.setActor(actor);
			movie.addActor(newRole);
		}
		if (user.isActor() && !found) {
			throw new ActorNotFoundException("If you have no role, you cannot enter this movie!");
		}
		for (CrewRequest crew : newMovieRequest.getCrew()) {
			Crew newCrew = new Crew();
			newCrew.setMovie(movie);
			newCrew.setName(crew.getName());
			newCrew.setPosition(Crew.Position.valueOf(crew.getPosition()));
			movie.addCrew(newCrew);
		}
		for(MultimediaRequest m : newMovieRequest.getMultimedia()) {
			Multimedia newMultimedia= new Multimedia(); 
			newMultimedia.setActor(null);
			newMultimedia.setMovie(movie);
			newMultimedia.setType(Multimedia.Type.valueOf(m.getType()));
			newMultimedia.setLink(m.getLink());
			movie.addMultimedia(newMultimedia);
		}
		return movieRepository.save(movie);
	}

	public Movie updateMovie(Optional<Long> userId, Optional<Long> movieId, MovieUpdateRequest newMovie) {
		if (movieId.isEmpty() || userId.isEmpty()) {
			throw new ParameterControlException("Requires userId and movieId parameters");
		}
		Optional<Movie> movieOpt = movieRepository.findById(movieId.get());
		if (movieOpt.isPresent()) {
			Movie movie = movieOpt.get();
			User user = userService.getUser(userId.get());
			if (user==null) {
				throw new UserNotFoundException();
			}
			//Movies can be changed by only their company
			if (!movie.getCompany().getCompanyUser().getId().equals(user.getId())) {
				throw new InnovaImdbAuthenticationException("You have no autority to change this movie info");
			}
			movie.setTitle(newMovie.getTitle());
			movie.setProductionDate(newMovie.getProductionDate());
			movie.setDescription(newMovie.getDescription());
			movie.clearActors();
			movie.clearCrew();
			movie.clearMultimedia();
			for (RoleRequest role : newMovie.getActors()) {
				Role newRole = new Role();
				newRole.setMovie(movie);
				newRole.setRoleName(role.getRoleName());
				newRole.setDescription(role.getDescription());
				Actor actor = actorService.findById(role.getActorId());
				if (actor == null) {
					throw new ActorNotFoundException();
				}
				newRole.setActor(actor);
				movie.addActor(newRole);
			}
			for (CrewRequest crew : newMovie.getCrew()) {
				Crew newCrew = new Crew();
				newCrew.setMovie(movie);
				newCrew.setName(crew.getName());
				newCrew.setPosition(Crew.Position.valueOf(crew.getPosition()));
				movie.addCrew(newCrew);
			}
			for(MultimediaRequest m : newMovie.getMultimedia()) {
				Multimedia newMultimedia= new Multimedia(); 
				newMultimedia.setActor(null);
				newMultimedia.setMovie(movie);
				newMultimedia.setType(Multimedia.Type.valueOf(m.getType()));
				newMultimedia.setLink(m.getLink());
				movie.addMultimedia(newMultimedia);
			}
			movieRepository.save(movie);
			return movie;
		}
		return null;
	}

	public List<MovieTitleResponse> deleteMovie(Optional<Long> userId, Optional<Long> movieId) {
		if (userId.isEmpty() || movieId.isEmpty()) {
			throw new ParameterControlException("Parameters are userId and movieId");
		}
		Optional<Movie> movie = movieRepository.findById(movieId.get());
		if (movie.isPresent()) {
			Company company=movie.get().getCompany();
			if (company==null) {
				throw new CompanyNotFoundException("Check the movie record");
			}
			User user = userService.getUser(userId.get());
			if (user==null) {
				throw new UserNotFoundException();
			}
			if (!company.getCompanyUser().getId().equals(user.getId())) {
				throw new InnovaImdbAuthenticationException("You have no autority to change this movie info");
			}
			movieRepository.deleteById(movieId.get());
			return movieRepository.findAllByCompany(company.getId());
		}
		return null;
	}
	

}
