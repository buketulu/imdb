package com.innova.imdb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.innova.imdb.entities.Actor;
import com.innova.imdb.entities.Movie;
import com.innova.imdb.entities.Role;
import com.innova.imdb.entities.User;
import com.innova.imdb.entities.User.CustomerType;
import com.innova.imdb.repository.UserRepository;
import com.innova.imdb.services.UserService;

@SpringBootApplication
public class ImdbApplication {

	@Autowired
	UserRepository userRepository;

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(ImdbApplication.class, args);
		System.out.println("deneme");
	}

}
