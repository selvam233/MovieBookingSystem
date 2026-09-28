package Springproject.MovieBookingApp.Repository;

import Springproject.MovieBookingApp.Entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByGenre(String genre);

    List<Movie> findByLanguage(String language);

    Optional<Movie> findByName(String name);
}