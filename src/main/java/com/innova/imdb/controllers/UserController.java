package com.innova.imdb.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.innova.imdb.entities.User;
import com.innova.imdb.exceptions.UserNotFoundException;
import com.innova.imdb.requests.UserActorCreateRequest;
import com.innova.imdb.requests.UserCompanyCreateRequest;
import com.innova.imdb.requests.UserCreateRequest;
import com.innova.imdb.requests.UserUpdateRequest;
import com.innova.imdb.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;

	
	@GetMapping
	public List<User> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@PostMapping
	public User insertUser(@RequestBody UserCreateRequest user) {
		return userService.insertUser(user);
	}

	@PostMapping("/actor")
	public User insertUser(@RequestBody UserActorCreateRequest user) {
		return userService.insertActorUser(user);
	}

	@PostMapping("/company")
	public User insertUser(@RequestBody UserCompanyCreateRequest user) {
		return userService.insertCompanyUser(user);
	}

	@GetMapping("/{userId}")
	public User getUser(@PathVariable Long userId) {
		User user= userService.getUser(userId);
		if (user==null) {
			throw new UserNotFoundException("User not found:" +userId);
		}
		return user;
	}

	@PutMapping
	public User updateUser(@RequestParam Long userId, @RequestBody UserUpdateRequest newUser) {
		return userService.updateUser(userId, newUser);
	}

	@DeleteMapping
	public List<User> deleteUser(@RequestParam Long userId) {
		return userService.deleteUser(userId);
	}

	
}


