package com.inLine.SecurityAndJWT.jwt;

import java.io.Serializable;

public class LoginResponse implements Serializable {
        private final String token;
        private final String first_name;
        private final String last_name;
        private final String account_type;



    public LoginResponse(String token, String first_name, String last_name, String level) {
        this.token = token;
        this.first_name = first_name;
        this.last_name = last_name;
        this.account_type=level;
    }

    public String getToken() {
        return this.token;
        }

        public String getFirst_name() {
            return first_name;
        }
    public String getLast_name() {
        return last_name;
    }

    public String getAccount_type() {
        return account_type;
    }
}

