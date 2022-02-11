package me.dcal.catalogue.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import me.dcal.catalogue.service.GameService;
import me.dcal.catalogue.service.MovieService;

import java.io.IOException;
import java.text.ParseException;


@Controller
public class Accueil {
	
	@Autowired 
	GameService gameService;
    @Autowired
    MovieService movieService;

    @GetMapping("/")
    public String accueil(Model model) throws ParseException, IOException {
        return "home";
    }
    
    @GetMapping("/form")
    public String form(Model model) throws ParseException, IOException {
        return "form";
    }
    
    @GetMapping("/gamelist")
    public String gamelist(Model model
    		, @RequestParam(required = false) String name
    		, @RequestParam(required = false) String genre
    		, @RequestParam(required = false) String platform
    		, @RequestParam(required = false) String publisher
    ) 
    {
    	model.addAttribute("games", gameService.getListGame(
    			name, genre, platform,publisher
    			).getGameList());
        return "list-game";
    }
    
    @GetMapping("/gameview")
    public String gameveiw(Model model, @RequestParam String url) {
    	model.addAttribute("game", gameService.getGame(url));
        return "idGame";
    }

    @GetMapping("/formMovie")
    public String formMovie(Model model) throws ParseException, IOException {
        return "formMovie";
    }
    
    @GetMapping("/movielist")
    public String movielist(Model model
    		, @RequestParam(required = false) String name
    		, @RequestParam(required = false) String director
    		, @RequestParam(required = false) String starring
    ) 
    {
    	model.addAttribute("movies", movieService.getListMovie(name, director, starring).getMovieList());
        return "list-movie";
    }
    
    @GetMapping("/movieview")
    public String movieview(Model model, @RequestParam String url) {
    	model.addAttribute("movie", movieService.getMovie(url));
        return "idMovie";
    }
   
}