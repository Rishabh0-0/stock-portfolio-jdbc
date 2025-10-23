package com.portfolio.controller;

import com.portfolio.model.User;

import java.util.Scanner;

public class LoginPage {
    private static final Scanner sc = new Scanner(System.in);

    public static void display() {
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("====================================");
            System.out.println("        STOCK TRADING SYSTEM        ");
            System.out.println("====================================");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.println("====================================");
            System.out.print("Select an option (1-3): ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    login();
                    break;
                case "2":
                    register();
                    break;
                case "3":
                    exit();
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice! Please select a valid option (1-6).");
            }
            System.out.println();
        }

        sc.close();
    }

    private static void login() {
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        UserController.loginUser(email, password);
    }

    private static void register() {
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        UserController.registerUser(email, password);
    }

    private static void exit() {
        UserController.logout();
    }

}
