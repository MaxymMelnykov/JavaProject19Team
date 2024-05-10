package com.example.javaproject19team.RoomPackage;

import com.example.javaproject19team.HotelReservationApp;
import com.example.javaproject19team.RoomPackage.Room;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class RoomListApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Список доступних номерів");

        // Створення списку доступних номерів
        ObservableList<Room> rooms = FXCollections.observableArrayList(HotelReservationApp.getRooms());

        // Створення TableView для відображення списку номерів
        TableView<Room> tableView = new TableView<>(rooms);

        // Додаємо колонки до TableView
        TableColumn<Room, String> numberColumn = new TableColumn<>("Номер");
        numberColumn.setCellValueFactory(data -> data.getValue().numberProperty());

        TableColumn<Room, String> typeColumn = new TableColumn<>("Тип");
        typeColumn.setCellValueFactory(data -> data.getValue().typeProperty());

        TableColumn<Room, Number> priceColumn = new TableColumn<>("Ціна");
        priceColumn.setCellValueFactory(data -> data.getValue().priceProperty());

        TableColumn<Room, String> detailsColumn = new TableColumn<>("Детальна інформація");
        detailsColumn.setCellValueFactory(data -> data.getValue().detailsProperty());

        tableView.getColumns().addAll(numberColumn, typeColumn, priceColumn, detailsColumn);


        Scene scene = new Scene(tableView, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
