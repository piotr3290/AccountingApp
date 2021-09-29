package pl.wroblewski.simplyaccounting.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JwtUtilsComponent {

    @Value("${simplyaccounting.app.jwt.secret}")
    private String jwtSecret;

    @Value("${simplyaccounting.app.jwt.expiration}")
    private int jwtExpiration;

    public String generateJwt(Authentication authentication) {

        UserDetails principal = (UserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(principal.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS512, TextCodec.BASE64.encode(jwtSecret))
                .compact();
    }

    public String getSubjectFromJwt(String token) {
        return Jwts.parser()
                .setSigningKey(TextCodec.BASE64.encode(jwtSecret))
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
