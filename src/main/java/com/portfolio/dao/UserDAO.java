package com.portfolio.dao;

import com.portfolio.model.User;
import com.portfolio.util.DbUtil;

import java.sql.*;

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
            System.out.println("User created successfully!");

        } catch (Exception e) {
            System.out.println("Error message: " + e.getMessage());
            e.printStackTrace();
        }


        return generatedId;
    }

    public User getUserById(int id){
        String sql = "SELECT * FROM users WHERE user_id = ?";
        User user = new User();

        try (
                Connection conn = DbUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ){
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    user.setUser_id(rs.getInt("user_id"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
                }
            }

        } catch (SQLException e){
            System.out.println("Error message: " + e.getMessage());
        }

        return user;
    }
}
