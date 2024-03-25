package com.example.javaproject19team;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class NumbersListApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Список доступних номерів");

        // Створення списку доступних номерів
        ObservableList<Numbers> rooms = FXCollections.observableArrayList(
                new Numbers("101", "Стандарт", 100, "Детальна інформація 1"),
                new Numbers("202", "Полулюкс", 150, "Детальна інформація 2"),
                new Numbers("303", "Люкс", 200, "Детальна інформація 3")
        );

        // Створення TableView для відображення списку номерів
        TableView<Numbers> tableView = new TableView<>(rooms);

        // Додаємо колонки до TableView
        TableColumn<Numbers, String> numberColumn = new TableColumn<>("Номер");
        numberColumn.setCellValueFactory(data -> data.getValue().numberProperty());

        TableColumn<Numbers, String> typeColumn = new TableColumn<>("Тип");
        typeColumn.setCellValueFactory(data -> data.getValue().typeProperty());

        TableColumn<Numbers, Number> priceColumn = new TableColumn<>("Ціна");
        priceColumn.setCellValueFactory(data -> data.getValue().priceProperty());

        TableColumn<Numbers, String> detailsColumn = new TableColumn<>("Детальна інформація");
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
