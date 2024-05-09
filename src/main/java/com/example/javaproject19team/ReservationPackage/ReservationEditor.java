package com.example.javaproject19team.ReservationPackage;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ReservationEditor extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Reservation Editor");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Labels
        Label nameLabel = new Label("Ім'я гостя:");
        GridPane.setConstraints(nameLabel, 0, 0);

        Label dateLabel = new Label("Дата:");
        GridPane.setConstraints(dateLabel, 0, 1);

        Label roomTypeLabel = new Label("Тип номера:");
        GridPane.setConstraints(roomTypeLabel, 0, 2);

        Label roomNumberLabel = new Label("Номер кімнати:");
        GridPane.setConstraints(roomNumberLabel, 0, 3);

        // TextFields
        TextField nameInput = new TextField();
        nameInput.setPromptText("Введіть ім'я");
        GridPane.setConstraints(nameInput, 1, 0);

        DatePicker datePicker = new DatePicker();
        GridPane.setConstraints(datePicker, 1, 1);

        ComboBox<String> roomComboBox = new ComboBox<>();
        roomComboBox.getItems().addAll("Одномісний", "Двохмісний", "Багатовмісний");
        GridPane.setConstraints(roomComboBox, 1, 2);

        TextField roomNumberInput = new TextField();
        roomNumberInput.setPromptText("Введіть номер кімнати");
        GridPane.setConstraints(roomNumberInput, 1, 3);

        // Buttons
        Button cancelButton = new Button("Скасувати");
        GridPane.setConstraints(cancelButton, 0, 4);
        cancelButton.setOnAction(e -> primaryStage.close());

        Button saveButton = new Button("Зберегти");
        GridPane.setConstraints(saveButton, 1, 4);
        saveButton.setOnAction(e -> saveReservation(nameInput.getText(), String.valueOf(datePicker.getValue()), roomComboBox.getValue()));

        grid.getChildren().addAll(nameLabel, nameInput, dateLabel, datePicker, roomTypeLabel, roomNumberLabel, roomComboBox, roomNumberInput,
                cancelButton, saveButton);

        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void saveReservation(String guestName, String date, String roomType) {

    }

    public static void main(String[] args) {
        launch(args);
    }
}
