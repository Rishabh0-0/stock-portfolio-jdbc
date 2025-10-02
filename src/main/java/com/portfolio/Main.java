package com.portfolio;

import com.portfolio.dao.UserDAO;
import com.portfolio.model.User;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        UserDAO userDao = new UserDAO();

//        userDao.addUser(new User("rishabhraj", "abc@123gmail.com", "abc123", 0, LocalDateTime.now()));
        userDao.getUserById(8).ifPresent(System.out::println);
        userDao.getUserByEmail("abc@123gmail.com").ifPresent(System.out::println);
    }
}
