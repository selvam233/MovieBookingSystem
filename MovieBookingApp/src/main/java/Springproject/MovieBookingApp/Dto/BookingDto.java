package Springproject.MovieBookingApp.Dto;

import Springproject.MovieBookingApp.Entity.Bookingstatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BookingDto {

    private Integer numberOfSeats;
    private LocalDate bookingTime;
    private Double price;
    private Bookingstatus bookingStatus;
    private List<String> seatNumbers;
    private Long userId;
    private Long showId;


}

