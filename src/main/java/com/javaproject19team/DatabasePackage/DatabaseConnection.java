/*
DatabaseConnection:
Цей клас призначений для ініціалізації бази даних та таблиць.
*/

package com.javaproject19team.DatabasePackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Підключення до БД
/**
 * Клас, який забезпечує підключення до бази даних.
 */

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "student";


    /**
     * Метод, що повертає об'єкт Connection для взаємодії з базою даних.
     *
     * @return З'єднання з базою даних
     *
     * @throws SQLException Якщо виникає помилка під час підключення до бази даних
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}

