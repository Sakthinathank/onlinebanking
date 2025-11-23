package com.example.onlinebanking.repository;

import com.example.onlinebanking.model.Transaction;
import com.example.onlinebanking.model.User;
import com.example.onlinebanking.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUser(User user);

    List<Transaction> findByAccount(Account account);
}
