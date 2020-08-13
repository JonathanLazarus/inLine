package com.inLine.SecurityAndJWT.jwt;

import com.google.common.net.HttpHeaders;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfig {
    private String prefix = "Bearer ";
    private int hours = 1;
    private String secretKey;

    public JwtConfig() {
    }

    public String getSecretKey() {
        return secretKey;
    }
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public int getHours() {
        return hours;
    }

    public String getPrefix() {
        return prefix;
    }
    public String getAuthorizationHeader(){
        return HttpHeaders.AUTHORIZATION;
    }

    @Bean
    public SecretKey secretKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
