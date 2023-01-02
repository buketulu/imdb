package com.innova.imdb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.innova.imdb.entities.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
	

}
