package com.portfolio.service;

import com.portfolio.dao.StockDAO;
import com.portfolio.model.Stock;

import java.util.List;

public class StockService {
    public static void listALlStocks() {
        List<Stock> allStocks = StockDAO.getAllStocks();

        allStocks.forEach(System.out::println);
    }

    public static void addNewStock(Stock stock) {
        StockDAO.addStock(stock);
    }

    public static void updateStockPrice(String symbol, double price) {
        Stock stock = StockDAO.getStockBySymbol(symbol);
        if (stock != null) {
            stock.setCurrentPrice(price);
            StockDAO.updateStock(stock);
        }
    }
}


