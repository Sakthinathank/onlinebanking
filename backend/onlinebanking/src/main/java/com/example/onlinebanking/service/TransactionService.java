package com.example.onlinebanking.service;

import com.example.onlinebanking.model.Transaction;
import com.example.onlinebanking.model.User;

import java.util.List;

public interface TransactionService {

    String deposit(String accountNumber, double amount, User user);

    String withdraw(String accountNumber, double amount, User user);

    double getBalance(String accountNumber, User user);

    String transfer(String fromAccount, String toAccount, double amount, User user);

    List<Transaction> getUserTransactions(User user);

}
