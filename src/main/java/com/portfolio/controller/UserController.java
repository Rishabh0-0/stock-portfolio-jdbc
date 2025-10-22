package com.portfolio.controller;

import com.portfolio.model.User;
import com.portfolio.service.UserService;

import java.util.regex.Pattern;

public class UserController {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s]).{8,}$");

    public static void registerUser(String email, String password) {
        if (!isEmailValid(email)) {
            System.out.println("Please enter a valid email address (e.g., name@example.com).");
            return;
        }

        if (!isPasswordStrong(password)) {
            System.out.println("Enter a strong password with letters, numbers, and symbols.");
            return;
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        UserService.registerUser(user);
        System.out.println("Registration successful!");
    }

    public static void loginUser(String email, String password) {
        if (!isEmailValid(email)) {
            System.out.println("Please enter a valid email address");
            return;
        }

        if (UserService.login(email, password)) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Please enter valid email and password!");
        }
    }


    private static boolean isEmailValid(String email) {
        return !email.isEmpty() && EMAIL_PATTERN.matcher(email).matches();
    }

    private static boolean isPasswordStrong(String password) {
        return !password.isEmpty() && PASSWORD_PATTERN.matcher(password).matches();
    }
}
