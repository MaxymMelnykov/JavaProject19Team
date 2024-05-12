package com.example.javaproject19team.СlientPackage;

import com.example.javaproject19team.DatabasePackage.DatabaseHandler;
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
        primaryStage.getIcons().add(new Image("file:src/main/resources/icon.png"));
        primaryStage.setTitle("Додавання нового клієнта");

        // Labels
        Label nameLabel = new Label("Ім'я:");
        TextField nameInput = new TextField();
        nameInput.setPromptText("Введіть ім'я");

        HBox nameHBox = new HBox(nameLabel, nameInput);
        nameHBox.setAlignment(Pos.CENTER);
        nameHBox.setSpacing(10);

        Label surnameLabel = new Label("Фамілія:");
        TextField surnameInput = new TextField();
        surnameInput.setPromptText("Введіть фамілію");

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

        Button cancelButton = new Button("Вийти");
        cancelButton.setOnAction(e -> primaryStage.close());
        cancelButton.setMinWidth(100);
        cancelButton.setId("cancel-button");

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

        VBox root = new VBox(nameHBox, surnameHBox, emailHBox, phoneHBox, buttonHBox);
        root.setSpacing(10);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 315, 210);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void saveClient(StringProperty clientName, StringProperty clientSurname, StringProperty emailInput, StringProperty phoneNumber) {
        String clientNameDB = clientName.get();
        String clientSurnameDB = clientSurname.get();
        String emailDB = emailInput.get();
        String phoneDB = phoneNumber.get();

        DatabaseHandler.saveClientDB(clientNameDB, clientSurnameDB, emailDB, phoneDB);
        Client newClient = new Client(clientName, clientSurname, emailInput, phoneNumber);
        clientListener.onClientSaved(newClient);

    }

    public void setClientListener(ClientListener listener) {
        this.clientListener = listener;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
