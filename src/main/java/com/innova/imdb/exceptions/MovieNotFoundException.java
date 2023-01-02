package com.innova.imdb.exceptions;

public class MovieNotFoundException extends RuntimeException {

	public MovieNotFoundException() {
		super();
	}

	public MovieNotFoundException(String message) {
		super(message);
	}

	
}
