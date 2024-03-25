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

public class ClientsListApp extends Application {

    private TableView<Clients> tableView;
    private ObservableList<Clients> clients;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Список клієнтів");

        // Додамо декілька фіктивних резервацій для демонстрації
        clients = FXCollections.observableArrayList(
                new Clients("Олександр", "Коваленко", "okov@gmail.com","+38033454521"),
                new Clients("Ольга", "Іваненко", "oiv@gmail.com","+38065734581"),
                new Clients("Андрій", "Мельник", "amel@gmail.com","+38036734529"),
                new Clients("Юлія", "Сидоренко", "yus@gmail.com","+38034124521"),
                new Clients("Марія", "Іваненко", "sdfg@gmail.com","+38034534511"),
                new Clients("Максим", "Шевченко", "okjj@gmail.com","+38035674861"),
                new Clients("Тетяна", "Ковальчук", "tetkov@gmail.com","+380342342121")

        );

        tableView = new TableView<>();
        tableView.setItems(clients);

        TableColumn<Clients, String> nameColumn = new TableColumn<>("Ім'я");
        nameColumn.setCellValueFactory(data -> data.getValue().nameProperty());

        TableColumn<Clients, String> surnameColumn = new TableColumn<>("Фамілія");
        surnameColumn.setCellValueFactory(data -> data.getValue().surnameProperty());

        TableColumn<Clients, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(data -> data.getValue().emailProperty());

        TableColumn<Clients, String> phoneColumn = new TableColumn<>("Телефон");
        phoneColumn.setCellValueFactory(data -> data.getValue().phoneProperty());

        tableView.getColumns().addAll(nameColumn, surnameColumn,emailColumn, phoneColumn);


        Scene scene = new Scene(tableView, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}