package com.example.ordersApp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserServiceImpl mUserService;

    @PostMapping("/signUp")
    public boolean signUpUsername(@RequestBody UserEntity userEntity) {
        return mUserService.saveUser(userEntity);
    }
}
