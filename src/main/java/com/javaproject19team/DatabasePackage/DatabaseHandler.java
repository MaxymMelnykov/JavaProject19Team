/*
DatabaseHandler:
Цей клас призначений для взаємодії з базою даних.
Він містить методи для збереження, видалення, оновлення та отримання даних з бази даних.
Крім того, він забезпечує підключення до бази даних та виконання SQL-запитів.
*/

package com.javaproject19team.DatabasePackage;

import com.javaproject19team.ReservationPackage.Reservation;
import com.javaproject19team.RoomPackage.Room;
import com.javaproject19team.СlientPackage.Client;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.*;
import java.time.LocalDate;

public class DatabaseHandler {

    // Запити до БД
    private static final String INSERT_CLIENT_SQL = "INSERT INTO Clients (name, surname, email, phone) VALUES (?, ?, ?, ?)";
    private static final String INSERT_ROOM_SQL = "INSERT INTO Rooms (number, type, price, details, roomStatus) VALUES (?, ?, ?, ?, ?)";
    private static final String INSERT_RESERVATION_SQL = "INSERT INTO Reservations (clientid, roomid, arrivaldate, departuredate,reservationstatus) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ID_FROM_CLIENTS_SQL = "SELECT ClientID FROM Clients where name = ? and surname = ? and email = ? and phone = ?";
    private static final String SELECT_ID_FROM_ROOMS_SQL = "SELECT roomid FROM Rooms where number = ? and type = ? and price = ? and details = ?";
    private static final String SELECT_CLIENT_SQL = "SELECT * FROM Clients WHERE clientID = ?";
    private static final String SELECT_ROOM_SQL = "SELECT * FROM Rooms WHERE roomID = ?";
    private static final String SELECT_RESERVATION_SQL = "SELECT * FROM Reservations WHERE reservationID = ?";
    private static final String SELECT_COUNT_ALL_CLIENTS_SQL = "SELECT COUNT(*) FROM clients";
    private static final String SELECT_COUNT_ALL_ROOMS_SQL = "SELECT COUNT(*) FROM Rooms";
    private static final String SELECT_COUNT_ALL_RESERVATIONS_SQL = "SELECT COUNT(*) FROM Reservations";
    private static final String SELECT_COUNT_ROOMS_RESERVATION_SQL = "SELECT COUNT(*) FROM Reservations WHERE roomID = ?";
    private static final String SELECT_ROOM_STATUS_SQL = "SELECT roomstatus FROM Rooms WHERE number = ?";
    private static final String UPDATE_STATUS_ROOMS_TO_FALSE_SQL = "UPDATE Rooms SET roomStatus = false where number = ?";
    private static final String UPDATE_STATUS_RESERVATIONS_TO_FALSE_SQL = "UPDATE Reservations SET reservationStatus = false where ClientID = ?";

    // Методи для рахунку клієнтів, номерів та резервацій

