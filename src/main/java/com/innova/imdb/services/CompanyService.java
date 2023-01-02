package com.innova.imdb.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innova.imdb.entities.Company;
import com.innova.imdb.entities.User;
import com.innova.imdb.exceptions.CompanyNotFoundException;
import com.innova.imdb.exceptions.InnovaImdbAuthenticationException;
import com.innova.imdb.exceptions.ParameterControlException;
import com.innova.imdb.exceptions.UserNotFoundException;
import com.innova.imdb.repository.CompanyRepository;
import com.innova.imdb.repository.MovieRepository;
import com.innova.imdb.requests.CompanyRequest;
import com.innova.imdb.responses.CompanyResponse;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private UserService userService;

	public Company findById(Long companyId) {
		Company company= companyRepository.getById(companyId);
		if (company==null) {
			throw new CompanyNotFoundException();
		}
		return company;
	}
	
	public Company getCompanyById(Optional<Long> companyId) {
		return findById(companyId.get());
	}

	public Company updateCompany(Optional<Long> userId, Optional<Long> companyId, CompanyRequest newCompanyRequest) {
		if (companyId.isEmpty() || userId.isEmpty()) {
			throw new ParameterControlException("Requires userId and companyId parameters");
		}
		Company company=getCompanyById(companyId);
		User user = userService.getUser(userId.get());
		if (user==null) {
			throw new UserNotFoundException();
		}
		if (!company.getCompanyUser().equals(user)) {
			throw new InnovaImdbAuthenticationException("You have no autority to change this company info");
		}
		company.setName(newCompanyRequest.getName());
		return companyRepository.save(company);
	}

	public Set<CompanyResponse> getCompanies() {
		List<Company> companyList=companyRepository.findAll();
		return companyList.stream().map(a -> new CompanyResponse(a)).collect(Collectors.toSet());
	}
}
