package com.portfolio;

import com.portfolio.dao.UserDAO;
import com.portfolio.model.User;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        UserDAO userDao = new UserDAO();

//        userDao.addUser(new User("rishabhraj", "abc@123gmail.com", "abc123", 0, LocalDateTime.now()));
        System.out.println(userDao.getUserById(8));
    }
}