    // Метод виконує підрахунок кількості резервацій для вказаної кімнати.
    public static int countRoomsReservations(final int roomID) {
        int counter = 0;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COUNT_ROOMS_RESERVATION_SQL)) {
            preparedStatement.setInt(1, roomID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    counter = resultSet.getInt(1); // Отримати значення першого стовпця результату запиту
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return counter;
    }

    // Метод підраховує кількість кімнат у базі даних.
    public static int countRoomsFromDB() {
        int counter = 0;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COUNT_ALL_ROOMS_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                counter = resultSet.getInt(1); // Отримати значення першого стовпця результату запиту
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return counter;
    }

    // Метод підраховує кількість клієнтів у базі даних.
    public static int countClientsFromDB() {
        int counter = 0;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COUNT_ALL_CLIENTS_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                counter = resultSet.getInt(1); // Отримати значення першого стовпця результату запиту
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return counter;
    }

    // Метод підраховує кількість резервацій у базі даних.
    public static int countReservationsFromDB() {
        int counter = 0;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COUNT_ALL_RESERVATIONS_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                counter = resultSet.getInt(1); // Отримати значення першого стовпця результату запиту
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return counter;
    }

    // Методи для запису данних про клієнтів, номерів та резервацій до БД

    // Метод зберігає дані нового клієнта у базі даних.
    public static void saveClientDB(String clientName, String clientSurname, String email, String phone) {
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

    // Метод зберігає дані нової кімнати у базі даних.
    public static void saveRoomDB(String number, String type, int price, String details, boolean status) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ROOM_SQL)) {
            preparedStatement.setString(1, number);
            preparedStatement.setString(2, type);
            preparedStatement.setInt(3, price);
            preparedStatement.setString(4, details);
            preparedStatement.setBoolean(5, status);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод зберігає дані нової резервації у базі даних.
    public static void saveReservationDB(Integer clientID, Integer roomID, Date arrivalDate, Date departureDate, boolean status) {
        System.out.println(arrivalDate);
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_RESERVATION_SQL)) {
            preparedStatement.setInt(1, clientID);
            preparedStatement.setInt(2, roomID);
            preparedStatement.setDate(3, arrivalDate);
            preparedStatement.setDate(4, departureDate);
            preparedStatement.setBoolean(5, status);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Методи для отримання клієнтів, номерів та резервацій з БД

    // Метод виконує вибірку даних про клієнта з бази даних за його ідентифікатором.
    public static Client getClientFromDB(int ClientID) {
        Client client = null;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLIENT_SQL)) {
            preparedStatement.setInt(1, ClientID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Отримання даних про клієнта з результату запиту
                    StringProperty nameProperty = new SimpleStringProperty(resultSet.getString("name"));
                    StringProperty surnameProperty = new SimpleStringProperty(resultSet.getString("surname"));
                    StringProperty emailProperty = new SimpleStringProperty(resultSet.getString("email"));
                    StringProperty phoneNumberProperty = new SimpleStringProperty(resultSet.getString("phone"));

                    // Створення об'єкта клієнта на основі отриманих даних
                    client = new Client(nameProperty, surnameProperty, emailProperty, phoneNumberProperty);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    // Метод виконує вибірку даних про номер з бази даних за його ідентифікатором.
    public static Room getRoomFromDB(int RoomID) {
        Room room = null;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ROOM_SQL)) {
            preparedStatement.setInt(1, RoomID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Отримання даних про номер з результату запиту
                    StringProperty numberProperty = new SimpleStringProperty(resultSet.getString("number"));
                    StringProperty typeProperty = new SimpleStringProperty(resultSet.getString("type"));
                    IntegerProperty priceProperty = new SimpleIntegerProperty((resultSet.getInt("price")));
                    StringProperty detailsProprty = new SimpleStringProperty(resultSet.getString("details"));
                    boolean status = resultSet.getBoolean("roomStatus");

                    // Створення об'єкта номера на основі отриманих даних
                    room = new Room(numberProperty, typeProperty, priceProperty, detailsProprty, status);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return room;
    }

    // Метод виконує вибірку даних про резервацію з бази даних за її ідентифікатором.
    public static Reservation getReservationFromDB(int reservationID) {
        Reservation reservation = null;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_RESERVATION_SQL)) {

            preparedStatement.setInt(1, reservationID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int clientID = resultSet.getInt("clientID");
                    int roomID = resultSet.getInt("roomID");
                    LocalDate arrivalDate = resultSet.getDate("arrivalDate").toLocalDate();
                    LocalDate departureDate = resultSet.getDate("departureDate").toLocalDate();
                    boolean status = resultSet.getBoolean("reservationStatus");

                    // Отримання об'єктів клієнта та номера за їх ідентифікаторами
                    Room room = getRoomFromDB(roomID);
                    Client client = getClientFromDB(clientID);

                    // Створення об'єкта резервації на основі отриманих даних
                    reservation = new Reservation(client, room, arrivalDate, departureDate, status);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reservation;
    }

    // Методи для отримання ID клієнта з БД

    // Метод виконує вибірку ідентифікатора клієнта за його даними.
    public static int getClientIDFromDB(Client client) {
        int ID = 0;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID_FROM_CLIENTS_SQL)) {
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getSurname());
            preparedStatement.setString(3, client.getEmail());
            preparedStatement.setString(4, client.getPhone());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    ID = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ID;
    }

    // Метод виконує вибірку ідентифікатора номера за його даними.
    public static int getRoomIDFromDB(Room room) {
        int ID = 0;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID_FROM_ROOMS_SQL)) {
            preparedStatement.setString(1, room.getNumber());
            preparedStatement.setString(2, room.getType());
            preparedStatement.setInt(3, room.getPrice());
            preparedStatement.setString(4, room.getDetails());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    ID = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ID;
    }
    /*Метод для перевірки, чи в БД кімната зайнята(потрібно для конструктора в Reservations,
    щоб, наприклад, якщо кімната була одномістна і до неї додавався новий клієнт, кімната змінювала статус
     */

    // Метод виконує перевірку, чи зайнята вказана кімната.
    public static boolean isRoomOccupied(String roomNumber) {
        boolean isOccupied = false;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ROOM_STATUS_SQL)) {
            preparedStatement.setString(1, roomNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    isOccupied = resultSet.getBoolean("roomStatus");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isOccupied;
    }

    //Методи для зміни статусу кімнат та резервацій в БД

    // Метод виконує оновлення статусу кімнати на "false".
    public static void UpdateStatusRoomToFalseDB(String number) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STATUS_ROOMS_TO_FALSE_SQL)) {
            preparedStatement.setString(1, number);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Метод виконує оновлення статусу резервації на "false".
    public static void UpdateStatusReservationToFalseDB(int ClientID) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STATUS_RESERVATIONS_TO_FALSE_SQL)) {
            preparedStatement.setInt(1, ClientID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
