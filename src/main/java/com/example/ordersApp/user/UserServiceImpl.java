package com.example.ordersApp.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.example.ordersApp.security.CustomAuthenticationFilter.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository mUserRepository;
    private PasswordEncoder mPasswordEncoder;

    @Override
    public boolean saveUser(UserEntity userEntity) {
        if (!mUserRepository.findByUsername(userEntity.getUsername()).isPresent()) {
            userEntity.setPassword(mPasswordEncoder.encode(userEntity.getPassword()));
            mUserRepository.save(userEntity);
            return true;
        }
        throw new UsernameNotFoundException("Username already taken");
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring("Bearer ".length());
            JWTVerifier jwtVerifier = JWT.require(ALGORITHM).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            String username = decodedJWT.getSubject();
            String accessToken = createToken(username, EXPIRATION_DATE, request.getRequestURL().toString());
            String refreshToken = createToken(username, EXPIRATION_DATE * 5, request.getRequestURL().toString());
            response.setHeader("Access-Token", accessToken);
            response.setHeader("Refresh-Token", refreshToken);
        }
        else {
            throw new RuntimeException("refresh token is missing");
        }
    }
}
