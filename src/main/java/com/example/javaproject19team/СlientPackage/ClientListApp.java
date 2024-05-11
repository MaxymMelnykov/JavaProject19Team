package com.example.javaproject19team.СlientPackage;

import com.example.javaproject19team.HotelReservationApp;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ClientListApp extends Application implements ClientListener {
    private static Stage hotelReservationStage;
    private TableView<Client> tableView;
    private ObservableList<Client> clientsObs = FXCollections.observableArrayList();
    private ClientListener clientListener;
    public static void setHotelReservationStage(Stage stage) {
        hotelReservationStage = stage;
    }

    public void setClientListener(ClientListener clientListener) {
        this.clientListener = clientListener;
    }

    @Override
    public void start(Stage primaryStage) {
        hotelReservationStage.hide();

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

        Button addButton = new Button("Додати");
        addButton.setOnAction(e -> {
            showAddOrRemoveClients();
        });


        Button refreshButton = new Button("Оновити");
        refreshButton.setOnAction(e -> {
            clientsObs.clear();
            clientsObs.addAll(HotelReservationApp.getClients());
        });
        Button onMainMenuButton = new Button("До головного меню");
        onMainMenuButton.setOnAction(e -> {
            primaryStage.hide();
            HotelReservationApp.primaryStage.show();
        });
        GridPane.setConstraints(onMainMenuButton, 0, 4);

        HBox buttonsBox = new HBox(addButton, refreshButton,onMainMenuButton);
        buttonsBox.setSpacing(10);
        buttonsBox.setPadding(new Insets(10));

        TextField filterField = new TextField();
        filterField.setPromptText("Пошук...");
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            String filter = newValue.toLowerCase();
            tableView.setItems(clientsObs.filtered(client ->
                    client.getName().toLowerCase().contains(filter) ||
                            client.getSurname().toLowerCase().contains(filter) ||
                            client.getEmail().toLowerCase().contains(filter) ||
                            client.getPhone().toLowerCase().contains(filter)
            ));
        });

        VBox root = new VBox(buttonsBox, filterField, tableView);
        root.setSpacing(10);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    @Override
    public void onClientSaved(Client client) {
        clientsObs.add(client);
    }

    private void showAddOrRemoveClients() {
        Stage addOrRemoveClients = new Stage();
        ClientEditor clientEditor = new ClientEditor();
        clientEditor.setClientListener(clientListener);
        try {
            clientEditor.start(addOrRemoveClients);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Showing AddOrRemove");
    }
    @Override
    public void stop() throws Exception {
        super.stop();
        // Показываем HotelReservationApp при закрытии ClientListApp
        hotelReservationStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}