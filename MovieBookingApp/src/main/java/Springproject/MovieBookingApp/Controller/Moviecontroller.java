package Springproject.MovieBookingApp.Controller;

import Springproject.MovieBookingApp.Dto.MovieDto;
import Springproject.MovieBookingApp.Entity.Movie;
import Springproject.MovieBookingApp.Service.Movieservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class Moviecontroller {

    @Autowired
    private Movieservice movieservice;

    @PostMapping("/add")
    public Movie addMovie(@RequestBody MovieDto movieDto){
        return movieservice.addMovie(movieDto);
    }

    @GetMapping("/all")
    public List<Movie> getAllMovies(){
        return movieservice.getAllMovies();
    }

    @GetMapping("/genre/{genre}")
    public List<Movie> getMoviesByGenre(@PathVariable String genre){
        return movieservice.getMoviesByGenre(genre);
    }

    @GetMapping("/language/{language}")
    public List<Movie> getMoviesByLanguage(@PathVariable String language){
        return movieservice.getMoviesByLanguage(language);
    }

    @GetMapping("/title/{title}")
    public Movie getMoviesByTitle(@PathVariable String title){
        return movieservice.getMoviesByTitle(title);
    }

    @PutMapping("/update/{id}")
    public Movie updateMovie(@PathVariable Long id, @RequestBody MovieDto movieDto){
        return movieservice.updateMovie(id, movieDto);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Long id){
        movieservice.deleteMovie(id);
        return "Movie deleted successfully";
    }
}