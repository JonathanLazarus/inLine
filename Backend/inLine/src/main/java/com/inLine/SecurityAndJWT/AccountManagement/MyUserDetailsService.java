package com.inLine.SecurityAndJWT.AccountManagement;


import com.inLine.dao.AccountDao;
import com.inLine.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    AccountDao accountRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    public void addUser(Account newAccount){
        newAccount.setEncodedPassword(passwordEncoder);
        accountRepository.save(newAccount);
    }
    @Override
    public Account loadUserByUsername(String email) throws UsernameNotFoundException {
        return accountRepository.selectUserByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(String.format("Username %s not found", email)));
    }
}
