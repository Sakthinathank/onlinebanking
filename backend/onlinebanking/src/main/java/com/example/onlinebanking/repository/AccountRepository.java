package com.example.onlinebanking.repository;

import com.example.onlinebanking.model.Account;
import com.example.onlinebanking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);

    List<Account> findByUser(User user);

}
