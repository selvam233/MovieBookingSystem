package Springproject.MovieBookingApp.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShowDto {
    private LocalDateTime showTime;
    private Double  price;

    private Long movieId;
    private Long theatreId;

}
