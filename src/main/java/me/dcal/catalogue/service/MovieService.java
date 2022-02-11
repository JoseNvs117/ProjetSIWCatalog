package me.dcal.catalogue.service;

import me.dcal.catalogue.model.Movie;
import me.dcal.catalogue.model.MovieList;

public interface MovieService {

	public MovieList getListMovie(String name, String director, String starring) ;
	
	public Movie getMovie(String res);
	

}
