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

    // ---------------- CREATE NEW ACCOUNT ----------------
    @Override
    public Account createAccount(User user) {

        Account account = new Account();

        // Generate 10-digit random account number
        account.setAccountNumber(generateAccountNumber());

        account.setBalance(0.0);
        account.setUser(user);

        return accountRepository.save(account);
    }

    // ---------------- GET USER ACCOUNTS ----------------
    @Override
    public List<Account> getUserAccounts(User user) {
        return accountRepository.findByUser(user);
    }

    // ---------------- GET ACCOUNT BY NUMBER ----------------
    @Override
    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    // ---------------- GET BALANCE ----------------
    @Override
    public double getBalance(String accountNumber, User user) {

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // ensure account belongs to logged-in user
        if (!account.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Not your account!");
        }

        return account.getBalance();
    }

    // ---------------- GENERATE ACCOUNT NO ----------------
    private String generateAccountNumber() {
        Random random = new Random();
        long number = 1000000000L + random.nextLong(9000000000L); // 10-digit
        return String.valueOf(number);
    }
}
