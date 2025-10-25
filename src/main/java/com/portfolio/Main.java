package com.portfolio;

import com.portfolio.dao.HoldingDAO;
import com.portfolio.dao.StockDAO;
import com.portfolio.dao.TransactionDAO;
import com.portfolio.dao.UserDAO;
import com.portfolio.model.Stock;
import com.portfolio.model.Transaction;
import com.portfolio.model.User;
import com.portfolio.service.PortfolioService;
import com.portfolio.service.StockService;
import com.portfolio.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser = null;

    public static void main(String[] args) {
        System.out.println("=== Stock Portfolio Management System ===");
        System.out.println("Welcome to the Stock Portfolio Management System!");
        
        // Initialize sample data
        initializeSampleData();
        
        boolean running = true;
        while (running) {
            if (currentUser == null) {
                running = showLoginMenu();
            } else {
                running = showMainMenu();
            }
        }
        
        System.out.println("Thank you for using Stock Portfolio Management System!");
        scanner.close();
    }

    private static boolean showLoginMenu() {
        System.out.println("\n=== LOGIN MENU ===");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
        
        int choice = getIntInput();
        
        switch (choice) {
            case 1:
                handleLogin();
                break;
            case 2:
                handleRegister();
                break;
            case 3:
                return false;
            default:
                System.out.println("Invalid option. Please try again.");
        }
        return true;
    }

    private static boolean showMainMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("Welcome, " + currentUser.getUsername() + "!");
        System.out.println("Wallet Balance: $" + String.format("%.2f", currentUser.getWallet_balance()));
        System.out.println("\n1. View Portfolio");
        System.out.println("2. Buy Stock");
        System.out.println("3. Sell Stock");
        System.out.println("4. View All Stocks");
        System.out.println("5. View Transaction History");
        System.out.println("6. Add Funds");
        System.out.println("7. Logout");
        System.out.print("Choose an option: ");
        
        int choice = getIntInput();
        
        switch (choice) {
            case 1:
                PortfolioService.viewPortfolio(currentUser.getUser_id());
                break;
            case 2:
                handleBuyStock();
                break;
            case 3:
                handleSellStock();
                break;
            case 4:
                StockService.listAllStocks();
                break;
            case 5:
                viewTransactionHistory();
                break;
            case 6:
                handleAddFunds();
                break;
            case 7:
                currentUser = null;
                System.out.println("Logged out successfully!");
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
        return true;
    }

    private static void handleLogin() {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        if (UserService.login(email, password)) {
            currentUser = UserDAO.getUserByEmail(email);
        }
    }

    private static void handleRegister() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter initial wallet balance: $");
        double balance = getDoubleInput();
        
        User newUser = new User(username, email, password);
        newUser.setWallet_balance(balance);
        UserService.registerUser(newUser);
    }

    private static void handleBuyStock() {
        System.out.print("Enter stock symbol: ");
        String symbol = scanner.nextLine().toUpperCase();
        System.out.print("Enter quantity to buy: ");
        int quantity = getIntInput();
        
        PortfolioService.buyStock(currentUser.getUser_id(), symbol, quantity);
        
        // Refresh user data
        currentUser = UserDAO.getUserById(currentUser.getUser_id());
    }

    private static void handleSellStock() {
        System.out.print("Enter stock symbol: ");
        String symbol = scanner.nextLine().toUpperCase();
        System.out.print("Enter quantity to sell: ");
        int quantity = getIntInput();
        
        PortfolioService.sellStock(currentUser.getUser_id(), symbol, quantity);
        
        // Refresh user data
        currentUser = UserDAO.getUserById(currentUser.getUser_id());
    }

    private static void viewTransactionHistory() {
        List<Transaction> transactions = TransactionDAO.getTransactionsByUser(currentUser.getUser_id());
        
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
        
        System.out.printf("%-15s %-10s %-10s %-10s %-15s %-20s%n", 
            "TRANSACTION ID", "STOCK", "TYPE", "QUANTITY", "PRICE", "TIMESTAMP");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        
        for (Transaction txn : transactions) {
            Stock stock = StockDAO.getStockById(txn.getStock_id());
            String stockSymbol = stock != null ? stock.getSymbol() : "Unknown";
            System.out.printf("%-15d %-10s %-10s %-10d %-15.2f %-20s%n",
                txn.getTransaction_id(), stockSymbol, txn.getType(), 
                txn.getQuantity(), txn.getPrice(), txn.getTimestamp());
        }
    }

    private static void handleAddFunds() {
        System.out.print("Enter amount to add: $");
        double amount = getDoubleInput();
        
        if (amount > 0) {
            if (UserService.addFunds(currentUser.getUser_id(), amount)) {
                System.out.println("Funds added successfully!");
                // Refresh user data
                currentUser = UserDAO.getUserById(currentUser.getUser_id());
            } else {
                System.out.println("Failed to add funds.");
            }
        } else {
            System.out.println("Amount must be positive.");
        }
    }

    private static void initializeSampleData() {
        System.out.println("Initializing sample data...");
        
        // Add sample stocks
        Stock[] sampleStocks = {
            new Stock("AAPL", "Apple Inc.", 150.25),
            new Stock("GOOGL", "Alphabet Inc.", 2800.50),
            new Stock("MSFT", "Microsoft Corporation", 300.75),
            new Stock("TSLA", "Tesla Inc.", 800.00),
            new Stock("AMZN", "Amazon.com Inc.", 3200.25),
            new Stock("META", "Meta Platforms Inc.", 250.80),
            new Stock("NVDA", "NVIDIA Corporation", 450.60),
            new Stock("NFLX", "Netflix Inc.", 400.30)
        };
        
        for (Stock stock : sampleStocks) {
            if (StockDAO.getStockBySymbol(stock.getSymbol()) == null) {
                StockDAO.addStock(stock);
            }
        }
        
        System.out.println("Sample data initialized successfully!");
    }

    private static int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    private static double getDoubleInput() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }
}
