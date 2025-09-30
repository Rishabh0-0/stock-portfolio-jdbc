package com.portfolio.model;

import java.time.LocalDateTime;

public class Transaction {
    private int transaction_id;
    private int user_id;
    private int stock_id;
    private String type;
    private int quantity;
    private double price;
    private LocalDateTime timestamp;

    public Transaction() {
    }

    public Transaction(int transaction_id, int user_id, int stock_id, String type, int quantity, double price, LocalDateTime timestamp) {
        this.transaction_id = transaction_id;
        this.user_id = user_id;
        this.stock_id = stock_id;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
        this.timestamp = timestamp;
    }

    public Transaction(int user_id, int stock_id, String type, int quantity, double price, LocalDateTime timestamp) {
        this.user_id = user_id;
        this.stock_id = stock_id;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
        this.timestamp = timestamp;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getStock_id() {
        return stock_id;
    }

    public void setStock_id(int stock_id) {
        this.stock_id = stock_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transaction_id=" + transaction_id +
                ", user_id=" + user_id +
                ", stock_id=" + stock_id +
                ", type='" + type + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", timestamp=" + timestamp +
                '}';
    }
}
