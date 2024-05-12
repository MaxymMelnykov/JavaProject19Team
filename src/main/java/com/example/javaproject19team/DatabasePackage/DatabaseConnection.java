package com.example.javaproject19team.DatabasePackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Підключення до БД
public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "student";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}

