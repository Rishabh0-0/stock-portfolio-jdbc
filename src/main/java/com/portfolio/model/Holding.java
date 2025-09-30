package com.portfolio.model;

public class Holding {
    private int holding_id;
    private int user_id;
    private int stock_id;
    private int quantity;

    public Holding() {
    }

    public Holding(int holding_id, int user_id, int stock_id, int quantity) {
        this.holding_id = holding_id;
        this.user_id = user_id;
        this.stock_id = stock_id;
        this.quantity = quantity;
    }

    public Holding(int user_id, int stock_id, int quantity) {
        this.user_id = user_id;
        this.stock_id = stock_id;
        this.quantity = quantity;
    }

    public int getHolding_id() {
        return holding_id;
    }

    public void setHolding_id(int holding_id) {
        this.holding_id = holding_id;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "holding{" +
                "holding_id=" + holding_id +
                ", user_id=" + user_id +
                ", stock_id=" + stock_id +
                ", quantity=" + quantity +
                '}';
    }
}
