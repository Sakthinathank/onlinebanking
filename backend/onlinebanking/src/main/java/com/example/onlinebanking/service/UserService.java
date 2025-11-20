package com.example.onlinebanking.service;

import com.example.onlinebanking.dto.LoginDto;
import com.example.onlinebanking.dto.UserDto;

public interface UserService {
    String registerUser(UserDto userDto);

    String loginUser(LoginDto loginDto);

}
