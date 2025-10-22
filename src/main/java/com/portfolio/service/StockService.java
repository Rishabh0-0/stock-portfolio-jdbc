package com.portfolio.service;

import com.portfolio.dao.StockDAO;
import com.portfolio.model.Stock;

import java.util.List;

public class StockService {
    public static void listAllStocks() {
        List<Stock> allStocks = StockDAO.getAllStocks();

        System.out.printf("%-10s %-25s %-15s%n", "SYMBOL", "COMPANY NAME", "PRICE");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        for (Stock stock: allStocks) {
            System.out.printf("%-10s %-25s %-15.2f%n", stock.getSymbol(), stock.getCompany_name(), stock.getCurrentPrice());
        }

    }

    public static void getStockBySymbol(String symbol) {
        Stock stock = StockDAO.getStockBySymbol(symbol);

        if (stock == null) {
            System.out.println("Invalid stock symbol");
            return;
        }

        System.out.printf("%-10s %-25s %-15s%n", "SYMBOL", "COMPANY NAME", "PRICE");
        System.out.printf("%-10s %-25s %-15.2f%n", stock.getSymbol(), stock.getCompany_name(), stock.getCurrentPrice());
    }

    public static void addNewStock(Stock stock) {
        StockDAO.addStock(stock);
    }

    public static void updateStockPrice(String symbol, double price) {
        Stock stock = StockDAO.getStockBySymbol(symbol);
        if (stock == null) {
            throw new RuntimeException("Stock with this symbol doesn't exist");
        }
        stock.setCurrentPrice(price);
        StockDAO.updateStock(stock);
    }
}


