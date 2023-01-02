package com.innova.imdb.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.innova.imdb.responses.CompanyResponse;
import com.innova.imdb.services.CompanyService;

@RestController
@RequestMapping("/companies")
public class CompanyController {
	
	@Autowired
	private CompanyService companyService;

	
	@GetMapping
	public Set<CompanyResponse> getAllCompanies(){
		return companyService.getCompanies();
	}
	
}


