package com.cnpm.utilities;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection connection;

    public static Connection getConnection() {
        String dbName = "csdl8";
        String dbUser = "root";
        String dbPassword = "";
        String url = "jdbc:mysql://localhost/" + dbName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, dbUser, dbPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }
}
