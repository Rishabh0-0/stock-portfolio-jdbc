package com.portfolio.model;

public class Stock {
    private int stock_id;
    private String symbol;
    private String company_name;
    private double currentPrice;

    public Stock() {
    }

    public Stock(int stock_id, String symbol, String company_name, double currentPrice) {
        this.stock_id = stock_id;
        this.symbol = symbol;
        this.company_name = company_name;
        this.currentPrice = currentPrice;
    }

    public Stock(String symbol, String company_name, double currentPrice) {
        this.symbol = symbol;
        this.company_name = company_name;
        this.currentPrice = currentPrice;
    }

    public int getStock_id() {
        return stock_id;
    }

    public void setStock_id(int stock_id) {
        this.stock_id = stock_id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "stock_id=" + stock_id +
                ", symbol='" + symbol + '\'' +
                ", company_name='" + company_name + '\'' +
                ", currentPrice=" + currentPrice +
                '}';
    }
}
