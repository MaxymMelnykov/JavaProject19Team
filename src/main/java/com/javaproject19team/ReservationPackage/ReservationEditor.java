/*
ReservationEditor:
Цей клас відповідає за відображення вікна додавання нової резервації та збереження введеної інформації.
*/
package com.javaproject19team.ReservationPackage;

import com.javaproject19team.DatabasePackage.DatabaseHandler;
import com.javaproject19team.HotelReservationApp;
import com.javaproject19team.RoomPackage.Room;
import com.javaproject19team.СlientPackage.Client;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

/**
 * ReservationEditor:
 * Цей клас відповідає за відображення вікна додавання нової резервації та збереження введеної інформації.
 */
public class ReservationEditor extends Application {
    ComboBox<Room> roomComboBox;
    ReservationListener reservationListener;
    ObservableList<Client> clientsObservableList = FXCollections.observableArrayList(HotelReservationApp.getClients());

    /**
     * Метод, що викликається при старті додатку.
     *
     * @param primaryStage головне вікно додатку
     */
    @Override
    public void start(Stage primaryStage) {
        // Налаштування головного вікна програми
        primaryStage.setTitle("Додавання нової резервації");
        primaryStage.getIcons().add(new Image("file:src/main/resources/icon.png"));

        // ComboBox для вибору клієнта
        ComboBox<Client> clientComboBox = new ComboBox<>();
        clientComboBox.setPromptText("Виберіть клієнта");
        clientComboBox.getItems().addAll(clientsObservableList);
        clientComboBox.setMinWidth(280);

        // ComboBox для вибору типу кімнати
        ComboBox<String> roomTypeComboBox = new ComboBox<>();
        roomTypeComboBox.setPromptText("Виберіть тип номеру");
        roomTypeComboBox.getItems().addAll("Одномісний", "Двомісний", "Багатовмісний");
        roomTypeComboBox.setMinWidth(280);
        roomTypeComboBox.setOnAction(e -> {
            String selectedType = roomTypeComboBox.getValue();
            updateRoomList(selectedType);
        });

        // ComboBox для вибору кімнати
        roomComboBox = new ComboBox<>();
        roomComboBox.setPromptText("Виберіть номер");
        roomComboBox.setMinWidth(280);

        // Вибір дати заселення
        Label dateLabelArrival = new Label("Дата заселення:");
        DatePicker datePickerArrival = new DatePicker();
        datePickerArrival.setMaxWidth(150);
        HBox arrivalHBox = new HBox(dateLabelArrival, datePickerArrival);
        arrivalHBox.setAlignment(Pos.CENTER);
        arrivalHBox.setSpacing(5);

        // Вибір дати виселення
        Label dateLabelDeparture = new Label("Дата виселення:");
        DatePicker datePickerDeparture = new DatePicker();
        datePickerDeparture.setMaxWidth(150);
        HBox departureHBox = new HBox(dateLabelDeparture, datePickerDeparture);
        departureHBox.setAlignment(Pos.CENTER);
        departureHBox.setSpacing(5);

        // Кнопки "Вийти"
        Button cancelButton = new Button("Вийти");
        cancelButton.setMinWidth(100);
        cancelButton.setOnAction(e -> primaryStage.close());
        cancelButton.setId("cancel-button");

        // Кнопки "Зберегти"
        Button saveButton = new Button("Зберегти");
        saveButton.setMinWidth(100);
        saveButton.setOnAction(e -> saveReservation(
                clientComboBox.getValue(),
                roomComboBox.getValue(),
                datePickerArrival.getValue(),
                datePickerDeparture.getValue()
        ));

        HBox buttonHbox = new HBox(cancelButton, saveButton);
        buttonHbox.setSpacing(80);
        saveButton.setId("save-button");

        // Групування всіх елементів
        VBox root = new VBox(clientComboBox, roomTypeComboBox, roomComboBox, arrivalHBox, departureHBox, buttonHbox);
        root.setSpacing(10);
        root.setPadding(new Insets(10));

        // Створення сцени
        Scene scene = new Scene(root, 300, 255);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Метод для збереження резервації.
     * @param client обраний клієнт
     * @param room обрана кімната
     * @param arrivalDate дата прибуття
     * @param departureDate дата виїзду
     */
    private void saveReservation(Client client, Room room, LocalDate arrivalDate, LocalDate departureDate) {
        int clientIDDB = DatabaseHandler.getClientIDFromDB(client);
        int roomIDDB = DatabaseHandler.getRoomIDFromDB(room);
        java.sql.Date arrivalDateDB = java.sql.Date.valueOf(arrivalDate);
        java.sql.Date departureDateDB = java.sql.Date.valueOf(departureDate);
        boolean status = true;

        // Збереження резервації в базу даних та створення об'єкта резервації
        DatabaseHandler.saveReservationDB(clientIDDB, roomIDDB, arrivalDateDB, departureDateDB, status);
        Reservation newReservation = new Reservation(client, room, arrivalDate, departureDate, status);
        reservationListener.onReservationSaved(newReservation);
    }


    /**
     * Встановлення reservationListener резервацій.
     * @param listener об'єкт слухача
     */
    public void setReservationListener(ReservationListener listener) {
        this.reservationListener = listener;
    }

    /**
     * Метод для оновлення списку кімнат в залежності від вибраного типу.
     * @param selectedType вибраний тип кімнати
     */
    private void updateRoomList(String selectedType) {
        ObservableList<Room> roomsObservableList;
        switch (selectedType) {
            case "Одномісний":
                roomsObservableList = FXCollections.observableArrayList(HotelReservationApp.getFreeSingleRooms());
                break;
            case "Двомісний":
                roomsObservableList = FXCollections.observableArrayList(HotelReservationApp.getFreePairRooms());
                break;
            case "Багатовмісний":
                roomsObservableList = FXCollections.observableArrayList(HotelReservationApp.getFreeMultiRooms());
                break;
            default:
                roomsObservableList = FXCollections.observableArrayList();
                break;
        }
        roomComboBox.setItems(roomsObservableList);
    }

    // Точка входу у програму
    public static void main(String[] args) {
        launch(args);
    }
}
