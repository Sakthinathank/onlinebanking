package com.example.onlinebanking.controller;

import com.example.onlinebanking.dto.LoginDto;
import com.example.onlinebanking.dto.UserDto;
import com.example.onlinebanking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")

    public String registerUser(@RequestBody UserDto userDto){
        return userService.registerUser(userDto);
    }
    @PostMapping("/login")
    public String loginUser(@RequestBody LoginDto loginDto) {
        return userService.loginUser(loginDto);
    }




}
