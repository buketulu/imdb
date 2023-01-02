package com.innova.imdb.exceptions;

public class DuplicateUserNameException extends RuntimeException{

	public DuplicateUserNameException() {
		super();
	}

	public DuplicateUserNameException(String userName) {
		super(userName +" is not available. Please choose another user name!");
	}


}
