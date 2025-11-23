package com.example.onlinebanking.service.impl;

import com.example.onlinebanking.model.Account;
import com.example.onlinebanking.model.Transaction;
import com.example.onlinebanking.model.User;
import com.example.onlinebanking.repository.AccountRepository;
import com.example.onlinebanking.repository.TransactionRepository;
import com.example.onlinebanking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    // ---------------------- DEPOSIT ----------------------
    @Override
    public String deposit(String accountNumber, double amount, User user) {

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

        saveTransaction(account, user, amount, "DEPOSIT");

        return "Amount deposited successfully!";
    }

    // ---------------------- WITHDRAW ----------------------
    @Override
    public String withdraw(String accountNumber, double amount, User user) {

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);

        saveTransaction(account, user, amount, "WITHDRAW");

        return "Amount withdrawn successfully!";
    }

    // ---------------------- TRANSFER ----------------------
    @Override
    public String transfer(String fromAccountNumber, String toAccountNumber, double amount, User user) {

        if (fromAccountNumber.equals(toAccountNumber)) {
            throw new RuntimeException("Cannot transfer to the same account");
        }

        Account fromAcc = accountRepository.findByAccountNumber(fromAccountNumber)
                .orElseThrow(() -> new RuntimeException("Sender account not found"));

        Account toAcc = accountRepository.findByAccountNumber(toAccountNumber)
                .orElseThrow(() -> new RuntimeException("Receiver account not found"));

        if (fromAcc.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance for transfer");
        }

        fromAcc.setBalance(fromAcc.getBalance() - amount);
        toAcc.setBalance(toAcc.getBalance() + amount);

        accountRepository.save(fromAcc);
        accountRepository.save(toAcc);

        saveTransaction(fromAcc, user, amount, "TRANSFER");
        saveTransaction(toAcc, user, amount, "DEPOSIT");

        return "Transfer successful!";
    }

    // ---------------------- GET USER TRANSACTIONS ----------------------
    @Override
    public List<Transaction> getUserTransactions(User user) {
        return transactionRepository.findByUser(user);
    }

    // ---------------------- SAVE TRANSACTION ----------------------
    private void saveTransaction(Account account, User user, double amount, String type) {

        Transaction t = new Transaction();
        t.setAccount(account);
        t.setUser(user);
        t.setAmount(amount);
        t.setType(type);
        t.setTimestamp(LocalDateTime.now());

        transactionRepository.save(t);
    }
}
