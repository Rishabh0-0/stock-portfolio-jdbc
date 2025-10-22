package com.portfolio.controller;

import java.util.Scanner;

public class MainMenu {
    private static final Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        boolean exit = false;

        while (!exit) {
            System.out.println("====================================");
            System.out.println("        STOCK TRADING SYSTEM        ");
            System.out.println("====================================");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. View Portfolio");
            System.out.println("4. Buy");
            System.out.println("5. Sell");
            System.out.println("6. Exit");
            System.out.println("====================================");
            System.out.print("Select an option (1-6): ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    login();
                    break;
                case "2":
                    register();
                    break;
                case "3":
                    viewPortfolio();
                    break;
                case "4":
                    buy();
                    break;
                case "5":
                    sell();
                    break;
                case "6":
                    System.out.println("Exiting.. ");
                    exit = true;
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

    }

    private static void viewPortfolio() {

    }

    private static void buy() {

    }

    private static void sell() {

    }

}
