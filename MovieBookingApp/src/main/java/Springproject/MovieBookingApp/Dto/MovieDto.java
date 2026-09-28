package Springproject.MovieBookingApp.Dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class MovieDto {
    private String description;
    private String name;
    private String genre;
    private Integer duration;
    private String language;
    private LocalDate releaseDate;
}
