package com.angularpsringbootecommerce.backend.controllers;

import com.angularpsringbootecommerce.backend.dtos.UserLoginDto;
import com.angularpsringbootecommerce.backend.dtos.UserRegisterDto;
import com.angularpsringbootecommerce.backend.models.User;
import com.angularpsringbootecommerce.backend.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public User register(@RequestBody UserRegisterDto body){
        return authenticationService.register(body.getEmail(), body.getPassword());
    }

    @PostMapping("/login")
    public UserLoginDto login(@RequestBody UserRegisterDto body){
        return authenticationService.login(body.getEmail(), body.getPassword());
    }
}
