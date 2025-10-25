package com.portfolio.service;

import com.portfolio.dao.HoldingDAO;
import com.portfolio.dao.StockDAO;
import com.portfolio.dao.TransactionDAO;
import com.portfolio.dao.UserDAO;
import com.portfolio.model.Holding;
import com.portfolio.model.Stock;
import com.portfolio.model.Transaction;
import com.portfolio.model.User;

import java.time.LocalDateTime;
import java.util.List;

public class PortfolioService {
    public static void buyStock(int userId, String symbol, int qty) {
        // check availability of stock
        User user = UserDAO.getUserById(userId);
        if (user == null) {
            System.out.println("Invalid user");
            return;
        }
        Stock stock = StockDAO.getStockBySymbol(symbol);
        if (stock == null) {
            System.out.println("Stock is not available");
            return;
        }
        // check if user have enough balance
        double amountReq = stock.getCurrentPrice() * qty;
        if (user.getWallet_balance() < amountReq) {
            System.out.println("Insufficient Wallet balance!");
            return;
        }

        // Deduct amount from users wallet
        user.setWallet_balance(user.getWallet_balance() - (qty * stock.getCurrentPrice()));
        UserDAO.updateUser(user);

        // update holdings
        HoldingDAO.addOrUpdateHolding(user.getUser_id(), stock.getStock_id(), qty);

        // add in the transactions
        Transaction tnx = new Transaction(user.getUser_id(), stock.getStock_id(), "BUY", qty, stock.getCurrentPrice(), LocalDateTime.now());
        TransactionDAO.addTransaction(tnx);
    }

    public static void sellStock(int userId, String symbol, int qty) {
        // Get user availability
        User user = UserDAO.getUserById(userId);
        if (user == null) {
            System.out.println("Invalid user");
            return;
        }

        // Check stock availability
        Stock stock =  StockDAO.getStockBySymbol(symbol);
        if (stock == null) {
            System.out.println("Stock is not available");
            return;
        }

        // Check holding of user
        Holding stockHolding = HoldingDAO.getHolding(user.getUser_id(), stock.getStock_id());
        if (stockHolding == null) {
            System.out.println("User doesn't own this stock");
            return;
        }

        // Check quantity available to sell
        int holdingQty = stockHolding.getQuantity();
        if (qty > holdingQty) {
            System.out.println("Invalid Quantity! cannot sell more than " + holdingQty);
            return;
        }

        // credit amount into users wallet
        user.setWallet_balance(user.getWallet_balance() + (qty * stock.getCurrentPrice()));
        UserDAO.updateUser(user);

        // Update the holdings
        HoldingDAO.addOrUpdateHolding(user.getUser_id(), stock.getStock_id(), holdingQty - qty);

        // Add in transactions
        Transaction tnx = new Transaction(user.getUser_id(), stock.getStock_id(), "SELL", qty, stock.getCurrentPrice(), LocalDateTime.now());
        TransactionDAO.addTransaction(tnx);
    }

    public static void viewPortfolio(int userId) {
        List<Holding> holdings = HoldingDAO.getHoldingsByUser(userId);

        if (holdings.isEmpty()) {
            System.out.println("No stocks in your portfolio.");
            return;
        }

        System.out.printf("%-10s %-25s %-10s %-15s %-15s%n", "SYMBOL", "COMPANY NAME", "QUANTITY", "CURRENT PRICE", "TOTAL VALUE");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        
        double totalValue = 0.0;
        for (Holding holding : holdings) {
            Stock stock = StockDAO.getStockById(holding.getStock_id());
            if (stock != null) {
                double totalStockValue = stock.getCurrentPrice() * holding.getQuantity();
                totalValue += totalStockValue;
                System.out.printf("%-10s %-25s %-10d %-15.2f %-15.2f%n", 
                    stock.getSymbol(), stock.getCompany_name(), holding.getQuantity(), 
                    stock.getCurrentPrice(), totalStockValue);
            }
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.printf("Total Portfolio Value: $%.2f%n", totalValue);
    }
}
