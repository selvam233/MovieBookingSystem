package Springproject.MovieBookingApp.Repository;

import Springproject.MovieBookingApp.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Userrepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);

}
