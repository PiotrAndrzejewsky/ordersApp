package com.example.ordersApp.security;

import com.example.ordersApp.user.UserEntity;
import com.example.ordersApp.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public UserPrincipalDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity userEntity = this.userRepository.findByUsername(s);
        UserPrincipal userPrincipal = new UserPrincipal(userEntity);

        return userPrincipal;
    }
}