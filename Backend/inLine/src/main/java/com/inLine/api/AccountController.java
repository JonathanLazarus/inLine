package com.inLine.api;



import com.inLine.SecurityAndJWT.AccountManagement.MyUserDetailsService;
import com.inLine.SecurityAndJWT.jwt.JwtRequest;
import com.inLine.SecurityAndJWT.jwt.LoginResponse;
import com.inLine.SecurityAndJWT.jwt.RegisterResponse;
import com.inLine.SecurityAndJWT.jwt.JwtTokenUtil;
import com.inLine.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AccountController {


    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private MyUserDetailsService userDetailsService;

    public AccountController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, MyUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login/submit")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        } catch (AuthenticationException e) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("username or password is incorrect");
        }

        final Account newAccountDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(newAccountDetails);
        return ResponseEntity.ok(new LoginResponse(token, newAccountDetails.getFirstName(), newAccountDetails.getLastName(), newAccountDetails.getLevel()));
    }

    @PostMapping("/register/submit")
    public ResponseEntity<?> CreateAccount(@RequestBody Account registerDetails) {
        try {
            userDetailsService.loadUserByUsername(registerDetails.getEmail());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("there is a account with this username");
        } catch (UsernameNotFoundException e) {
            userDetailsService.addUser(registerDetails);

            final String token = jwtTokenUtil.generateToken(registerDetails);
            return ResponseEntity.ok(new RegisterResponse(token));

        }

    }

}
