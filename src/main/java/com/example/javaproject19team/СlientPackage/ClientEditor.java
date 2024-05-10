package com.example.javaproject19team.СlientPackage;

import com.example.javaproject19team.DatabasePackage.DatabaseHandler;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ClientEditor extends Application {
    private ClientListener clientListener;


    @Override
    public void start(Stage primaryStage) {

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Labels
        Label nameLabel = new Label("Ім'я:");
        GridPane.setConstraints(nameLabel, 0, 0);

        Label surnameLabel = new Label("Фамілія:");
        GridPane.setConstraints(surnameLabel, 0, 1);

        Label emailLabel = new Label("Email:");
        GridPane.setConstraints(emailLabel, 0, 2);

        Label phoneLabel = new Label("Номер телефону:");
        GridPane.setConstraints(phoneLabel, 0, 3);

        // TextFields
        TextField nameInput = new TextField();
        nameInput.setPromptText("Введіть ім'я");
        GridPane.setConstraints(nameInput, 1, 0);

        TextField surnameInput = new TextField();
        surnameInput.setPromptText("Введіть фамілію");
        GridPane.setConstraints(surnameInput, 1, 1);

        TextField emailInput = new TextField();
        emailInput.setPromptText("Введіть емейл");
        GridPane.setConstraints(emailInput, 1, 2);

        TextField phoneInput = new TextField();
        phoneInput.setPromptText("Введіть телефон");
        GridPane.setConstraints(phoneInput, 1, 3);

        // Buttons
        Button cancelButton = new Button("Скасувати");
        cancelButton.setOnAction(e -> primaryStage.close());
        GridPane.setConstraints(cancelButton, 0, 4);

        Button saveButton = new Button("Зберегти");
        GridPane.setConstraints(saveButton, 1, 4);
        saveButton.setOnAction(e -> saveClient(
                new SimpleStringProperty(nameInput.getText()),
                new SimpleStringProperty(surnameInput.getText()),
                new SimpleStringProperty(emailInput.getText()),
                new SimpleStringProperty(phoneInput.getText())));



        grid.getChildren().addAll(nameLabel, surnameLabel, emailLabel, phoneLabel, nameInput, surnameInput, emailInput, phoneInput,
                cancelButton, saveButton);

        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void saveClient(StringProperty clientName, StringProperty clientSurname, StringProperty emailInput, StringProperty phoneNumber) {
        String clientNameDB = clientName.get();
        String clientSurnameDB = clientSurname.get();
        String emailDB = emailInput.get();
        String phoneDB = phoneNumber.get();

        DatabaseHandler.saveClientDB(clientNameDB,clientSurnameDB,emailDB,phoneDB);
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
