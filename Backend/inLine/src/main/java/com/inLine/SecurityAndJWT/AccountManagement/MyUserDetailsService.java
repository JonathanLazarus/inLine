package com.inLine.SecurityAndJWT.AccountManagement;


import com.inLine.dao.AccountDao;
import com.inLine.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    AccountDao userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;



    public void addUser(Account newAccount){
        newAccount.setEncodedPassword(passwordEncoder);
        userRepository.save(newAccount);
    }
    @Override
    public Account loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.selectUserByEmail(s)
                .orElseThrow(()-> new UsernameNotFoundException(String.format("Username %s not found", s)));

        //return null;
    }
}
