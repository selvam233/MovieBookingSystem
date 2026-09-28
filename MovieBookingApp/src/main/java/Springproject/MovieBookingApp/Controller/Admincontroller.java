package Springproject.MovieBookingApp.Controller;

import Springproject.MovieBookingApp.Dto.RegisterRequestDto;
import Springproject.MovieBookingApp.Dto.RegisterResponseDto;
import Springproject.MovieBookingApp.Entity.User;
import Springproject.MovieBookingApp.Service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class Admincontroller {

    private final AuthenticationService authenticationService;

    public Admincontroller(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> registerAdminUser(@RequestBody RegisterRequestDto dto) {
        User user = authenticationService.registerAdminUser(dto);
        return ResponseEntity.ok(RegisterResponseDto.builder()
                .userid(user.getUserid())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getRoles())
                .build());
    }
}
