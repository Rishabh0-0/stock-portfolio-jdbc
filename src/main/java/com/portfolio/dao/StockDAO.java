package com.portfolio.dao;

import com.portfolio.model.Stock;
import com.portfolio.util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StockDAO {
    // add stock
    public int addStock(Stock stock){
        String sql = "INSERT INTO stocks (symbol, company_name, current_price)" +
                "VALUES (?, ?, ?)";
        int generatedId = -1;

        try (
                Connection conn = DbUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ) {
            ps.setString(1, stock.getSymbol());
            ps.setString(2, stock.getCompany_name());
            ps.setDouble(3, stock.getCurrentPrice());

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()){
                    if (rs.next()){
                        generatedId = rs.getInt(1);
                        System.out.println("Stock added successfully with ID: " + generatedId);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error message: " + e.getMessage());
        }

        return generatedId;
    }

    // get stock by id
    public Optional<Stock> getStockById(int id){
        String sql = "SELECT * FROM stocks WHERE stock_id = ?";

        try (
                Connection conn = DbUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ){
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    return Optional.of(getStock(rs));
                }
            }

        } catch (SQLException e){
            System.out.println("Error message: " + e.getMessage());
        }

        return Optional.empty();
    }

    // get stock by symbol
    public Optional<Stock> getStockBySymbol(String symbol){
        String sql = "SELECT * FROM stocks WHERE symbol = ?";

        try (
                Connection conn = DbUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ){
            ps.setString(1, symbol);

            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    return Optional.of(getStock(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error message: " + e.getMessage());
        }

        return Optional.empty();
    }

    // get all stock
    public List<Stock> getAllStocks() {
        List<Stock> stocks = new ArrayList<>();

        String sql = "SELECT * FROM stocks";

        try (
                Connection conn = DbUtil.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
        ) {
            while (rs.next()){
                stocks.add(getStock(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error message: " + e.getMessage());
        }

        return stocks;
    }

    // update stock price
    public boolean updateStock(Stock stock) {
        String sql = "UPDATE Stocks SET symbol = ?, company_name = ?, current_price = ? WHERE stock_id = ?";

        try (
                Connection conn = DbUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, stock.getSymbol());
            stmt.setString(2, stock.getCompany_name());
            stmt.setDouble(3, stock.getCurrentPrice());
            stmt.setInt(4, stock.getStock_id());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error message: " + e.getMessage());
            return false;
        }
    }

    // delete stock
    public boolean deleteStock(int id) {
        String sql = "DELETE FROM Stocks WHERE stock_id = ?";

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

    private Stock getStock(ResultSet rs) throws SQLException {
        int stockId = rs.getInt("stock_id");
        String symbol = rs.getString("symbol");
        String company_name = rs.getString("company_name");
        double currentPrice = rs.getDouble("current_price");


        return new Stock(stockId, symbol, company_name, currentPrice);
    }

}
