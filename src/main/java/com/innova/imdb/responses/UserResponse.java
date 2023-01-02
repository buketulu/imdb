package com.innova.imdb.responses;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import com.innova.imdb.entities.Role;
import com.innova.imdb.entities.User.CustomerType;

import lombok.Data;

@Data
public class UserResponse {
	Long id;
	String userName;
	String password;

	String customerType;

	public UserResponse(Long id, String userName, String password, String customerType) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.customerType = customerType;
	}

    

	
    
	
	
	
	

}
