package Springproject.MovieBookingApp.Service;

import Springproject.MovieBookingApp.Dto.MovieDto;
import Springproject.MovieBookingApp.Entity.Movie;
import Springproject.MovieBookingApp.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Movieservice {

    @Autowired
    private MovieRepository movieRepository;

    public Movie addMovie(MovieDto movieDTO){
        Movie movie = new Movie();
        movie.setName(movieDTO.getName());
        movie.setDescription(movieDTO.getDescription());
        movie.setGenre(movieDTO.getGenre());
        movie.setReleaseDate(movieDTO.getReleaseDate());
        movie.setDuration(movieDTO.getDuration());
        movie.setLanguage(movieDTO.getLanguage());

        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies(){
        return movieRepository.findAll();
    }

    public List<Movie> getMoviesByGenre(String genre){
        List<Movie> movies = movieRepository.findByGenre(genre);

        if(movies.isEmpty()){
            throw new RuntimeException("No Movies Found with this Genre " + genre);
        }
        return movies;
    }

    public List<Movie> getMoviesByLanguage(String language){
        List<Movie> movies = movieRepository.findByLanguage(language);

        if(movies.isEmpty()){
            throw new RuntimeException("No Movies Found with this language " + language);
        }
        return movies;
    }

    public Movie getMoviesByTitle(String title){
        return movieRepository.findByName(title)
                .orElseThrow(() -> new RuntimeException("No Movies Found with this title " + title));
    }

    public Movie updateMovie(Long id, MovieDto movieDTO){
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No Movie Found for this id " + id));

        movie.setName(movieDTO.getName());
        movie.setDescription(movieDTO.getDescription());
        movie.setGenre(movieDTO.getGenre());
        movie.setReleaseDate(movieDTO.getReleaseDate());
        movie.setDuration(movieDTO.getDuration());
        movie.setLanguage(movieDTO.getLanguage());

        return movieRepository.save(movie);
    }

    public void deleteMovie(Long id){
        movieRepository.deleteById(id);
    }
}