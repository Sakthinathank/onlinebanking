package com.example.onlinebanking.service;

import com.example.onlinebanking.model.Account;
import com.example.onlinebanking.model.User;

import java.util.List;

public interface AccountService {

    Account createAccount(User user);

    List<Account> getUserAccounts(User user);

    Account getAccountByNumber(String accountNumber);

    double getBalance(String accountNumber, User user);

}
