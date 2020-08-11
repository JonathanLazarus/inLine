package com.inLine.SecurityAndJWT.jwt;

import java.io.Serializable;

public class JwtResponse implements Serializable {
    private final String jwttoken;
    private final String first_name;



    public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
        first_name = null;
    }

    public JwtResponse(String token, String firstName) {
        this.jwttoken=token;
        this.first_name=firstName;
    }


    public String getToken() {
        return this.jwttoken;
    }
    public String getFirst_name() {
        return first_name;
    }
}

