package com.innova.imdb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.innova.imdb.entities.User;


public interface UserRepository extends JpaRepository<User, Long> {

	User findByUserName(String userName);

}
