package com.inLine.SecurityAndJWT.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JwtRequest {
    private String email;
    private String password;

    public JwtRequest( @JsonProperty("email") String email,
                       @JsonProperty("password") String password){
        this.email=email;
        this.password=password;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
