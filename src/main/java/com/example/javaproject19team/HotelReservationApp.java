package com.example.javaproject19team;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HotelReservationApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Головний екран");
        VBox mainContainer = new VBox();
        mainContainer.setPadding(new Insets(10)); // Встановлюємо відступи для головного контейнера
        mainContainer.setSpacing(10); // Відступ між контейнерами


        HBox buttonsBox = new HBox(); // Використовуємо HBox для розміщення кнопок в ряд
        buttonsBox.setSpacing(10); // Відступи між кнопками
        buttonsBox.setPadding(new Insets(10, 10, 10, 10));

        Button reservationsButton = new Button("Резервації");
        reservationsButton.setMinWidth(100); // Збільшуємо ширину кнопки
        reservationsButton.setOnAction(e -> showReservations());

        Button clientsButton = new Button("Клієнти");
        clientsButton.setMinWidth(100); // Збільшуємо ширину кнопки
        clientsButton.setOnAction(e -> showClients());

        Button roomsButton = new Button("Номери");
        roomsButton.setMinWidth(100); // Збільшуємо ширину кнопки
        roomsButton.setOnAction(e -> showRooms());

        buttonsBox.getChildren().addAll(reservationsButton, clientsButton, roomsButton);


        HBox typeRoomBox = new HBox();
        typeRoomBox.setSpacing(10);
        Label typeRoomLabel = new Label("Виберіть тип номеру ");
        ComboBox<String> roomComboBox = new ComboBox<>();
        roomComboBox.getItems().addAll("Одномісний", "Двохмісний", "Багатовмісний");
        typeRoomBox.getChildren().addAll(typeRoomLabel, roomComboBox);

        HBox reservedRoomsNumberBox = new HBox();
        reservedRoomsNumberBox.setSpacing(10);
        Label reservedRoomsLabel = new Label("Кількість зайнятих номерів :         32");
        reservedRoomsNumberBox.getChildren().addAll(reservedRoomsLabel);


        HBox unreservedRoomsNumberBox = new HBox();
        unreservedRoomsNumberBox.setSpacing(10);
        Label unreservedRoomsLabel = new Label("Кількість доступних номерів :      56");
        unreservedRoomsNumberBox.getChildren().addAll(unreservedRoomsLabel);


        mainContainer.getChildren().addAll(buttonsBox, typeRoomBox, reservedRoomsNumberBox, unreservedRoomsNumberBox);

        Scene scene = new Scene(mainContainer, 400, 300); // Встановлюємо висоту на 100
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showReservations() {
        // Показати екран Резервацій
        System.out.println("Showing Reservations");
    }

    private void showClients() {
        // Показати екран Клієнтів
        System.out.println("Showing Clients");
    }

    private void showRooms() {
        // Показати екран Номерів
        System.out.println("Showing Rooms");
    }

    public static void main(String[] args) {
        launch(args);
    }
}