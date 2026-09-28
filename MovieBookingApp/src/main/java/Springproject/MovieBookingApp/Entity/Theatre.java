package Springproject.MovieBookingApp.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Theatre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String theatreName;

    private String theatreLocation;

    private Integer theatreCapacity;

    private String screenType;

    @OneToMany(mappedBy = "theatre", fetch = FetchType.LAZY)
    private List<Show> shows;
}