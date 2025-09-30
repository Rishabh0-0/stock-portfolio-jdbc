package com.portfolio.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DbUtil {
    private static Connection conn = null;

    public static Connection getConnection() {
        if (conn != null) return conn;

        try {
            Properties props = new Properties();
            InputStream input = DbUtil.class.getClassLoader().getResourceAsStream("db.properties");

            if (input == null) {
                throw new RuntimeException("db.properties not found in resources folder");
            }

            props.load(input);

            String url = props.getProperty("url");
            String username = props.getProperty("username");
            String password = props.getProperty("password");
            String driver = props.getProperty("driver");

            Class.forName(driver);

            conn = DriverManager.getConnection(url, username, password);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }
}
