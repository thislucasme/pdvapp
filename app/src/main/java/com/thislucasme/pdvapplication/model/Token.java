package com.thislucasme.pdvapplication.model;

public class Token {
    private String acess_token;

    public Token() {
    }

    public Token(String acess_token) {
        this.acess_token = acess_token;
    }

    public String getAcess_token() {
        return acess_token;
    }

    public void setAcess_token(String acess_token) {
        this.acess_token = acess_token;
    }
}
