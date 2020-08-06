package com.inLine.dao;

import com.inLine.model.Account;

import java.util.Optional;

public class AccountDaoImpl implements AccountDao {
    @Override
    public Optional<Account> selectUserByEmail(String username) {
        return Optional.empty();
    }

    @Override
    public void addUser(Account newAccount) {
           save(newAccount);
    }
}
