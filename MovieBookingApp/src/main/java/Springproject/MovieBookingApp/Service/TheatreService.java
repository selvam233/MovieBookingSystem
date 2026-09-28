package Springproject.MovieBookingApp.Service;

import Springproject.MovieBookingApp.Dto.TheatreDto;
import Springproject.MovieBookingApp.Entity.Theatre;
import Springproject.MovieBookingApp.Repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheatreService {

    @Autowired
    private TheatreRepository theatreRepository;


    public Theatre addTheatre(TheatreDto theatreDto) {

        Theatre theatre = new Theatre();

        theatre.setTheatreName(theatreDto.getTheatreName());
        theatre.setTheatreLocation(theatreDto.getTheatreLocation());
        theatre.setTheatreCapacity(theatreDto.getTheatreCapacity());
        theatre.setScreenType(theatreDto.getScreenType());

        return theatreRepository.save(theatre);
    }


    public List<Theatre> getTheatreByLocation(String location) {

        List<Theatre> theatres = theatreRepository.findByTheatreLocation(location);

        if (theatres.isEmpty()) {
            throw new RuntimeException("No theatres found for location: " + location);
        }

        return theatres;
    }

    public Theatre updateTheatre(Long theatreId, TheatreDto theatreDto) {

        Theatre theatre = theatreRepository.findById(theatreId)
                .orElseThrow(() -> new RuntimeException("No theatre found with id: " + theatreId));

        theatre.setTheatreName(theatreDto.getTheatreName());
        theatre.setTheatreLocation(theatreDto.getTheatreLocation());
        theatre.setTheatreCapacity(theatreDto.getTheatreCapacity());
        theatre.setScreenType(theatreDto.getScreenType());

        return theatreRepository.save(theatre);
    }


    public void deleteTheatre(Long theatreId) {
        theatreRepository.deleteById(theatreId);
    }
}