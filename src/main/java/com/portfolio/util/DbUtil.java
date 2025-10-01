package com.portfolio.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DbUtil {
    private static Connection conn;

    public static Connection getConnection() {
        if (conn != null) return conn;

        Properties props = new Properties();
        try (InputStream input = DbUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            props.load(input);

            String url = props.getProperty("db.url");
            String username = props.getProperty("db.username");
            String password = props.getProperty("db.password");
            String driver = props.getProperty("db.driver");

            Class.forName(driver);

            conn = DriverManager.getConnection(url, username, password);

        } catch (Exception e) {
            System.out.println("Error connecting to DB");
            e.printStackTrace();
        }

        return conn;
    }
}
