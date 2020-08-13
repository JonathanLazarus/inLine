package com.inLine.SecurityAndJWT.jwt;


import com.inLine.model.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;

    @Autowired
    public JwtTokenUtil(SecretKey secretKey, JwtConfig jwtConfig) {

        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
    }


    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    //for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    //generate token for user
    public String generateToken(Account account) {

        return Jwts.builder()
                .setSubject(account.getEmail())
                .claim("authorities", account.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ 60*60*100*jwtConfig.getHours()))//5 hours
                .signWith(secretKey)
                .compact();
    }

    //validate token
    public Boolean validateToken(String token, Account userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
