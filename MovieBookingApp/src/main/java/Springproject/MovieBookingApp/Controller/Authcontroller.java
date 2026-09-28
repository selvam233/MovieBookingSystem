package Springproject.MovieBookingApp.Controller;

import Springproject.MovieBookingApp.Dto.LoginRequestDto;
import Springproject.MovieBookingApp.Dto.LoginResponseDto;
import Springproject.MovieBookingApp.Dto.RegisterRequestDto;
import Springproject.MovieBookingApp.Dto.RegisterResponseDto;
import Springproject.MovieBookingApp.Entity.User;
import Springproject.MovieBookingApp.Service.AuthenticationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class Authcontroller {

    private final AuthenticationService authenticationService;

    public Authcontroller(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> registerNormalUser(@RequestBody RegisterRequestDto dto) {
        User user = authenticationService.registerNormalUser(dto);
        return ResponseEntity.ok(RegisterResponseDto.builder()
                .userid(user.getUserid())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getRoles())
                .build());
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto dto) {
        LoginResponseDto result = authenticationService.login(dto);

        ResponseCookie cookie = ResponseCookie.from("jwt", result.getJwtToken())
                .httpOnly(true)
                .secure(false)          // set true when you use HTTPS
                .path("/")
                .maxAge(3600)
                .sameSite("Lax")
                .build();

        result.setJwtToken(null);       // remove it from the JSON body
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(result);
    }
}