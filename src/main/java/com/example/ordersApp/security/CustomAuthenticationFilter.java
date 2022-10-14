package com.example.ordersApp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.ordersApp.model.LoginViewModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager mAuthenticationManager;
    public static final Algorithm ALGORITHM = Algorithm.HMAC256("token");
    public static final int EXPIRATION_DATE = 300000;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.mAuthenticationManager = authenticationManager;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        String access_token = createToken(user.getUsername(), EXPIRATION_DATE, request.getRequestURL().toString());
        String refresh_token = createToken(user.getUsername(), EXPIRATION_DATE * 5, request.getRequestURL().toString());
        response.setHeader("Access-Token", access_token);
        response.setHeader("Refresh-Token", refresh_token);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        LoginViewModel credentials = null;
        try {
            credentials = new ObjectMapper().readValue(request.getInputStream(), LoginViewModel.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword());
        return mAuthenticationManager.authenticate(authenticationToken);
    }

    public static String createToken(String subject, int time, String url) {
        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + time))
                .withIssuer(url)
                .sign(ALGORITHM);
    }
}
