package com.innova.imdb.requests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.innova.imdb.entities.Movie;

import lombok.Data;

@Data
public class MovieRequest {
	String id;
}
