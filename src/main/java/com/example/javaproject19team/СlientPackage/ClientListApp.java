package com.example.javaproject19team.СlientPackage;


import com.example.javaproject19team.HotelReservationApp;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ClientListApp extends Application {

    private TableView<Client> tableView;
    private ObservableList<Client> clientsObs = FXCollections.observableArrayList();



    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Список клієнтів");

        clientsObs.addAll(HotelReservationApp.getClients());
        tableView = new TableView<>();
            tableView.setItems(clientsObs);


            TableColumn<Client, String> nameColumn = new TableColumn<>("Ім'я");
            nameColumn.setCellValueFactory(data -> data.getValue().nameProperty());

            TableColumn<Client, String> surnameColumn = new TableColumn<>("Фамілія");
            surnameColumn.setCellValueFactory(data -> data.getValue().surnameProperty());

            TableColumn<Client, String> emailColumn = new TableColumn<>("Email");
            emailColumn.setCellValueFactory(data -> data.getValue().emailProperty());

            TableColumn<Client, String> phoneColumn = new TableColumn<>("Телефон");
            phoneColumn.setCellValueFactory(data -> data.getValue().phoneProperty());

            tableView.getColumns().addAll(nameColumn, surnameColumn, emailColumn, phoneColumn);


            Scene scene = new Scene(tableView, 600, 400);
            primaryStage.setScene(scene);
            primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}