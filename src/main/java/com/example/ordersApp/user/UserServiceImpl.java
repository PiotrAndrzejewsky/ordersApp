package com.example.ordersApp.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository mUserRepository;
    @Autowired
    private PasswordEncoder mPasswordEncoder;

    @Value("${app.secret-token}")
    private String secret = "";

    @Value("${app.token-time}")
    private int time;

    @Override
    public boolean saveUser(UserEntity userEntity) {
        if (mUserRepository.findByUsername(userEntity.getUsername()).isEmpty()) {
            userEntity.setPassword(mPasswordEncoder.encode(userEntity.getPassword()));
            mUserRepository.save(userEntity);
            return true;
        }
        throw new UsernameNotFoundException("Username already taken");
    }

    @Override
    public HttpStatus login(UserEntity userEntity, HttpServletResponse httpServletResponse) {
        if (mUserRepository.findByUsername(userEntity.getUsername()).isPresent()) {
            UserEntity user = mUserRepository.findByUsername(userEntity.getUsername()).get();
            if (mPasswordEncoder.matches(userEntity.getPassword(), user.getPassword())) {
                String access_token = createToken(user.getUsername(), user.getUserId());
                String refresh_token = createToken(user.getUsername(), user.getUserId());
                httpServletResponse.setHeader(HttpHeaders.AUTHORIZATION, access_token);
                httpServletResponse.setHeader("Refresh-Token", refresh_token);
                return HttpStatus.OK;
            }
        }
        return HttpStatus.FORBIDDEN;
    }

    public String createToken(String subject, Long id) {
        Algorithm ALGORITHM = Algorithm.HMAC256(secret);
        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + time))
                .withIssuer(id.toString())
                .sign(ALGORITHM);
    }
}
