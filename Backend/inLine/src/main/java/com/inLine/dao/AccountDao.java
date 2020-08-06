package com.inLine.dao;

import com.inLine.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountDao extends JpaRepository<Account, Integer> {
    Optional<Account> selectUserByEmail(String username);
    void addUser(Account newAccount);
}
