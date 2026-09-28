package Springproject.MovieBookingApp.Entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    private String description;
    private String Name;
    private String genre;
    private Integer duration;
    private String language;
    private LocalDate releaseDate;
    @OneToMany(mappedBy = "movie",fetch = FetchType.LAZY)
    private List<Show> show;
}
