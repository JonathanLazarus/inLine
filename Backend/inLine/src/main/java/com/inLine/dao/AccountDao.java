package com.inLine.dao;

import com.inLine.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountDao extends JpaRepository<Account, Integer> {
   Optional<Account> selectUserByEmail(String username);

}
