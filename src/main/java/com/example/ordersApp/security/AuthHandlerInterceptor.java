package com.example.ordersApp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Component
public class AuthHandlerInterceptor implements HandlerInterceptor {
    @Value("${app.secret-token}")
    private String secret = "";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!request.getRequestURI().equals("/user/login") && !request.getRequestURI().equals("/user/save") && !request.getRequestURI().equals("/error")) {
            System.out.println(request.getHeader(HttpHeaders.AUTHORIZATION));
            decodeToken(request, secret);
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    public static DecodedJWT decodeToken(HttpServletRequest request, String secret) {
        Algorithm ALGORITHM = Algorithm.HMAC256(secret);
        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        JWTVerifier jwtVerifier = JWT.require(ALGORITHM).build();
        return jwtVerifier.verify(accessToken);
    }
}
