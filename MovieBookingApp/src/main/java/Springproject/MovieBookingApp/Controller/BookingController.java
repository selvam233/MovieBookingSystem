package Springproject.MovieBookingApp.Controller;

import Springproject.MovieBookingApp.Dto.BookingDto;
import Springproject.MovieBookingApp.Entity.Booking;
import Springproject.MovieBookingApp.Entity.Bookingstatus;
import Springproject.MovieBookingApp.Service.Bookingservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private Bookingservice bookingservice;


    @PostMapping("/createbooking")
    public ResponseEntity<Booking> createBookings(@RequestBody BookingDto bookingDto) {
        return ResponseEntity.ok(bookingservice.createBookings(bookingDto));
    }


    @GetMapping("/getuserbooking/{id}")
    public ResponseEntity<List<Booking>> getuserbookings(@PathVariable Long id) {
        return ResponseEntity.ok(bookingservice.getuserbookings(id));
    }


    @GetMapping("/getshowbookings/{id}")
    public ResponseEntity<List<Booking>> getshowbookings(@PathVariable Long id) {
        return ResponseEntity.ok(bookingservice.getshowbookings(id));
    }


    @PutMapping("/{id}/confirm")
    public ResponseEntity<Booking> confirmBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingservice.confirmBooking(id));
    }


    @PutMapping("/{id}/cancel")
    public ResponseEntity<Booking> cancelBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingservice.cancelBooking(id));
    }

    @GetMapping("/status/{bookingstatus}")
    public ResponseEntity<List<Booking>> getBookingsBystatus(@PathVariable Bookingstatus bookingstatus) {
        return ResponseEntity.ok(bookingservice.getBookingsByStatus(bookingstatus));
    }
}