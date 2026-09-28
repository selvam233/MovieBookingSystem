package Springproject.MovieBookingApp.Repository;

import Springproject.MovieBookingApp.Entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Showrepository extends JpaRepository<Show, Long> {

    List<Show> findByMovie_Name(String name);

    List<Show> findByTheatre_TheatreName(String theatreName);

}