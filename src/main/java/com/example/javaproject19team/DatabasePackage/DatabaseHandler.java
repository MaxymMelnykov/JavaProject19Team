package com.example.javaproject19team.DatabasePackage;

import com.example.javaproject19team.СlientPackage.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHandler {
    private static final String INSERT_CLIENT_SQL = "INSERT INTO Clients (name, surname, email, phone) VALUES (?, ?, ?, ?)";
    private static final String INSERT_ROOM_SQL = "INSERT INTO Rooms (number, type, price, details) VALUES (?, ?, ?, ?)";
    private static final String SELECT_CLIENT_SQL = "SELECT * FROM Clients WHERE clientID = ?";

    public static void saveClient(String clientName, String clientSurname, String email, String phone) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CLIENT_SQL)) {
            preparedStatement.setString(1, clientName);
            preparedStatement.setString(2, clientSurname);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, phone);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void saveRoom(String number, String type, int price, String details) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ROOM_SQL)) {
            preparedStatement.setString(1, number);
            preparedStatement.setString(2, type);
            preparedStatement.setInt(3, price);
            preparedStatement.setString(4, details);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //SAVE RESERVATION



    //
    public static Client getClientFromDB(int ClientID) {
        Client client = null;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLIENT_SQL)) {
            preparedStatement.setInt(1,ClientID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Извлекаем данные о клиенте из результата запроса
                    String name = resultSet.getString("name");
                    String surname = resultSet.getString("surname");
                    String email = resultSet.getString("email");
                    String phoneNumber = resultSet.getString("phone");

                    // Создаем экземпляр класса Client
                    client = new Client(name, surname, email, phoneNumber);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }
}

