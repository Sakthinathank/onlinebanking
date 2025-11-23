package com.example.onlinebanking.controller;

import com.example.onlinebanking.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        System.out.println("🔥🔥 REGISTER ENDPOINT REACHED 🔥🔥");
        System.out.println("Received: " + userDto.getEmail());
        return ResponseEntity.ok("User registered successfully");
    }


}

