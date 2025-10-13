package com.portfolio.dao;

import com.portfolio.model.Holding;
import com.portfolio.util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HoldingDAO {
    public boolean addOrUpdateHolding(int userId, int stockId, int quantity) {
        // check if the holding already exists
        String sql1 = "SELECT holding_id FROM holdings WHERE user_id = ? AND stock_id = ?";
        String sql2 = "UPDATE holdings SET quantity = quantity + ? WHERE holding_id = ?";
        String sql3 = "INSERT INTO holdings(user_id, stock_id, quantity) VALUES (?, ?, ?)";
        String sql4 = "DELETE FROM holdings WHERE user_id = ? AND stock_id = ? AND quantity <= 0";
        int holdingId = -1;


        try (Connection conn = DbUtil.getConnection()) {
            conn.setAutoCommit(false);

            try(
                    PreparedStatement ps1 = conn.prepareStatement(sql1);
                    PreparedStatement ps2 = conn.prepareStatement(sql2);
                    PreparedStatement ps3 = conn.prepareStatement(sql3);
                    PreparedStatement ps4 = conn.prepareStatement(sql4)
            ) {
                ps1.setInt(1, userId);
                ps1.setInt(2, stockId);

                try (ResultSet rs = ps1.executeQuery()) {
                    if (rs.next()) {
                        holdingId = rs.getInt(1);
                    }
                }

                if (holdingId != -1) {
                    ps2.setInt(1, quantity);
                    ps2.setInt(2, holdingId);
                    ps2.executeUpdate();
                } else {
                    ps3.setInt(1, userId);
                    ps3.setInt(2, stockId);
                    ps3.setInt(3, quantity);

                    ps3.executeUpdate();
                }

                ps4.setInt(1, userId);
                ps4.setInt(2, stockId);
                ps4.executeUpdate();

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Transaction rolled back. Error: " + e.getMessage());
                return false;
            } finally {
                conn.setAutoCommit(true);
            }

        } catch (Exception e) {
            System.out.println("Error getting connection: " + e.getMessage());
            return false;
        }

        // could use ON DUPLICATE KEY UPDATE in mysql also

    }


    public Holding getHolding(int userId, int stockId) {
        String sql = "SELECT * FROM holdings WHERE user_id = ? AND stock_id = ?";

        try(
                Connection conn = DbUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, userId);
            ps.setInt(2, stockId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return getHoldingFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error Message: " + e.getMessage());
        }

        return null;
    }

    public List<Holding> getHoldingsByUser(int userId) {
        List<Holding> result = new ArrayList<>();
        String sql = "SELECT * FROM holdings WHERE user_id = ?";

        try(
                Connection conn = DbUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(getHoldingFromResultSet(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error Message: " + e.getMessage());
        }

        return result;
    }

    public boolean deleteHolding(int userId, int stockId) {
        String sql = "DELETE FROM holdings WHERE user_id = ? AND stock_id = ?";

        try(
                Connection conn = DbUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, userId);
            ps.setInt(2, stockId);

            int rowsDeleted = ps.executeUpdate();

            if (rowsDeleted > 0) return true;

        } catch (SQLException e) {
            System.out.println("Error Message: " + e.getMessage());
        }

        return false;
    }


    private Holding getHoldingFromResultSet(ResultSet rs) throws SQLException {
        int holding_id = rs.getInt("holding_id");
        int user_id = rs.getInt("user_id");
        int stock_id = rs.getInt("stock_id");
        int quantity = rs.getInt("quantity");

        return new Holding(holding_id, user_id, stock_id, quantity);
    }
}
