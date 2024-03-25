package com.example.javaproject19team;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ReservationsListApp extends Application {

    private TableView<Reservation> tableView;
    private ObservableList<Reservation> reservations;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Список резервацій");

        // Додамо декілька фіктивних резервацій для демонстрації
        reservations = FXCollections.observableArrayList(
                new Reservation("Олександр Коваленко", "2024-03-04", "Активна"),
                new Reservation("Ольга Іваненко", "2024-02-22", "Active"),
                new Reservation("Андрій Мельник", "2024-03-12", "Active"),
                new Reservation("Юлія Сидоренко", "2024-03-11", "Active"),
                new Reservation("Марія Іваненко", "2024-02-04", "Active"),
                new Reservation("Максим Шевченко", "2024-03-27", "Active"),
                new Reservation("Тетяна Ковальчук", "2024-03-28", "Не активна")
        );

        tableView = new TableView<>();
        tableView.setItems(reservations);

        TableColumn<Reservation, String> guestColumn = new TableColumn<>("Гість");
        guestColumn.setCellValueFactory(data -> data.getValue().guestProperty());

        TableColumn<Reservation, String> dateColumn = new TableColumn<>("Дата резервації");
        dateColumn.setCellValueFactory(data -> data.getValue().dateProperty());

        TableColumn<Reservation, String> statusColumn = new TableColumn<>("Статус");
        statusColumn.setCellValueFactory(data -> data.getValue().statusProperty());

        tableView.getColumns().addAll(guestColumn, dateColumn, statusColumn);

        // Додамо контрольні елементи для фільтрації
        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Виберіть дату");

        ComboBox<String> statusFilter = new ComboBox<>();
        statusFilter.getItems().addAll("Всі", "Активні", "Не активні");
        statusFilter.setValue("Всі");

        Button filterButton = new Button("Фільтрувати");
        filterButton.setOnAction(e -> filterReservations(String.valueOf(datePicker.getValue()), statusFilter.getValue()));

        BorderPane root = new BorderPane();
        root.setTop(new HBox(datePicker, statusFilter, filterButton));
        root.setCenter(tableView);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void filterReservations(String selectedDate, String selectedStatus) {
        ObservableList<Reservation> filteredList = FXCollections.observableArrayList();
        for (Reservation reservation : reservations) {
            if (("All".equals(selectedStatus) || reservation.getStatus().equals(selectedStatus)) &&
                    (selectedDate == null || selectedDate.isEmpty() || reservation.getDate().equals(selectedDate))) {
                filteredList.add(reservation);
            }
        }
        tableView.setItems(filteredList);
    }

    public static void main(String[] args) {
        launch(args);
    }
}