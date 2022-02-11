package me.dcal.catalogue.service;


import me.dcal.catalogue.model.Game;
import me.dcal.catalogue.model.GameList;

public interface GameService {
	public GameList getListGame(String name,String genre,String platform,String publisher) ;
	
	public Game getGame(String res);
	

}
