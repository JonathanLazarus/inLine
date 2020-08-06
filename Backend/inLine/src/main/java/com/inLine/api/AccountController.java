package com.inLine.api;



import com.inLine.SecurityAndJWT.AccountManagement.MyUserDetailsService;
import com.inLine.SecurityAndJWT.jwt.JwtRequest;
import com.inLine.SecurityAndJWT.jwt.JwtResponse;
import com.inLine.SecurityAndJWT.jwt.JwtTokenUtil;
import com.inLine.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {


    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private MyUserDetailsService userDetailsService;
    //insert provider
    public AccountController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, MyUserDetailsService userDetailsService) {

        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login/submit")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        final Account newAccountDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(newAccountDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping("/register/submit")
    public ResponseEntity<?> CreateAccount (@RequestBody Account registerDetails) throws Exception {
        try{
            userDetailsService.loadUserByUsername(registerDetails.getEmail());
            throw new Exception("there is a account with this username");
        }catch (UsernameNotFoundException e){

            userDetailsService.addUser(registerDetails);

            final String token = jwtTokenUtil.generateToken(registerDetails);
            return ResponseEntity.ok(new JwtResponse(token));
        }

    }

}
