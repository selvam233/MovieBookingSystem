package Springproject.MovieBookingApp.Controller;

import Springproject.MovieBookingApp.Dto.ShowDto;
import Springproject.MovieBookingApp.Entity.Show;
import Springproject.MovieBookingApp.Service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/show")
public class ShowController {

    @Autowired
    private ShowService showService;

    @PostMapping("/createshow")
    public ResponseEntity<Show> createShow(@RequestBody ShowDto dto) {
        return ResponseEntity.ok(showService.createShow(dto));
    }

    @GetMapping("/getallshows")
    public ResponseEntity<List<Show>> getAllShows() {
        return ResponseEntity.ok(showService.getAllShows());
    }

    @GetMapping("/bymovie")
    public ResponseEntity<List<Show>> getByMovie(@RequestParam String movie) {
        return ResponseEntity.ok(showService.getShowsByMovieName(movie));
    }

    @GetMapping("/bytheatre")
    public ResponseEntity<List<Show>> getByTheatre(@RequestParam String theatre) {
        return ResponseEntity.ok(showService.getShowsByTheatreName(theatre));
    }

    @PutMapping("/updateshow/{id}")
    public ResponseEntity<Show> updateShow(@PathVariable Long id,
                                           @RequestBody ShowDto dto) {
        return ResponseEntity.ok(showService.updateShow(id, dto));
    }

    @DeleteMapping("/deleteshow/{id}")
    public ResponseEntity<String> deleteShow(@PathVariable Long id) {
        showService.deleteShow(id);
        return ResponseEntity.ok("Show deleted successfully");
    }
}