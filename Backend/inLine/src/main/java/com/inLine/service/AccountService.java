package com.inLine.service;


import com.inLine.dao.AccountDao;
import com.inLine.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    AccountDao accountDao;
    @Autowired
    PasswordEncoder passwordEncoder;


    public Account addUser(Account newAccount) {
        newAccount.setEncodedPassword(passwordEncoder);
        return accountDao.save(newAccount);
    }

    @Override
    public Account loadUserByUsername(String s) throws UsernameNotFoundException {
        return accountDao.selectUserByEmail(s)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", s)));

    }
}