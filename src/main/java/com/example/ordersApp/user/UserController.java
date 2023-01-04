package com.example.ordersApp.user;

import com.example.ordersApp.apiError.ApiError;
import com.example.ordersApp.security.AuthHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserServiceImpl mUserService;

    @PostMapping("/user/save")
    public ResponseEntity<Object> signUpUsername(@RequestBody UserEntity userEntity) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/save").toUriString());
        return ResponseEntity.created(uri).body(mUserService.saveUser(userEntity));
    }

    @PostMapping("/user/login")
    public ResponseEntity<Object> login(@RequestBody UserEntity userEntity, HttpServletResponse httpServletResponse) { // take request body from request (username, password)
        Long id = mUserService.login(userEntity, httpServletResponse);
        if (id != 0) {
            return new ResponseEntity<Object>(id, new HttpHeaders(), HttpStatus.OK);
        }
        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, ApiError.DEFAULT_MESSAGE, ApiError.DEFAULT_SUGGESTED_ACTION);
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }
}
