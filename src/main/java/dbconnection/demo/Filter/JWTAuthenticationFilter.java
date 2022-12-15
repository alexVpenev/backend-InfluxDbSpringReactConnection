package dbconnection.demo.Filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.demo.config.AuthenticationConfigConstants;
import dbconnection.demo.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            Account creds = new ObjectMapper()
                    .readValue(request.getInputStream(), Account.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException, ServletException {
        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withClaim("role", auth.getAuthorities().iterator().next().getAuthority())
                .withExpiresAt(new Date(System.currentTimeMillis() + AuthenticationConfigConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(AuthenticationConfigConstants.SECRET.getBytes()));

        //START - SENDING JWT AS A BODY
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(
                "{\"" + AuthenticationConfigConstants.HEADER_STRING + "\":\"" + AuthenticationConfigConstants.TOKEN_PREFIX + token + "\"}"
        );
        //END - SENDING JWT AS A BODY

        //START - SENDING JWT AS A HEADER
        response.addHeader(AuthenticationConfigConstants.HEADER_STRING, AuthenticationConfigConstants.TOKEN_PREFIX + token);
        //END - SENDING JWT AS A HEADER
    }


}
