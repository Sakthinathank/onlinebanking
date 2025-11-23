package com.example.onlinebanking.service.impl;

import com.example.onlinebanking.model.Account;
import com.example.onlinebanking.model.User;
import com.example.onlinebanking.repository.AccountRepository;
import com.example.onlinebanking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public User getUserByEmail(String email) {
        return null;
    }

    // ---------------- Create New Account ----------------
    @Override
    public Account createAccount(User user) {

        Account account = new Account();

        // Generate a random 10–12 digit account number
        account.setAccountNumber(generateAccountNumber());

        // Set starting balance as 0
        account.setBalance(0.0);

        // Link account with logged-in user
        account.setUser(user);

        return accountRepository.save(account);
    }

    // ---------------- Get all accounts of a user ----------------
    @Override
    public List<Account> getUserAccounts(User user) {
        return accountRepository.findByUser(user);
    }

    // ---------------- Get account by account number ----------------
    @Override
    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    // ---------------- Generate Account Number ----------------
    private String generateAccountNumber() {
        Random random = new Random();
        long number = 1000000000L + random.nextLong(9000000000L); // 10-digit number
        return String.valueOf(number);
    }
}
