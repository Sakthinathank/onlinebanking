package com.example.onlinebanking.service.impl;

import com.example.onlinebanking.dto.LoginDto;
import com.example.onlinebanking.dto.UserDto;
import com.example.onlinebanking.model.User;
import com.example.onlinebanking.repository.UserRepository;
import com.example.onlinebanking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    // ---------------------- REGISTER ----------------------
    @Override
    public String registerUser(UserDto dto) {

        // 1. Check if email already exists
        if (userRepository.existsByEmail(dto.getEmail())) {
            return "Email already registered!";
        }

        // 2. Convert DTO → Entity
        User user = new User();
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setPhone(dto.getPhone());
        user.setAddress(dto.getAddress());

        // 3. Save user to database
        userRepository.save(user);

        return "User registered successfully!";
    }


    // ---------------------- LOGIN ----------------------
    @Override
    public String loginUser(LoginDto loginDto) {

        // 1. Check if user exists using email
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Check password match
        if (!user.getPassword().equals(loginDto.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // 3. Success
        return "Login successful";
    }
}
