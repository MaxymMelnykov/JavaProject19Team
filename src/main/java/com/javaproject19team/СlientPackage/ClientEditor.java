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

/**
 * ClientEditor:
 * Цей клас відповідає за відображення вікна додавання нового клієнта та збереження введеної інформації.
 */
public class ClientEditor extends Application {
    private ClientListener clientListener;


    /**
     * Метод, що викликається при старті додатку
     *
     * @param primaryStage Об'єкт вікна додатку
     */

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
        saveButton.setOnAction(e -> {
            if (validateInput(nameInput, surnameInput, emailInput, phoneInput)) {
                saveClient(
                        new SimpleStringProperty(nameInput.getText()),
                        new SimpleStringProperty(surnameInput.getText()),
                        new SimpleStringProperty(emailInput.getText()),
                        new SimpleStringProperty(phoneInput.getText()));
            }
        });
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


    /**
     * Метод для збереження даних про клієнта в базу даних
     *
     * @param nameInput    Ім'я клієнта
     * @param surnameInput Прізвище клієнта
     * @param emailInput    Електронна пошта клієнта
     * @param phoneInput   Телефон клієнта
     */
    // Метод для перевірки введених даних
    private boolean validateInput(TextField nameInput, TextField surnameInput, TextField emailInput, TextField phoneInput) {
        if (nameInput.getText().isEmpty() || surnameInput.getText().isEmpty() || emailInput.getText().isEmpty() || phoneInput.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Помилка введення", "Будь ласка, заповніть всі поля.");
            return false;
        }

        if (!emailInput.getText().matches("\\S+@\\S+\\.\\S+")) {
            showAlert(Alert.AlertType.ERROR, "Помилка введення", "Будь ласка, введіть коректний Email.");
            return false;
        }

        if (!phoneInput.getText().matches("\\+380\\d{9}")) {
            showAlert(Alert.AlertType.ERROR, "Помилка введення", "Будь ласка, введіть коректний номер телефону у форматі \"+380xxxxxxxxx\".");
            return false;
        }


        return true;
    }

    // Метод для показу Alert
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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


    /**
     * Метод для встановлення ClientListener
     *
     * @param listener Об'єкт слухача подій
     */

    public void setClientListener(ClientListener listener) {
        this.clientListener = listener;
    }

    // Точка входу у програму
    public static void main(String[] args) {
        launch(args);
    }
}