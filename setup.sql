-- Database setup script for Stock Portfolio Management System
-- Run this script to create the database and tables

-- Create database
CREATE DATABASE IF NOT EXISTS stock_portfolio;
USE stock_portfolio;

-- Create tables (same as schema.sql)
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    wallet_balance DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE stocks (
    stock_id INT AUTO_INCREMENT PRIMARY KEY,
    symbol VARCHAR(10) UNIQUE NOT NULL,
    company_name VARCHAR(100) NOT NULL,
    current_price DECIMAL(10,2) NOT NULL
);

CREATE TABLE transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    stock_id INT,
    type ENUM('BUY','SELL') NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL, -- price at transaction time
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (stock_id) REFERENCES stocks(stock_id)
);

CREATE TABLE holdings (
    holding_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    stock_id INT,
    quantity INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (stock_id) REFERENCES stocks(stock_id),
    UNIQUE(user_id, stock_id) -- one record per stock per user
);

CREATE TABLE watchlist (
    watch_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    stock_id INT,
    added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (stock_id) REFERENCES stocks(stock_id),
    UNIQUE(user_id, stock_id)
);

-- Insert sample stocks
INSERT INTO stocks (symbol, company_name, current_price) VALUES
('AAPL', 'Apple Inc.', 150.25),
('GOOGL', 'Alphabet Inc.', 2800.50),
('MSFT', 'Microsoft Corporation', 300.75),
('TSLA', 'Tesla Inc.', 800.00),
('AMZN', 'Amazon.com Inc.', 3200.25),
('META', 'Meta Platforms Inc.', 250.80),
('NVDA', 'NVIDIA Corporation', 450.60),
('NFLX', 'Netflix Inc.', 400.30);

-- Insert sample users (passwords are hashed versions of 'password123')
INSERT INTO users (username, email, password, wallet_balance) VALUES
('john_doe', 'john@example.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewdBPj4/LewdBPj4', 10000.00),
('jane_smith', 'jane@example.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewdBPj4/LewdBPj4', 15000.00),
('bob_wilson', 'bob@example.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewdBPj4/LewdBPj4', 5000.00);

SELECT 'Database setup completed successfully!' as status;
