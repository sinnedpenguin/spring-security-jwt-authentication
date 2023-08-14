package com.springsecurityjwtauthentication.backend.controllers;

import com.springsecurityjwtauthentication.backend.dtos.UserDto;
import com.springsecurityjwtauthentication.backend.dtos.UserLoginDto;
import com.springsecurityjwtauthentication.backend.models.User;
import com.springsecurityjwtauthentication.backend.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public User register(@RequestBody UserDto user){
        return authenticationService.register(user.getEmail(), user.getPassword());
    }

    @PostMapping("/login")
    public UserLoginDto login(@RequestBody UserDto user){
        return authenticationService.login(user.getEmail(), user.getPassword());
    }
}
