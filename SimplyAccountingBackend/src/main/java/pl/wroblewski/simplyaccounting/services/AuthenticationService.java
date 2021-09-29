package pl.wroblewski.simplyaccounting.services;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pl.wroblewski.simplyaccounting.models.requests.LoginRequest;
import pl.wroblewski.simplyaccounting.models.responses.LoginResponse;
import pl.wroblewski.simplyaccounting.utils.JwtUtilsComponent;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final JwtUtilsComponent jwtUtilsComponent;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    public LoginResponse authenticate(LoginRequest loginRequest) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDetails, loginRequest.getPassword(), userDetails.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtilsComponent.generateJwt(authentication);

        return LoginResponse.builder()
                .username(loginRequest.getUsername())
                .token(jwt)
                .build();
    }

}
