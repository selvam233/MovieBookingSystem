package Springproject.MovieBookingApp.Dto;

import lombok.Data;

@Data
public class TheatreDto {

    private Long id;
    private String theatreName;
    private String theatreLocation;
    private Integer theatreCapacity;
    private String screenType;
}