/*
ClientEditor:
Цей клас відповідає за відображення вікна додавання нового клієнта та збереження введеної інформації.
*/
package com.javaproject19team.СlientPackage;

import com.javaproject19team.DatabasePackage.DatabaseHandler;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

public class ClientEditor extends Application {
    private ClientListener clientListener;

    @Override
    public void start(Stage primaryStage) {
        // Налаштування вікна додавання нового клієнта
        primaryStage.getIcons().add(new Image("file:src/main/resources/icon.png"));
        primaryStage.setTitle("Додавання нового клієнта");

        // Інтерфейс введення даних про клієнта
        Label nameLabel = new Label("Ім'я:");
        TextField nameInput = new TextField();
        nameInput.setPromptText("Введіть ім'я");

        HBox nameHBox = new HBox(nameLabel, nameInput);
        nameHBox.setAlignment(Pos.CENTER);
        nameHBox.setSpacing(10);

        Label surnameLabel = new Label("Прізвище:");
        TextField surnameInput = new TextField();
        surnameInput.setPromptText("Введіть прізвище");

        HBox surnameHBox = new HBox(surnameLabel, surnameInput);
        surnameHBox.setAlignment(Pos.CENTER);
        surnameHBox.setSpacing(10);

        Label emailLabel = new Label("Email:");
        TextField emailInput = new TextField();
        emailInput.setPromptText("Введіть емейл");

        HBox emailHBox = new HBox(emailLabel, emailInput);
        emailHBox.setAlignment(Pos.CENTER);
        emailHBox.setSpacing(10);

        Label phoneLabel = new Label("Номер телефону:");
        TextField phoneInput = new TextField();
        phoneInput.setPromptText("Введіть телефон");

        HBox phoneHBox = new HBox(phoneLabel, phoneInput);
        phoneHBox.setAlignment(Pos.CENTER);
        phoneHBox.setSpacing(10);

        // Кнопка "Вийти"
        Button cancelButton = new Button("Вийти");
        cancelButton.setOnAction(e -> primaryStage.close());
        cancelButton.setMinWidth(100);
        cancelButton.setId("cancel-button");
        //Кнопка "Зберегти"
        Button saveButton = new Button("Зберегти");
        saveButton.setOnAction(e -> saveClient(
                new SimpleStringProperty(nameInput.getText()),
                new SimpleStringProperty(surnameInput.getText()),
                new SimpleStringProperty(emailInput.getText()),
                new SimpleStringProperty(phoneInput.getText())));
        saveButton.setMinWidth(100);
        saveButton.setId("save-button");

        HBox buttonHBox = new HBox(cancelButton, saveButton);
        buttonHBox.setSpacing(95);

        // VBox для елементів інтерфейсу
        VBox root = new VBox(nameHBox, surnameHBox, emailHBox, phoneHBox, buttonHBox);
        root.setSpacing(10);
        root.setPadding(new Insets(10));

        // Налаштування Scene та відображення вікна
        Scene scene = new Scene(root, 315, 210);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Метод для збереження даних про клієнта в базу даних
    private void saveClient(StringProperty clientName, StringProperty clientSurname, StringProperty emailInput, StringProperty phoneNumber) {
        // Отримання даних про клієнта
        String clientNameDB = clientName.get();
        String clientSurnameDB = clientSurname.get();
        String emailDB = emailInput.get();
        String phoneDB = phoneNumber.get();

        // Збереження даних про клієнта в базу даних
        DatabaseHandler.saveClientDB(clientNameDB, clientSurnameDB, emailDB, phoneDB);
        Client newClient = new Client(clientName, clientSurname, emailInput, phoneNumber);
        clientListener.onClientSaved(newClient);

    }

    // Метод для встановлення ClientListener
    public void setClientListener(ClientListener listener) {
        this.clientListener = listener;
    }

    // Точка входу у програму
    public static void main(String[] args) {
        launch(args);
    }
}
