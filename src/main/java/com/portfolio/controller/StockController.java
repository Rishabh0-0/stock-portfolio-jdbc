package com.portfolio.controller;

import com.portfolio.service.StockService;

public class StockController {
    public static void viewAllStock() {
        StockService.listAllStocks();
    }

    public static void searchStockBySymbol(String symbol) {
        if (symbol.length() > 10) {
            System.out.println("Please enter a valid symbol!");
            return;
        }

        StockService.getStockBySymbol(symbol.toUpperCase());
    }
}
