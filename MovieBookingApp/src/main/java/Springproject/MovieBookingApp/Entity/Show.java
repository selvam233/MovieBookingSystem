package Springproject.MovieBookingApp.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name="shows")
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime showTime;

    private Double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movieid", nullable = false)
    private Movie movie;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "theatreid", nullable = false)
    private Theatre theatre;

    @OneToMany(mappedBy = "show", fetch = FetchType.LAZY)
    private List<Booking> bookings;
}