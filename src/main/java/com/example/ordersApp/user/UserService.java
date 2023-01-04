package com.example.ordersApp.user;

import javax.servlet.http.HttpServletResponse;


public interface UserService {

    boolean saveUser(UserEntity userEntity);
    Long login(UserEntity userEntity, HttpServletResponse httpServletResponse);

}
