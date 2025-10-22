package com.portfolio.service;

import com.portfolio.dao.UserDAO;
import com.portfolio.model.User;
import com.portfolio.util.PasswordUtil;
import com.portfolio.util.SessionManager;

public class UserService {

    public static void registerUser(User user) {
        String email = user.getEmail();

        if (UserDAO.getUserByEmail(email) != null) {
            System.out.println("Account already exist with this email!");
            return;
        }

        user.setUsername(generateUniqueUsername());

        UserDAO.addUser(user);
        System.out.println("User registered Successfully!");
    }

    public static boolean login(String email, String password) {
        User user = UserDAO.getUserByEmail(email);

        if (user == null) {
            System.out.println("Account with this email does not exist!");
            return false;
        }

        if (!PasswordUtil.verifyPassword(password, PasswordUtil.hashPassword(user.getPassword()))) {
            System.out.println("Incorrect Password! Please try again.");
            return false;
        }

        SessionManager.login(user);
        System.out.println(user.getUsername() + "Successfully Logged In..");
        return true;
    }

    public static boolean addFunds(int userId, double amount) {
        User user = UserDAO.getUserById(userId);

        if (user == null) {
            System.out.println("Account does not exist!");
            return false;
        }

        user.setWallet_balance(user.getWallet_balance() + amount);
        return UserDAO.updateUser(user);
    }

    private static String generateUniqueUsername() {
        return "guest_" + System.currentTimeMillis();
    }
}
