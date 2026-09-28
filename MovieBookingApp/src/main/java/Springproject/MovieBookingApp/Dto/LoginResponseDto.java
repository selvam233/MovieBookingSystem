package Springproject.MovieBookingApp.Dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class LoginResponseDto {
    private String jwtToken;
    private String username;
    private Set<String> roles;

}
