package com.innova.imdb.responses;

import com.innova.imdb.entities.Company;

import lombok.Data;

@Data
public class CompanyResponse {
	Long id;
	String name;
	
	public CompanyResponse(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	

	public CompanyResponse(Company company) {
		super();
		this.id = company.getId();
		this.name = company.getName();

	}
}
