package com.example.ordersApp.user;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface UserService {

    boolean saveUser(UserEntity userEntity);
    Long login(UserEntity userEntity, HttpServletResponse httpServletResponse);

}
