package com.inLine.service;


import com.inLine.SecurityAndJWT.jwt.JwtTokenUtil;
import com.inLine.dao.AccountDao;
import com.inLine.model.Account;
import com.inLine.model.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService implements UserDetailsService {
    @Autowired
    AccountDao accountDao;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtUtil;

    public Account addUser(Account newAccount) {
        newAccount.setEncodedPassword(passwordEncoder);
        return accountDao.save(newAccount);
    }

    @Override
    public Account loadUserByUsername(String s) throws UsernameNotFoundException {
        return accountDao.selectUserByEmail(s)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", s)));
    }

    public List<UserStatus> getUserHistory(String token) {
        Account user = getAccountFromToken(token);
        return user.getHistory();
    }

    private Account getAccountFromToken(String token) {
        int id = getIdFromToken(token);
        return accountDao.findById(id)
            .orElse(null);
    }

    private int getIdFromToken(String token) {
        return jwtUtil.getIdFromToken(token.substring(7));
    }
}
