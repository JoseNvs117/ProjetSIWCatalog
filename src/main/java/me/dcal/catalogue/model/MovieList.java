package me.dcal.catalogue.model;

import java.util.ArrayList;
import java.util.List;

public class MovieList {
    
	public List<Element> movieList;

	public MovieList() {
		this.movieList=new ArrayList<>();
	}
	public String toString() {
		return "MovieList :\n" + movieList.toString();
	}
	public List<Element> getMovieList() {
		return movieList;
	}
	public void setMovieList(List<Element> movies) {
		this.movieList = movies;
	}

}
