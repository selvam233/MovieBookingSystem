package Springproject.MovieBookingApp.Service;

import Springproject.MovieBookingApp.Dto.BookingDto;
import Springproject.MovieBookingApp.Entity.Booking;
import Springproject.MovieBookingApp.Entity.Bookingstatus;
import Springproject.MovieBookingApp.Entity.Show;
import Springproject.MovieBookingApp.Entity.User;
import Springproject.MovieBookingApp.Repository.Bookingrepository;
import Springproject.MovieBookingApp.Repository.Showrepository;
import Springproject.MovieBookingApp.Repository.Userrepository;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class Bookingservice {

    private final Bookingrepository bookingRepository;
    private final Userrepository userRepository;
    private final Showrepository showRepository;

    public Bookingservice(Bookingrepository bookingRepository,
                          Userrepository userRepository,
                          Showrepository showRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.showRepository = showRepository;
    }

    @Transactional
    public Booking createBookings(BookingDto bookingDto) {

        User user = userRepository.findById(bookingDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Show show = showRepository.findById(bookingDto.getShowId())
                .orElseThrow(() -> new RuntimeException("Show not found"));

        List<String> seatNumbers = bookingDto.getSeatNumbers();

        if (seatNumbers == null || seatNumbers.isEmpty()) {
            throw new RuntimeException("Select at least one seat");
        }

        if (!isSeatsAvailable(show.getId(), seatNumbers)) {
            throw new RuntimeException("Seats not available");
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShow(show);
        booking.setNumberOfSeats(seatNumbers.size());
        booking.setSeatNumbers(seatNumbers);
        booking.setPrice(bookingDto.getPrice());
        booking.setBookingTime(LocalDateTime.now());
        booking.setBookingDeadline(LocalDateTime.now().plusHours(2));
        booking.setBookingStatus(Bookingstatus.PENDING);

        return bookingRepository.save(booking);
    }

    public List<Booking> getuserbookings(Long userId) {
        return bookingRepository.findByUser_Userid(userId);
    }

    public List<Booking> getshowbookings(Long showId) {
        return bookingRepository.findByShowId(showId);
    }

    @Transactional
    public Booking confirmBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setBookingStatus(Bookingstatus.CONFIRMED);
        return bookingRepository.save(booking);
    }

    @Transactional
    public Booking cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        validateCancellation(booking);

        booking.setBookingStatus(Bookingstatus.CANCELLED);
        return bookingRepository.save(booking);
    }

    public void validateCancellation(Booking booking) {
        if (booking.getBookingStatus() == Bookingstatus.CANCELLED) {
            throw new RuntimeException("Booking already cancelled");
        }

        LocalDateTime cancellationCutoff = booking.getShow().getShowTime().minusHours(2);
        if (LocalDateTime.now().isAfter(cancellationCutoff)) {
            throw new RuntimeException("Booking cannot be cancelled within 2 hours of the show");
        }
    }

    public List<Booking> getBookingsByStatus(Bookingstatus status) {
        return bookingRepository.findByBookingStatus(status);
    }

    public boolean isSeatsAvailable(Long showId, List<String> requestedSeats) {

        Set<String> unique = new HashSet<>(requestedSeats);
        if (unique.size() != requestedSeats.size()) {
            return false; // same seat requested twice
        }

        Set<String> bookedSeats = new HashSet<>();
        for (Booking b : bookingRepository.findByShowId(showId)) {
            if (b.getBookingStatus() != Bookingstatus.CANCELLED) {
                bookedSeats.addAll(b.getSeatNumbers());
            }
        }

        return unique.stream().noneMatch(bookedSeats::contains);
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void expireOldBookings() {
        LocalDateTime now = LocalDateTime.now();

        for (Booking booking : bookingRepository.findByBookingStatus(Bookingstatus.PENDING)) {
            if (booking.getBookingDeadline().isBefore(now)) {
                booking.setBookingStatus(Bookingstatus.CANCELLED);
                bookingRepository.save(booking);
            }
        }
    }
}