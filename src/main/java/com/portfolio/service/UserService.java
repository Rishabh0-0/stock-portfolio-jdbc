package com.portfolio.service;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import com.portfolio.dao.UserDAO;
import com.portfolio.model.User;
import com.portfolio.util.DbUtil;
import com.portfolio.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class UserService {
//    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
//    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[A-Za-z0-9_]{3,20}$");
//    private static final Pattern PASSWORD_PATTERN =
//            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$");


    public static void registerUser(User user) {
        String username = user.getUsername();
        String email = user.getEmail();

        if (UserDAO.getUserByUsername(username) != null) {
            System.out.println("Username already taken! choose a different username");
            return;
        }

        if (UserDAO.getUserByEmail(email) != null) {
            System.out.println("Account already exist with this email!");
            return;
        }

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

//    private static boolean usernameExists(String username) {
//        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
//
//        try(
//                Connection conn = DbUtil.getConnection();
//                PreparedStatement ps = conn.prepareStatement(sql);
//        ) {
//            ps.setString(1, username);
//
//            try (ResultSet rs = ps.executeQuery()) {
//                if (rs.next()) {
//                    return rs.getInt(1) > 0;
//                }
//            }
//
//            return false;
//        } catch (SQLException e) {
//            System.out.println("Error checking username " + e.getMessage());
//            return false;
//        }
//    }

//    private static boolean emailExists(String email) {
//        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
//
//        try(
//                Connection conn = DbUtil.getConnection();
//                PreparedStatement ps = conn.prepareStatement(sql);
//        ) {
//            ps.setString(1, email);
//
//            try (ResultSet rs = ps.executeQuery()) {
//                if (rs.next()) {
//                    return rs.getInt(1) > 0;
//                }
//            }
//
//            return false;
//        } catch (SQLException e) {
//            System.out.println("Error checking email " + e.getMessage());
//            return false;
//        }
//    }

//    private static boolean isUsernameValid(String username) {
//        return username.isEmpty() && USERNAME_PATTERN.matcher(username).matches();
//    }
//
//    private static boolean isPasswordValid(String password) {
//        return password.isEmpty() && PASSWORD_PATTERN.matcher(password).matches();
//    }
//
//    private static boolean isEmailValid(String email) {
//        return email.isEmpty() && EMAIL_PATTERN.matcher(email).matches();
//    }
}
