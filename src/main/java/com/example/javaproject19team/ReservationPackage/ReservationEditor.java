package com.example.javaproject19team.ReservationPackage;

import com.example.javaproject19team.DatabasePackage.DatabaseHandler;
import com.example.javaproject19team.HotelReservationApp;
import com.example.javaproject19team.RoomPackage.Room;
import com.example.javaproject19team.СlientPackage.Client;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalDate;

public class ReservationEditor extends Application {
    ComboBox<Room> roomComboBox;
    ReservationListener reservationListener;
    ObservableList<Room> roomsObservableList = FXCollections.observableArrayList(HotelReservationApp.getFreeRooms());
    ObservableList<Client> clientsObservableList = FXCollections.observableArrayList(HotelReservationApp.getClients());

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Reservation Editor");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label dateLabelArrival = new Label("Дата заселення:");
        GridPane.setConstraints(dateLabelArrival, 0, 3);

        Label dateLabelDeparture = new Label("Дата виселення:");
        GridPane.setConstraints(dateLabelDeparture, 0, 4);


        ComboBox<Client> clientComboBox = new ComboBox<>();
        clientComboBox.setPromptText("Виберіть клієнта");
        clientComboBox.getItems().addAll(clientsObservableList);
        GridPane.setConstraints(clientComboBox, 0, 0);
        GridPane.setColumnSpan(clientComboBox, 2);
        clientComboBox.setMinWidth(280);

        ComboBox<String> roomTypeComboBox = new ComboBox<>();
        roomTypeComboBox.setPromptText("Выберите тип комнаты");
        roomTypeComboBox.getItems().addAll("Одномісний", "Двомісний", "Багатовмісний");
        GridPane.setConstraints(roomTypeComboBox, 0, 1);
        GridPane.setColumnSpan(roomTypeComboBox, 2);
        roomTypeComboBox.setMinWidth(280);

        roomTypeComboBox.setOnAction(e -> {
            String selectedType = roomTypeComboBox.getValue();
            updateRoomList(selectedType);
        });

        roomComboBox = new ComboBox<>();
        roomComboBox.setPromptText("Выберите номер");
        GridPane.setConstraints(roomComboBox, 0, 2);
        GridPane.setColumnSpan(roomComboBox, 2);
        roomComboBox.setMinWidth(280);

        DatePicker datePickerArrival = new DatePicker();
        GridPane.setConstraints(datePickerArrival, 1, 3);

        DatePicker datePickerDeparture = new DatePicker();
        GridPane.setConstraints(datePickerDeparture, 1, 4);

        Button cancelButton = new Button("Скасувати");
        cancelButton.setMinWidth(100);
        cancelButton.setOnAction(e -> primaryStage.close());

        GridPane.setConstraints(cancelButton, 0, 5);

        Button saveButton = new Button("Зберегти");
        saveButton.setMinWidth(100);
        GridPane.setConstraints(saveButton, 1, 5);
        saveButton.setOnAction(e -> saveReservation(
                clientComboBox.getValue(),
                roomComboBox.getValue(),
                datePickerArrival.getValue(),
                datePickerDeparture.getValue()
        ));

        grid.getChildren().addAll(dateLabelArrival, dateLabelDeparture,roomTypeComboBox, clientComboBox, roomComboBox, datePickerArrival, datePickerDeparture, cancelButton, saveButton);

        Scene scene = new Scene(grid, 300, 210);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void saveReservation(Client client, Room room, LocalDate arrivalDate, LocalDate departureDate) {
        int clientIDDB = DatabaseHandler.getClientIDFromDB(client);
        int roomIDDB = DatabaseHandler.getRoomIDFromDB(room);
        java.sql.Date arrivalDateDB = java.sql.Date.valueOf(arrivalDate);
        java.sql.Date departureDateDB = java.sql.Date.valueOf(departureDate);
        boolean status = true;


        DatabaseHandler.saveReservationDB(clientIDDB, roomIDDB, arrivalDateDB, departureDateDB, status);
        Reservation newReservation = new Reservation(client, room, arrivalDate, departureDate, status);
        reservationListener.onReservationSaved(newReservation);
    }


    public void setReservationListener(ReservationListener listener) {
        this.reservationListener = listener;
    }

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
    public static void main(String[] args) {
        launch(args);
    }
}
