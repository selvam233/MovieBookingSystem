package Springproject.MovieBookingApp.Controller;

import Springproject.MovieBookingApp.Dto.TheatreDto;
import Springproject.MovieBookingApp.Entity.Theatre;
import Springproject.MovieBookingApp.Service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theatre")
public class TheatreController {

    @Autowired
    private TheatreService theatreService;

    // CREATE
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Theatre> addTheatre(@RequestBody TheatreDto theatreDto) {
        return ResponseEntity.ok(theatreService.addTheatre(theatreDto));
    }

    // READ BY LOCATION
    @GetMapping("/location/{location}")
    public ResponseEntity<List<Theatre>> getTheatresByLocation(@PathVariable String location) {
        return ResponseEntity.ok(theatreService.getTheatreByLocation(location));
    }

    // UPDATE
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Theatre> updateTheatre(
            @PathVariable Long id,
            @RequestBody TheatreDto theatreDto) {

        return ResponseEntity.ok(theatreService.updateTheatre(id, theatreDto));
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTheatre(@PathVariable Long id) {
        theatreService.deleteTheatre(id);
        return ResponseEntity.ok().build();
    }
}

