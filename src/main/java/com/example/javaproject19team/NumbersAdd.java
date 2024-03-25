package com.example.javaproject19team;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NumbersAdd extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Додавання номеру");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Labels
        Label numberLabel = new Label("Номер:");
        GridPane.setConstraints(numberLabel, 0, 0);

        Label typeLabel = new Label("Тип:");
        GridPane.setConstraints(typeLabel, 0, 1);

        Label priceLabel = new Label("Ціна:");
        GridPane.setConstraints(priceLabel, 0, 2);

        Label detailsLabel = new Label("Детальна інформація:");
        GridPane.setConstraints(detailsLabel, 0, 3);

        // TextFields
        TextField numberInput = new TextField();
        numberInput.setPromptText("Введіть номер");
        GridPane.setConstraints(numberInput, 1, 0);

        TextField typeInput = new TextField();
        typeInput.setPromptText("Введіть тип");
        GridPane.setConstraints(typeInput, 1, 1);

        TextField priceInput = new TextField();
        priceInput.setPromptText("Введіть ціну");
        GridPane.setConstraints(priceInput, 1, 2);

        TextField detailsInput = new TextField();
        detailsInput.setPromptText("Введіть детальну інформацію");
        GridPane.setConstraints(detailsInput, 1, 3);

        // Buttons
        Button cancelButton = new Button("Скасувати");
        cancelButton.setOnAction(e -> primaryStage.close());
        GridPane.setConstraints(cancelButton, 0, 4);

        Button saveButton = new Button("Зберегти");
        //Логіка збереження
        GridPane.setConstraints(saveButton, 1, 4);

        grid.getChildren().addAll(numberLabel, typeLabel, priceLabel, detailsLabel , numberInput, typeInput, priceInput, detailsInput,
                cancelButton, saveButton);

        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void saveReservation(String guestName, String date, String roomType) {
        // Логіка для збереження номеру

    }

    public static void main(String[] args) {
        launch(args);
    }
}
