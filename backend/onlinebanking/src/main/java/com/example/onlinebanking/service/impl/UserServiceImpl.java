package com.example.onlinebanking.service.impl;

import com.example.onlinebanking.dto.LoginDto;
import com.example.onlinebanking.dto.UserDto;
import com.example.onlinebanking.model.User;
import com.example.onlinebanking.repository.UserRepository;
import com.example.onlinebanking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // ---------------------- REGISTER ----------------------
    @Override
    public String registerUser(UserDto dto) {

        // Check if email exists
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            return "Email already registered!";
        }

        // Convert DTO -> Entity
        User user = new User();
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());

        // Encrypt password
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        user.setPhone(dto.getPhone());
        user.setAddress(dto.getAddress());

        userRepository.save(user);

        return "User registered successfully!";
    }

    // ---------------------- LOGIN ----------------------
    @Override
    public String loginUser(LoginDto loginDto) {

        // Find user by email
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check password
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return "Login successful";
    }

    // ---------------------- GET USER BY EMAIL ----------------------
    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
