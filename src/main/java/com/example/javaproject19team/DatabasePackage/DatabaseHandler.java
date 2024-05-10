package com.example.javaproject19team.DatabasePackage;

import com.example.javaproject19team.RoomPackage.Room;
import com.example.javaproject19team.СlientPackage.Client;
import com.example.javaproject19team.СlientPackage.ClientListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHandler {
    private static final String INSERT_CLIENT_SQL = "INSERT INTO Clients (name, surname, email, phone) VALUES (?, ?, ?, ?)";
    private static final String INSERT_ROOM_SQL = "INSERT INTO Rooms (number, type, price, details) VALUES (?, ?, ?, ?)";
    //!!!!!!!!!!!!!!!!!!!!//
    private static final String INSERT_RESERVATION_SQL = "INSERT INTO Reservations (clientID, roomID, arrivalDate, departureDate, status) VALUES (?,?,)";

    //!!!!!!!!!!!!!!!!!!!!//
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
    /*SAVE RESERVATION
    public static void saveReservation(Client client, Room room, String arrivalDate, String departureDate, boolean status){
        try (Connection connection = DatabaseConnection.getConnection();

    } catch (SQLException e) {
            e.printStackTrace();
        }
  */

    public static Client getClientFromDB(int ClientID) {
        Client client = null;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLIENT_SQL)) {
            preparedStatement.setInt(1, ClientID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Извлекаем данные о клиенте из результата запроса
                    StringProperty nameProperty = new SimpleStringProperty(resultSet.getString("name"));
                    StringProperty surnameProperty = new SimpleStringProperty(resultSet.getString("surname"));
                    StringProperty emailProperty = new SimpleStringProperty(resultSet.getString("email"));
                    StringProperty phoneNumberProperty = new SimpleStringProperty(resultSet.getString("phone"));

                    // Создаем экземпляр класса Client
                    client = new Client(nameProperty, surnameProperty, emailProperty, phoneNumberProperty);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }
}

