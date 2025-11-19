package com.example.onlinebanking.service.impl;

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

    @Override
    public String registerUser(UserDto dto) {

        // Check if email already exists
        if (userRepository.existsByEmail(dto.getEmail())) {
            return "Email already registered!";
        }

        // Convert DTO → Entity (User object)
        User user = new User();
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setPhone(dto.getPhone());
        user.setAddress(dto.getAddress());

        // Save user to database
        userRepository.save(user);

        return "User registered successfully!";
    }
}
