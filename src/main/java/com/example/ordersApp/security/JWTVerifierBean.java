package com.example.ordersApp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class JWTVerifierBean {

    @Value("${app.secret-token}")
    private String secret = "";

    public DecodedJWT decodeToken(HttpServletRequest request) {
        Algorithm ALGORITHM = Algorithm.HMAC256(secret);
        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        JWTVerifier jwtVerifier = JWT.require(ALGORITHM).build();
        return jwtVerifier.verify(accessToken);
    }
}
