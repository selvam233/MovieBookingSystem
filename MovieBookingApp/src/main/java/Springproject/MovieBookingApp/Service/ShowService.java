package Springproject.MovieBookingApp.Service;

import Springproject.MovieBookingApp.Dto.ShowDto;
import Springproject.MovieBookingApp.Entity.Movie;
import Springproject.MovieBookingApp.Entity.Show;
import Springproject.MovieBookingApp.Entity.Theatre;
import Springproject.MovieBookingApp.Repository.Bookingrepository;
import Springproject.MovieBookingApp.Repository.MovieRepository;
import Springproject.MovieBookingApp.Repository.Showrepository;
import Springproject.MovieBookingApp.Repository.TheatreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShowService {

    private final Showrepository showrepository;
    private final MovieRepository movieRepository;
    private final TheatreRepository theatreRepository;
    private final Bookingrepository bookingRepository;

    public ShowService(Showrepository showrepository,
                       MovieRepository movieRepository,
                       TheatreRepository theatreRepository,
                       Bookingrepository bookingRepository) {
        this.showrepository = showrepository;
        this.movieRepository = movieRepository;
        this.theatreRepository = theatreRepository;
        this.bookingRepository = bookingRepository;
    }


    @Transactional
    public Show createShow(ShowDto showDto) {
        Movie movie = movieRepository.findById(showDto.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        Theatre theatre = theatreRepository.findById(showDto.getTheatreId())
                .orElseThrow(() -> new RuntimeException("Theatre not found"));

        Show show = new Show();
        show.setMovie(movie);
        show.setTheatre(theatre);
        show.setShowTime(showDto.getShowTime());
        show.setPrice(showDto.getPrice());

        return showrepository.save(show);
    }

    public List<Show> getAllShows() {
        return showrepository.findAll();
    }


    public List<Show> getShowsByMovieName(String movieName) {
        return showrepository.findByMovie_Name(movieName);
    }


    public List<Show> getShowsByTheatreName(String theatreName) {
        return showrepository.findByTheatre_TheatreName(theatreName);
    }


    @Transactional
    public Show updateShow(Long id, ShowDto dto) {
        Show show = showrepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Show not found"));

        Movie movie = movieRepository.findById(dto.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        Theatre theatre = theatreRepository.findById(dto.getTheatreId())
                .orElseThrow(() -> new RuntimeException("Theatre not found"));

        show.setMovie(movie);
        show.setTheatre(theatre);
        show.setShowTime(dto.getShowTime());
        show.setPrice(dto.getPrice());

        return showrepository.save(show);
    }


    @Transactional
    public void deleteShow(Long id) {
        if (!showrepository.existsById(id)) {
            throw new RuntimeException("Show not found with id: " + id);
        }

        if (!bookingRepository.findByShowId(id).isEmpty()) {
            throw new RuntimeException("Cannot delete show " + id + " because it has existing bookings");
        }

        showrepository.deleteById(id);
    }
}