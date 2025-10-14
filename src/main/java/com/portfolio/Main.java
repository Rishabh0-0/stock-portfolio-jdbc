package com.portfolio;

import com.portfolio.dao.HoldingDAO;
import com.portfolio.dao.StockDAO;
import com.portfolio.dao.UserDAO;
import com.portfolio.model.Stock;
import com.portfolio.model.User;
import com.portfolio.service.PortfolioService;
import com.portfolio.service.StockService;
import com.portfolio.service.UserService;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        UserService.addFunds(8, 500);

        PortfolioService.buyStock(8, "AAPL", 2);


    }
}
