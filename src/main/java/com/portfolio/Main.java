package com.portfolio;

import com.portfolio.dao.HoldingDAO;
import com.portfolio.dao.StockDAO;
import com.portfolio.dao.UserDAO;
import com.portfolio.model.Stock;
import com.portfolio.model.User;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        UserDAO userDao = new UserDAO();

//        userDao.addUser(new User("rishabhraj", "abc@123gmail.com", "abc123", 0, LocalDateTime.now()));
//        userDao.getUserById(9).ifPresent(System.out::println);
//        userDao.getUserByEmail("abc@123gmail.com").ifPresent(System.out::println);

//        userDao.fetchAllUsers().forEach(System.out::println);
//
//        System.out.println(userDao.deleteUser(10) ? "userDeleted" : "userNotFound");

//        User user = userDao.getUserById(11).get();
//        user.setUsername("pogoli");
//        userDao.updateUser(user);


//        StockDAO stockDao = new StockDAO();
//
//        stockDao.addStock(new Stock("BEL", "Bharat Electronics", 500.26));
//        stockDao.getAllStocks().forEach(System.out::println);

//        userDao.fetchAllUsers().forEach(System.out::println);

        HoldingDAO holdingDao = new HoldingDAO();

//        System.out.println(holdingDao.addOrUpdateHolding(1, 2, 20));

        System.out.println(holdingDao.deleteHolding(1, 2));

        holdingDao.getHoldingsByUser(1).forEach(h -> {
            System.out.println("Holding ID: " + h.getHolding_id());
            System.out.println("User ID: " + h.getUser_id());
            System.out.println("Stock ID: " + h.getStock_id());
            System.out.println("Quantity: " + h.getQuantity());
            System.out.println();
        });

    }
}
