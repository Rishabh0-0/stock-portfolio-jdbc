package com.portfolio;

import com.portfolio.dao.HoldingDAO;
import com.portfolio.dao.StockDAO;
import com.portfolio.dao.UserDAO;
import com.portfolio.model.Stock;
import com.portfolio.model.User;
import com.portfolio.service.UserService;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
//        UserService.registerUser(new User("raj", "abc@123", "123"));
        System.out.println(UserService.login("abc@123", "123"));

    }
}
