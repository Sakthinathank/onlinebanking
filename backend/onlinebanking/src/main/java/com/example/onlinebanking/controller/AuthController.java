package com.example.onlinebanking.controller;

import com.example.onlinebanking.dto.LoginDto;
import com.example.onlinebanking.dto.UserDto;
import com.example.onlinebanking.security.JwtUtil;
import com.example.onlinebanking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    // REGISTER API
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {

        System.out.println("🔥 REGISTER ENDPOINT REACHED");

        String result = userService.registerUser(userDto);
        return ResponseEntity.ok(result);
    }

    // LOGIN API
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDto loginDto) {

        System.out.println("🔥 LOGIN ENDPOINT REACHED");
        System.out.println("Email : " + loginDto.getEmail());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );

        String token = jwtUtil.generateToken(loginDto.getEmail());
        return ResponseEntity.ok(token);
    }
}
