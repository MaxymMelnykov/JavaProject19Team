package com.javaproject19team.RoomPackage;

import com.javaproject19team.DatabasePackage.DatabaseHandler;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RoomEditor extends Application {
    RoomListener roomListener;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Додавання нового номеру");
        primaryStage.getIcons().add(new Image("file:src/main/resources/icon.png"));

        Label numberLabel = new Label("Номер:");
        TextField numberInput = new TextField();
        numberInput.setPromptText("Введіть номер");

        HBox numberHBox = new HBox(numberLabel, numberInput);
        numberHBox.setSpacing(5);
        numberHBox.setAlignment(Pos.CENTER);

        Label typeLabel = new Label("Тип:");
        ComboBox<String> typeInput = new ComboBox<>();
        typeInput.getItems().addAll("Одномістний", "Двомістний", "Багатовмістний");

        HBox typeHBox = new HBox(typeLabel, typeInput);
        typeHBox.setSpacing(5);
        typeHBox.setAlignment(Pos.CENTER);

        Label priceLabel = new Label("Ціна:");
        TextField priceInput = new TextField();
        priceInput.setPromptText("Введіть ціну");

        HBox priceHBox = new HBox(priceLabel, priceInput);
        priceHBox.setSpacing(5);
        priceHBox.setAlignment(Pos.CENTER);

        Label detailsLabel = new Label("Детальна інформація:");
        TextField detailsInput = new TextField();
        detailsInput.setPromptText("Введіть детальну інформацію");
        detailsInput.setMinWidth(210);

        HBox detailsHBox = new HBox(detailsLabel, detailsInput);
        detailsHBox.setSpacing(10);
        detailsHBox.setAlignment(Pos.CENTER);

        Button cancelButton = new Button("Вийти");
        cancelButton.setOnAction(e -> primaryStage.close());
        cancelButton.setMinWidth(100);
        cancelButton.setId("cancel-button");

        Button saveButton = new Button("Зберегти");
        saveButton.setOnAction(e -> saveRoom(
                new SimpleStringProperty(numberInput.getText()),
                new SimpleStringProperty(typeInput.getValue()),
                new SimpleIntegerProperty(Integer.parseInt(priceInput.getText())),
                new SimpleStringProperty(detailsInput.getText())));
        saveButton.setMinWidth(100);
        saveButton.setId("save-button");

        HBox buttonHBox = new HBox(cancelButton, saveButton);
        buttonHBox.setSpacing(160);

        VBox root = new VBox(numberHBox, typeHBox, priceHBox, detailsHBox, buttonHBox);
        root.setSpacing(10);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 380, 210);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void saveRoom(StringProperty number, StringProperty type, IntegerProperty price, StringProperty details) {
        String numberDB = number.get();
        String typeDB = type.get();
        int priceDB = price.get();
        String detailsDB = details.get();
        boolean status = true;

        if (DatabaseHandler.isRoomOccupied(numberDB)) {
            status = false; // Если комната уже занята, устанавливаем статус как занятый
        }

        DatabaseHandler.saveRoomDB(numberDB, typeDB, priceDB, detailsDB, status);
        Room newRoom = new Room(number, type, price, details, status);
        roomListener.onRoomSaved(newRoom);

    }

    public void setRoomListener(RoomListener listener) {
        this.roomListener = listener;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
