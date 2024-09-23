package com.jalonso98.users.dto;

public class LoginRequest {

    private String username;
    private String password;

    // Constructor vacío
    public LoginRequest() {}

    // Getters y Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
