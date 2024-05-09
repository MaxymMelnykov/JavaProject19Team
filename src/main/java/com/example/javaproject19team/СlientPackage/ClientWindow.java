package com.example.javaproject19team.СlientPackage;

import com.example.javaproject19team.СlientPackage.ClientEditor;
import com.example.javaproject19team.СlientPackage.ClientListApp;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
public class ClientWindow extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            primaryStage.setTitle("Клієнти");

            HBox buttonsBox = new HBox(); // Використовуємо HBox для розміщення кнопок в ряд
            buttonsBox.setSpacing(10); // Відступи між кнопками
            buttonsBox.setPadding(new Insets(10, 10, 10, 10));

            Button showClientsButton = new Button("Переглянути список клієнтів");
            showClientsButton.setMinWidth(100); // Збільшуємо ширину кнопки
            showClientsButton.setOnAction(e -> showClients());

            Button addOrRemoveClientsButton = new Button("Додати або змінити клієнта");
            addOrRemoveClientsButton.setMinWidth(100); // Збільшуємо ширину кнопки
            addOrRemoveClientsButton.setOnAction(e -> showAddOrRemoveClients());


            buttonsBox.getChildren().addAll(showClientsButton, addOrRemoveClientsButton);
            Scene scene = new Scene(buttonsBox, 400, 300); // Встановлюємо висоту на 100
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAddOrRemoveClients() {
        Stage addOrRemoveClients = new Stage();
        ClientEditor clientEditor = new ClientEditor();
        try {
            clientEditor.start(addOrRemoveClients);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Showing AddOrRemove");
    }

    private void showClients() {
        Stage showClients = new Stage();
        ClientListApp clientListApp = new ClientListApp();
        try {
            clientListApp.start(showClients);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Showing ClientsListApp");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
