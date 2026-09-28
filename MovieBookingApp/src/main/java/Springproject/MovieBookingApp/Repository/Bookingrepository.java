package Springproject.MovieBookingApp.Repository;

import Springproject.MovieBookingApp.Entity.Booking;
import Springproject.MovieBookingApp.Entity.Bookingstatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Bookingrepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUser_Userid(Long userid);

    List<Booking> findByShowId(Long showId);

    List<Booking> findByBookingStatus(Bookingstatus status);
}
