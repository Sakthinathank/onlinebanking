package com.example.onlinebanking.service;

import com.example.onlinebanking.dto.LoginDto;
import com.example.onlinebanking.dto.UserDto;
import com.example.onlinebanking.model.User;

public interface UserService {

    String registerUser(UserDto dto);

    String loginUser(LoginDto dto);

    User getUserByEmail(String email);
}
