package com.example.ordersApp.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {

    boolean saveUser(UserEntity userEntity);
    void refreshToken (HttpServletRequest request, HttpServletResponse response);
}
