package com.portfolio.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DbUtil {
    public static Connection getConnection() {
        Properties props = new Properties();
        try (InputStream input = DbUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) throw new RuntimeException("db.properties not found in classpath");
            props.load(input);

            String url = props.getProperty("db.url");
            String username = props.getProperty("db.username");
            String password = props.getProperty("db.password");
            String driver = props.getProperty("db.driver");

            Class.forName(driver);
            return DriverManager.getConnection(url, username, password);

        } catch (Exception e) {
            throw new RuntimeException("Error connecting to DB", e);
        }
    }
}
