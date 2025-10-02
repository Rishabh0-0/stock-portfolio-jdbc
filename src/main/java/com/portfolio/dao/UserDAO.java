package com.portfolio.dao;

import com.portfolio.model.User;
import com.portfolio.util.DbUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO {
    public int addUser(User user){
        String addUserSql = "INSERT INTO users (username, email, password, wallet_balance, created_at)" +
                "VALUES (?, ?, ?, ?, ?)";
        int generatedId = -1;

        try (
                Connection conn = DbUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(addUserSql, Statement.RETURN_GENERATED_KEYS);

        ) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setDouble(4, user.getWallet_balance());
            ps.setTimestamp(5, Timestamp.valueOf(user.getCreated_at()));

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()){
                    if (rs.next()){
                        generatedId = rs.getInt(1);
                        System.out.println("User created successfully with ID: " + generatedId);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error message: " + e.getMessage());
            e.printStackTrace();
        }


        return generatedId;
    }

    public Optional<User> getUserById(int id){
        String sql = "SELECT * FROM users WHERE user_id = ?";

        try (
                Connection conn = DbUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ){
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    return Optional.of(getUser(rs));
                }
            }

        } catch (SQLException e){
            System.out.println("Error message: " + e.getMessage());
        }

        return Optional.empty();
    }

    public Optional<User> getUserByEmail(String email){
        String sql = "SELECT * FROM users WHERE email = ?";

        try (
                Connection conn = DbUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ){
            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    return Optional.of(getUser(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error message: " + e.getMessage());
        }

        return Optional.empty();
    }

    public List<User> fetchAllUsers() {
        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM users";

        try (
                Connection conn = DbUtil.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
        ) {
            while (rs.next()){
                users.add(getUser(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error message: " + e.getMessage());
        }

        return users;
    }

    private User getUser(ResultSet rs) throws SQLException {
        int userId = rs.getInt("user_id");
        String username = rs.getString("username");
        String email = rs.getString("email");
        String password = rs.getString("password");
        double walletBalance = rs.getDouble("wallet_balance");
        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();

        return new User(userId, username, email, password, walletBalance, createdAt);
    }

}
