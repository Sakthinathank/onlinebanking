package com.example.onlinebanking.controller;

import com.example.onlinebanking.model.Transaction;
import com.example.onlinebanking.model.User;
import com.example.onlinebanking.service.TransactionService;
import com.example.onlinebanking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    // ------------------ DEPOSIT ------------------
    @PostMapping("/deposit")
    public String deposit(
            @RequestParam String accountNumber,
            @RequestParam double amount,
            Authentication authentication) {

        String email = authentication.getName();
        User user = userService.getUserByEmail(email);

        return transactionService.deposit(accountNumber, amount, user);
    }

    // ------------------ WITHDRAW ------------------
    @PostMapping("/withdraw")
    public String withdraw(
            @RequestParam String accountNumber,
            @RequestParam double amount,
            Authentication authentication) {

        String email = authentication.getName();
        User user = userService.getUserByEmail(email);

        return transactionService.withdraw(accountNumber, amount, user);
    }

    // ------------------ TRANSFER ------------------
    @PostMapping("/transfer")
    public String transfer(
            @RequestParam String fromAccount,
            @RequestParam String toAccount,
            @RequestParam double amount,
            Authentication authentication) {

        String email = authentication.getName();
        User user = userService.getUserByEmail(email);

        return transactionService.transfer(fromAccount, toAccount, amount, user);
    }

    // ------------------ VIEW TRANSACTIONS ------------------
    @GetMapping("/my-transactions")
    public List<Transaction> getMyTransactions(Authentication authentication) {

        String email = authentication.getName();
        User user = userService.getUserByEmail(email);

        return transactionService.getUserTransactions(user);
    }
}
