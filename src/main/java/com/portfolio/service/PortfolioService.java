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
}
