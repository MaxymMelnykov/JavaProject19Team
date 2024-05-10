package com.example.javaproject19team.ReservationPackage;

import com.example.javaproject19team.HotelReservationApp;
import com.example.javaproject19team.RoomPackage.Room;
import com.example.javaproject19team.СlientPackage.Client;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;

import java.time.LocalDate;
import java.util.ArrayList;

public class ReservationListApp extends Application {




    @Override
    public void start(Stage primaryStage) {
        TableView<Reservation> tableView;
        ObservableList<Reservation> reservations = FXCollections.observableArrayList(HotelReservationApp.getReservations());

        tableView = new TableView<>();
        tableView.setItems(reservations);


        TableColumn<Reservation, String> clientSurnameColumn = new TableColumn<>("Фамілія гостя");
        clientSurnameColumn.setCellValueFactory(data -> data.getValue().getClient().surnameProperty());

        TableColumn<Reservation, LocalDate> arrivalDateColumn = new TableColumn<>("Дата початку резервації");
        arrivalDateColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
        arrivalDateColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));

        TableColumn<Reservation, LocalDate> departureDateColumn = new TableColumn<>("Дата кінця резервації");
        departureDateColumn.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
        departureDateColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));

        TableColumn<Reservation, String> statusColumn = new TableColumn<>("Статус");
        statusColumn.setCellValueFactory(data -> {
            boolean status = data.getValue().isStatus();
            String statusText = status ? "Активна" : "Не активна";
            return new SimpleStringProperty(statusText);
        });

        tableView.getColumns().addAll(clientSurnameColumn, arrivalDateColumn, departureDateColumn, statusColumn);


        // Додамо контрольні елементи для фільтрації
        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Виберіть дату");

        ComboBox<String> statusFilter = new ComboBox<>();
        statusFilter.getItems().addAll("Всі", "Активні", "Не активні");
        statusFilter.setValue("Всі");

        Button filterButton = new Button("Фільтрувати");
        //filterButton.setOnAction(e -> filterReservations(String.valueOf(datePicker.getValue()), statusFilter.getValue()));

        BorderPane root = new BorderPane();
        root.setTop(new HBox(datePicker, statusFilter, filterButton));
        root.setCenter(tableView);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /*private void filterReservations(String selectedDate, String selectedStatus) {
        ObservableList<Reservation> filteredList = FXCollections.observableArrayList();
        for (Reservation reservation : reservations) {
            if (("All".equals(selectedStatus) || reservation.getStatus().equals(selectedStatus)) &&
                    (selectedDate == null || selectedDate.isEmpty() || reservation.getDate().equals(selectedDate))) {
                filteredList.add(reservation);
            }
        }
        tableView.setItems(filteredList);
    }*/

    public static void main(String[] args) {
        launch(args);
    }
}