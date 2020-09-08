package com.inLine.SecurityAndJWT.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Parameter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class JwtRequest {

    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String password;

    public JwtRequest(@JsonProperty("email") @Parameter(description = "Account email") String email,
                      @JsonProperty("password") @Parameter(description = "Account password")String password){
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
