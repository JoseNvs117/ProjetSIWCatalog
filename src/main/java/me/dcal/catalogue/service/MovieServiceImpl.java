package me.dcal.catalogue.service;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.springframework.stereotype.Service;

import me.dcal.catalogue.model.DataTypeEnumMovie;
import me.dcal.catalogue.model.Element;
import me.dcal.catalogue.model.Movie;
import me.dcal.catalogue.model.MovieList;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {
	
	
	public MovieList getListMovie(String name, String director, String starring) {
		ResultSet rs  = movieQuery(name, director, starring, DataTypeEnumMovie.EMPTY);
		return resultToListGame(rs);
	}
	
	public Movie getMovie(String res) {
		Movie movie = new Movie();
		movie.name = resultToList(singleMovieQuery(DataTypeEnumMovie.NAME, res), DataTypeEnumMovie.NAME).get(0);
		movie.name = movie.name.substring(0,movie.name.length()-3);
		movie.director = resultToList(singleMovieQuery(DataTypeEnumMovie.DIRECTOR, res), DataTypeEnumMovie.DIRECTOR);
		movie.starring = resultToList(singleMovieQuery(DataTypeEnumMovie.STARRING, res), DataTypeEnumMovie.STARRING);
		//movie.thumbnail = resultToList(singleMovieQuery(DataTypeEnumMovie.THUMBNAIL, res), DataTypeEnumMovie.THUMBNAIL).get(0);
		return movie;
	}
	
	private ResultSet movieQuery(String name, String director, String starring, DataTypeEnumMovie dataType) {
		String newname = capitalizeString(name);
		String newDirector = capitalizeString(director);
		String newStarring = capitalizeString(starring);
		
		String nameQ = (name==null||name.equals(""))?"":("FILTER contains(xsd:string(?Name),'"+newname.replace(" ", "_")+"').\n");
		String directorQ = (director==null||director.equals(""))?"":("FILTER contains(xsd:string(?Director),'"+newDirector.replace(" ", "_")+"').\n");
		String starringQ = (starring==null||starring.equals(""))?"":("FILTER contains(xsd:string(?Starring),'"+newStarring.replace(" ", "_")+"').\n");
		
		String szEndpoint = "https://dbpedia.org/sparql";
		String szQuery = ""+
				"PREFIX dbo:<http://dbpedia.org/ontology/>\n" + 
				"PREFIX dbp:<http://dbpedia.org/property/>\n" +  
				"PREFIX foaf:<http://xmlns.com/foaf/0.1/>\n" + 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
				"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
				"select distinct ?vg " +
				dataType.getQuery() + 
				" where{\n" + 
				"?vg rdf:type dbo:Film.\n" + 
				"?vg foaf:name ?Name.\n" + 
				"?vg dbo:director ?Director.\n" + 
				"?vg dbo:starring ?Starring.\n" + 
				 nameQ +
				 directorQ +
				 starringQ +
				"}" +
				"LIMIT 100";
		
		Query query = QueryFactory.create(szQuery);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(szEndpoint, query);
		System.out.println(szQuery);
        return qexec.execSelect();
		
	}

	private ResultSet singleMovieQuery(DataTypeEnumMovie dataType, String resource) {		
		String szEndpoint = "https://dbpedia.org/sparql";
		String szQuery = ""+
				"PREFIX dbo:<http://dbpedia.org/ontology/>\n" + 
				"PREFIX dbp:<http://dbpedia.org/property/>\n" + 
				"PREFIX dbr:<http://dbpedia.org/resource/>\n" + 
				"PREFIX foaf:<http://xmlns.com/foaf/0.1/>\n" + 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
				"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
				"select distinct " +
				dataType.getQuery() + 
				" where{\n" + 
				"<" + resource + "> foaf:name ?Name.\n" + 
				"<" + resource + "> dbo:director ?Director.\n" + 
				"<" + resource + "> dbo:starring ?Starring.\n" + 
				"}" +
				"LIMIT 30";
		Query query = QueryFactory.create(szQuery);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(szEndpoint, query);
		System.out.println(szQuery);
        return qexec.execSelect();
		
	}
	
	private ArrayList<String> resultToList(ResultSet rs,DataTypeEnumMovie dtype){
		ArrayList<String> list = new ArrayList<>();
		 while (rs.hasNext()) {
	        	QuerySolution qs= rs.next();
	        	list.add(Element.urlToName(qs.get(dtype.getQuery()).toString()));
		 }
		 return list; 
	}

	private MovieList resultToListGame(ResultSet rs){
		MovieList movies = new MovieList();
		while (rs.hasNext()) {
	       	QuerySolution qs= rs.next();
	       	movies.getMovieList().add(Element.urlToElem(qs.getResource("?vg").toString()));
		}
		return movies;
	}

	public static String capitalizeString(String string) {
		if(string != null){
			char[] chars = string.toLowerCase().toCharArray();
			boolean found = false;
			for (int i = 0; i < chars.length; i++) {
			if (!found && Character.isLetter(chars[i])) {
				chars[i] = Character.toUpperCase(chars[i]);
				found = true;
			} else if (Character.isWhitespace(chars[i]) || chars[i]=='.' || chars[i]=='\'' || chars[i]=='_') { 
				found = false;
			}
			}
			return String.valueOf(chars);
		}else return null;
	  }

}
