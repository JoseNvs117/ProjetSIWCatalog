package me.dcal.catalogue.model;

import java.util.List;

public class Movie {
	public String name;
	public List<String> thumbnail;
	public List<String> director;
	public List<String> starring;
	
	public String toString() {
		return  "name : " + name + "\n" +
				director.toString() + "\n" +
				thumbnail.toString() + "\n" +
				starring.toString() + "\n";
	}
}