package com.inLine.SecurityAndJWT.jwt;

import java.io.Serializable;

public class RegisterResponse implements Serializable {
    private final String jwttoken;




    public RegisterResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public RegisterResponse(String token, String firstName) {
        this.jwttoken=token;
    }


    public String getToken() {
        return this.jwttoken;
    }

}

