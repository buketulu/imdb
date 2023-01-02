package com.innova.imdb.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innova.imdb.entities.Actor;
import com.innova.imdb.entities.Company;
import com.innova.imdb.entities.Movie;
import com.innova.imdb.entities.Multimedia;
import com.innova.imdb.entities.Role;
import com.innova.imdb.entities.User;
import com.innova.imdb.exceptions.DuplicateUserNameException;
import com.innova.imdb.repository.ActorRepository;
import com.innova.imdb.repository.CompanyRepository;
import com.innova.imdb.repository.MovieRepository;
import com.innova.imdb.repository.UserRepository;
import com.innova.imdb.requests.MovieRoleRequest;
import com.innova.imdb.requests.MultimediaRequest;
import com.innova.imdb.requests.UserActorCreateRequest;
import com.innova.imdb.requests.UserCompanyCreateRequest;
import com.innova.imdb.requests.UserCreateRequest;
import com.innova.imdb.requests.UserUpdateRequest;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	MovieRepository movieRepository;

	@Autowired
	ActorRepository actorRepository;

	@Autowired
	CompanyRepository companyRepository;

	
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User getUser(Long userId) {
		return userRepository.findById(userId).orElse(null);
	}
	
	public User getUserById(Long userId) {
		return userRepository.getById(userId);
	}

	public User insertUser(UserCreateRequest user) {
		if (userRepository.findByUserName(user.getUserName()) != null) {
			throw new DuplicateUserNameException(user.getUserName());
		}
		User newUser = new User();
		newUser.setUserName(user.getUserName());
		newUser.setPassword(user.getPassword());
		newUser.setCustomerType(User.CustomerType.valueOf(user.getCustomerType()));
		return userRepository.save(newUser);
	}

	public User updateUser(Long userId, UserUpdateRequest newUser) {
		Optional<User> oldUser = userRepository.findById(userId);
		if (!oldUser.isPresent()) {
			return null;
		}
		User dbUser = oldUser.get();
		dbUser.setPassword(newUser.getPassword());
		userRepository.save(dbUser);
		return userRepository.findById(userId).orElse(null);
	}

	// DANGEREOUS
	public List<User> deleteUser(Long userId) {
		Optional<User> dbUser = userRepository.findById(userId);
		if (!dbUser.isPresent()) {
			System.out.println("User had been deleted!");
		}
		userRepository.deleteById(userId);
		return userRepository.findAll();
	}

	public void saveUser(User user) {
		userRepository.save(user);

	}

	public User insertActorUser(UserActorCreateRequest actorUser) {
		if (userRepository.findByUserName(actorUser.getUserName()) != null) {
			throw new DuplicateUserNameException(actorUser.getUserName());
		}
		User newUser = new User();
		newUser.setUserName(actorUser.getUserName());
		newUser.setPassword(actorUser.getPassword());
		newUser.setCustomerType(User.CustomerType.valueOf(actorUser.getCustomerType()));
		User savedUser = userRepository.save(newUser);
		Actor actor = new Actor();
		actor.setActorUser(savedUser);
		actor.setName(actorUser.getName());
		actor.setBirthdate(actorUser.getBirthdate());
		actor.setHeight(actorUser.getHeight());
		actor.setPersonalInfo(actorUser.getPersonalInfo());
		if (actorUser.getRoles() != null) {
			for (MovieRoleRequest role : actorUser.getRoles()) {
				Role newRole = new Role();
				newRole.setActor(actor);
				Movie movie = movieRepository.getById(role.getMovieId());
				newRole.setMovie(movie);
				newRole.setRoleName(role.getRoleName());
				newRole.setDescription(role.getDescription());
				actor.addRoles(newRole);
			}
		}
		if (actorUser.getMultimedia() != null) {
			for (MultimediaRequest multiMedia : actorUser.getMultimedia()) {
				Multimedia newMultimedia = new Multimedia();
				newMultimedia.setActor(actor);
				newMultimedia.setLink(multiMedia.getLink());
				newMultimedia.setType(Multimedia.Type.valueOf(multiMedia.getType()));
				actor.addMultimedia(newMultimedia);
			}
		}
		actorRepository.save(actor);
		return savedUser;
	}

	public User insertCompanyUser(UserCompanyCreateRequest companyUser) {
		if (userRepository.findByUserName(companyUser.getUserName()) != null) {
			throw new DuplicateUserNameException(companyUser.getUserName());
		}
		User newUser = new User();
		newUser.setUserName(companyUser.getUserName());
		newUser.setPassword(companyUser.getPassword());
		newUser.setCustomerType(User.CustomerType.valueOf(companyUser.getCustomerType()));
		User savedUser = userRepository.save(newUser);
		Company company = new Company();
		company.setCompanyUser(savedUser);
		company.setName(companyUser.getName());
		companyRepository.save(company);
		return savedUser;
	}

}
