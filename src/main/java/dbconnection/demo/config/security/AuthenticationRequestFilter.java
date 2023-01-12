package dbconnection.demo.config.security;

import dbconnection.demo.config.AccessToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class AuthenticationRequestFilter extends OncePerRequestFilter {

    private final static String SPRING_SECURITY_ROLE_PREFIX = "ROLE_";

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {


//        chain.doFilter(request, response);

        String requestTokenHeader = request.getHeader("Authorization");
        if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            System.out.println(request);
            System.out.println(response);
            return;
        }

        String accessToken = requestTokenHeader.substring(7);
//
//
        try {
            //.getHeader("Authorization")


            System.out.println(accessToken);
            AccessToken accessTokenDTO = decode(accessToken);
            System.out.println(accessTokenDTO.getRole());
            setupSpringSecurityContext(accessTokenDTO);
//            try{
                chain.doFilter(request, response);

//            }catch (Exception e) {
//                System.out.println("I was not here" + e);
//            }
        } catch (Exception e) {
            logger.error("Error validating access token I was here!!!", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.flushBuffer();
        }

    }

    private void setupSpringSecurityContext(AccessToken accessToken) {


        try {
            UserDetails userDetails = new User(accessToken.getSubject(), "",
                    accessToken.getRole()
                            .stream()
                            .map(role -> new SimpleGrantedAuthority(role))
                            .collect(Collectors.toUnmodifiableList()));
//                        .toList());
            System.out.println(accessToken.getRole());

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(accessToken);
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        } catch (Exception e) {
            System.out.println("Error from: setupSpringSecurityContext metoda" + e);
        }
    }


    byte[] keyBytes = Decoders.BASE64.decode("E91E158E4C6656F68B1B5D1C316766DE98D2AD6EF3BFB44F78E9CFCDF5");
    Key key = Keys.hmacShaKeyFor(keyBytes);

    private AccessToken decode(String accessToken) {

        try {

            Jwt jwt = Jwts.parserBuilder().setSigningKey(key).build().parse(accessToken);

            Claims claims = (Claims) jwt.getBody();

            String role = claims.get("role", String.class);
            List<String> roles = new ArrayList<>();
            roles.add(role);

//            return AccessToken.builder()
//                    .subject(claims.getSubject())
//                    .role(roles)
//                    .user_id(claims.get("user_id", Long.class))
//                    .build();
            System.out.println(role);
            return new AccessToken(claims.get("user_id", Long.class), roles, claims.getSubject());



        } catch (JwtException e) {
            throw new JwtException(e.getMessage());
        }


    }

}
