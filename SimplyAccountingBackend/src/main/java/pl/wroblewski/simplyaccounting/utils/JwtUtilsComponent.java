package pl.wroblewski.simplyaccounting.utils;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;

import java.util.Date;


@Component
public class JwtUtilsComponent {

    @Value("${simplyaccounting.app.jwtSecret}")
    private String jwtSecret;

    @Value("${simplyaccounting.app.jwtExpiration}")
    private int jwtExpiration;

    public String generateJwt(Authentication authentication) {

        UserDetails principal = (UserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(principal.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getSubjectFromJwt(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
