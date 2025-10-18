package com.portfolio.dao;

import com.portfolio.model.Transaction;
import com.portfolio.util.DbUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionDAO {
    // add transaction
    public static int addTransaction(Transaction txn){
        String sql = "INSERT INTO transactions (user_id, stock_id, type, quantity, price, timestamp)" +
                "VALUES (?, ?, ?, ?, ?, ?)";
        int generatedId = -1;

        try (
                Connection conn = DbUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ) {
            ps.setInt(1, txn.getUser_id());
            ps.setInt(2, txn.getStock_id());
            ps.setString(3, txn.getType());
            ps.setInt(4, txn.getQuantity());
            ps.setDouble(5, txn.getPrice());
            ps.setTimestamp(6, Timestamp.valueOf(txn.getTimestamp()));

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()){
                    if (rs.next()){
                        generatedId = rs.getInt(1);
                        System.out.println("Transaction added successfully with ID: " + generatedId);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error message: " + e.getMessage());
        }

        return generatedId;
    }

    // get transaction by id
    public static Transaction getTransactionById(int id){
        String sql = "SELECT * FROM transactions WHERE transaction_id = ?";

        try (
                Connection conn = DbUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ){
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    return getTransaction(rs);
                }
            }

        } catch (SQLException e){
            System.out.println("Error message: " + e.getMessage());
        }

        return null;
    }

    public static List<Transaction> getTransactionsByUser(int userId){
        List<Transaction> transactions = new ArrayList<>();

        String sql = "SELECT * FROM transactions WHERE user_id = ?";

        try (
                Connection conn = DbUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            try (ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    transactions.add(getTransaction(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error message: " + e.getMessage());
        }

        return transactions;
    }

    public static List<Transaction> getTransactionsByStock(int stock_id){
        List<Transaction> transactions = new ArrayList<>();

        String sql = "SELECT * FROM transactions WHERE stock_id = ?";

        try (
                Connection conn = DbUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            try (ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    transactions.add(getTransaction(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error message: " + e.getMessage());
        }

        return transactions;
    }

    public static List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();

        String sql = "SELECT * FROM transactions";

        try (
                Connection conn = DbUtil.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
        ) {
            while (rs.next()){
                transactions.add(getTransaction(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error message: " + e.getMessage());
        }

        return transactions;
    }

    private static Transaction getTransaction(ResultSet rs) throws SQLException {
        int transaction_id = rs.getInt("stock_id");
        int user_id = rs.getInt("symbol");
        int stock_id = rs.getInt("company_name");
        String type = rs.getString("current_price");
        int quantity = rs.getInt("quantity");
        double price = rs.getDouble("price");
        LocalDateTime timestamp = rs.getTimestamp("timestamp").toLocalDateTime();


        return new Transaction(transaction_id, user_id, stock_id, type, quantity, price, timestamp);
    }
}
