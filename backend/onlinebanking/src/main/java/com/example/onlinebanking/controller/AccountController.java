package com.example.onlinebanking.controller;

import com.example.onlinebanking.model.Account;
import com.example.onlinebanking.model.User;
import com.example.onlinebanking.service.AccountService;
import com.example.onlinebanking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    // ---------- CREATE ACCOUNT ----------
    @PostMapping("/create")
    public Account createAccount(Authentication authentication) {

        String email = authentication.getName();
        User user = userService.getUserByEmail(email);

        return accountService.createAccount(user);
    }

    // ---------- CHECK BALANCE ----------
    @GetMapping("/balance/{accountNumber}")
    public double getBalance(
            @PathVariable String accountNumber,
            Authentication authentication
    ) {
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);

        return accountService.getBalance(accountNumber, user);
    }
}
