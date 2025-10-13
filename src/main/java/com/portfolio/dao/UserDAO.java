package com.portfolio.dao;

import com.portfolio.model.User;
import com.portfolio.util.DbUtil;
import com.portfolio.util.PasswordUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO {
    public static boolean addUser(User user){
        String addUserSql = "INSERT INTO users (username, email, password, wallet_balance, created_at)" +
                "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection conn = DbUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(addUserSql);

        ) {
            String hashedPassword = PasswordUtil.hashPassword(user.getPassword());

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, hashedPassword);
            ps.setDouble(4, user.getWallet_balance());
            ps.setTimestamp(5, Timestamp.valueOf(user.getCreated_at()));

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                return true;
            }

        } catch (Exception e) {
            System.out.println("Error message: " + e.getMessage());
        }
        return false;
    }

    public static User getUserById(int id){
        String sql = "SELECT * FROM users WHERE user_id = ?";

        try (
                Connection conn = DbUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ){
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    return getUser(rs);
                }
            }

        } catch (SQLException e){
            System.out.println("Error message: " + e.getMessage());
        }

        return null;
    }

    public static User getUserByUsername(String username){
        String sql = "SELECT * FROM users WHERE username = ?";

        try (
                Connection conn = DbUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ){
            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    return getUser(rs);
                }
            }

        } catch (SQLException e){
            System.out.println("Error message: " + e.getMessage());
        }

        return null;
    }

    public static User getUserByEmail(String email){
        String sql = "SELECT * FROM users WHERE email = ?";

        try (
                Connection conn = DbUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ){
            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    return getUser(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error message: " + e.getMessage());
        }

        return null;
    }

    public static List<User> fetchAllUsers() {
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

    public static boolean updateUser(User user) {
        String sql = "UPDATE Users SET username = ?, email = ?, password = ?, wallet_balance = ? WHERE user_id = ?";

        try (
                Connection conn = DbUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setDouble(4, user.getWallet_balance());
            stmt.setInt(5, user.getUser_id());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error message: " + e.getMessage());
            return false;
        }
    }


    public static boolean deleteUser(int id) {
        String sql = "DELETE FROM Users WHERE user_id = ?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error message: " + e.getMessage());
            return false;
        }
    }

    private static User getUser(ResultSet rs) throws SQLException {
        int userId = rs.getInt("user_id");
        String username = rs.getString("username");
        String email = rs.getString("email");
        String password = rs.getString("password");
        double walletBalance = rs.getDouble("wallet_balance");
        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();

        return new User(userId, username, email, password, walletBalance, createdAt);
    }

}
