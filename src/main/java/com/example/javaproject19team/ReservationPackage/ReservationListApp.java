package com.example.javaproject19team.ReservationPackage;

import com.example.javaproject19team.RoomPackage.Room;
import com.example.javaproject19team.СlientPackage.Client;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ReservationListApp extends Application {

    private TableView<Reservation> tableView;
    private ObservableList<Reservation> reservations;
    private ArrayList<Client> clients;
    private ArrayList<Room> rooms;

    @Override
    public void start(Stage primaryStage) {


        // Додамо декілька фіктивних резервацій для демонстрації
        reservations = FXCollections.observableArrayList(
        );

        tableView = new TableView<>();
        tableView.setItems(reservations);

        //TableColumn<Reservation, String> guestColumn = new TableColumn<>("Гість");
        //guestColumn.setCellValueFactory(data);

        TableColumn<Reservation, String> dateColumn = new TableColumn<>("Дата резервації");
        //dateColumn.setCellValueFactory(data -> data.getValue().dateProperty());

        //TableColumn<Reservation, String> statusColumn = new TableColumn<>("Статус");
        //statusColumn.setCellValueFactory(data -> data.getValue().statusProperty());

        //tableView.getColumns().addAll(guestColumn, dateColumn, statusColumn);

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