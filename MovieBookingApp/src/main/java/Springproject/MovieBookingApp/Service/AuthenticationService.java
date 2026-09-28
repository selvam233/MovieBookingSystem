package Springproject.MovieBookingApp.Service;

import Springproject.MovieBookingApp.Dto.LoginRequestDto;
import Springproject.MovieBookingApp.Dto.LoginResponseDto;
import Springproject.MovieBookingApp.Dto.RegisterRequestDto;
import Springproject.MovieBookingApp.Entity.User;
import Springproject.MovieBookingApp.Repository.Userrepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationService {

    private final Userrepository userrepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;

    public AuthenticationService(Userrepository userrepository,
                                 AuthenticationManager authenticationManager,
                                 JwtService jwtService,
                                 PasswordEncoder encoder) {
        this.userrepository = userrepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.encoder = encoder;
    }

    public User registerNormalUser(RegisterRequestDto dto) {
        return registerUserWithRole(dto, "ROLE_USER");
    }

    public User registerAdminUser(RegisterRequestDto dto) {
        return registerUserWithRole(dto, "ROLE_ADMIN");
    }

    private User registerUserWithRole(RegisterRequestDto dto, String role) {
        if (userrepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        Set<String> roles = new HashSet<>();
        roles.add(role);

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setRoles(roles);

        return userrepository.save(user);
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername(),
                        loginRequestDto.getPassword()
                )
        );

        User user = userrepository.findByUsername(loginRequestDto.getUsername())
                .orElseThrow(() -> new RuntimeException("Username not found"));

        String token = jwtService.generateToken(user);

        return LoginResponseDto.builder()
                .jwtToken(token)
                .username(user.getUsername())
                .roles(user.getRoles())
                .build();
    }
}
