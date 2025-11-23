package com.example.onlinebanking.controller;

import com.example.onlinebanking.model.Account;
import com.example.onlinebanking.model.User;
import com.example.onlinebanking.security.JwtUtil;
import com.example.onlinebanking.service.AccountService;
import com.example.onlinebanking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;


    // ---------------- CREATE ACCOUNT ----------------
    @PostMapping("/create")
    public Account createAccount(Authentication authentication) {

        // Get logged-in user's email from JWT token
        String email = authentication.getName();

        // Fetch user from database
        User user = userService.getUserByEmail(email);

        // Create and return new account
        return accountService.createAccount(user);
    }


    // ---------------- VIEW USER ACCOUNTS ----------------
    @GetMapping("/my-accounts")
    public List<Account> getMyAccounts(Authentication authentication) {

        String email = authentication.getName();
        User user = userService.getUserByEmail(email);

        return accountService.getUserAccounts(user);
    }


    // ---------------- GET ACCOUNT BY NUMBER ----------------
    @GetMapping("/{accountNumber}")
    public Account getByAccountNumber(@PathVariable String accountNumber) {
        return accountService.getAccountByNumber(accountNumber);
    }
}
