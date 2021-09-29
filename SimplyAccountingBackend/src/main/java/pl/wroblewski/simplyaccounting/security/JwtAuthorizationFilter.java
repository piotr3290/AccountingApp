package pl.wroblewski.simplyaccounting.security;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.wroblewski.simplyaccounting.utils.JwtUtilsComponent;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final String AUTH_TYPE = "Bearer ";
    private final String AUTH_HEADER = "Authorization";
    private final UserDetailsService userDetailsService;
    private final JwtUtilsComponent jwtUtilsComponent;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws IOException, ServletException {
        try {
            String header = parseHeader(httpServletRequest);

            if (header != null) {
                UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(header);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception e) {
            logger.error(e);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String parseHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTH_HEADER);

        if (authorizationHeader != null && authorizationHeader.startsWith(AUTH_TYPE)) {
            return authorizationHeader.substring(AUTH_TYPE.length());
        }
        return null;
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUtilsComponent.getSubjectFromJwt(token));

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
