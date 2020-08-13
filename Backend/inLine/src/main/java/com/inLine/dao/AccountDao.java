package com.inLine.dao;

import com.inLine.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountDao extends JpaRepository<Account, Integer> {
   @Query("FROM Account WHERE email = ?1")
   Optional<Account> selectUserByEmail(String username);
}