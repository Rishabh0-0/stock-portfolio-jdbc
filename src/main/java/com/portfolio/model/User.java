package com.portfolio.model;


import java.time.LocalDateTime;

public class User {
    private int user_id;
    private String username;
    private String email;
    private String password;
    private double wallet_balance;
    private LocalDateTime created_at;

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.wallet_balance = 0.00;
        this.created_at = LocalDateTime.now();
    }

    public User(int user_id, String username, String email, String password, double wallet_balance, LocalDateTime created_at) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.wallet_balance = wallet_balance;
        this.created_at = created_at;
    }

    public User(String username, String email, String password, double wallet_balance, LocalDateTime created_at) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.wallet_balance = wallet_balance;
        this.created_at = created_at;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public double getWallet_balance() {
        return wallet_balance;
    }

    public void setWallet_balance(double wallet_balance) {
        this.wallet_balance = wallet_balance;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", wallet_balance=" + wallet_balance +
                ", created_at=" + created_at +
                '}';
    }
}
