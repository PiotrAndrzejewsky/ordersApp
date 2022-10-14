package com.example.ordersApp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.ordersApp.security.CustomAuthenticationFilter.ALGORITHM;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            UsernamePasswordAuthenticationToken authenticationToken = decodeToken(request, response, authorizationHeader);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
        }
        else {
            filterChain.doFilter(request, response);
        }
    }

    public static UsernamePasswordAuthenticationToken decodeToken(HttpServletRequest request, HttpServletResponse response, String authorizationHeader) {
        String token = authorizationHeader.substring("Bearer ".length());
        JWTVerifier jwtVerifier = JWT.require(ALGORITHM).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        String username = decodedJWT.getSubject();
        return new UsernamePasswordAuthenticationToken(username, null, null);
    }
}
