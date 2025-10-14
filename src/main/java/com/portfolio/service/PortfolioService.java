package com.portfolio.service;

import com.portfolio.dao.HoldingDAO;
import com.portfolio.dao.StockDAO;
import com.portfolio.dao.TransactionDAO;
import com.portfolio.dao.UserDAO;
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

        // update holdings
        HoldingDAO.addOrUpdateHolding(user.getUser_id(), stock.getStock_id(), qty);

        // add in the transactions
        Transaction tnx = new Transaction(user.getUser_id(), stock.getStock_id(), "BUY", qty, stock.getCurrentPrice(), LocalDateTime.now());
        TransactionDAO.addTransaction(tnx);
    }
}
