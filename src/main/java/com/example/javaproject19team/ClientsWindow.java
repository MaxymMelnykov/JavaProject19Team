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
public class ClientsWindow extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Клієнти");

        HBox buttonsBox = new HBox(); // Використовуємо HBox для розміщення кнопок в ряд
        buttonsBox.setSpacing(10); // Відступи між кнопками
        buttonsBox.setPadding(new Insets(10, 10, 10, 10));

        Button showReservationsButton = new Button("Переглянути список клієнтів");
        showReservationsButton.setMinWidth(100); // Збільшуємо ширину кнопки

        Button addOrRemoveReservationsButton = new Button("Додати або змінити клієнта");
        addOrRemoveReservationsButton.setMinWidth(100); // Збільшуємо ширину кнопки

        buttonsBox.getChildren().addAll(showReservationsButton, addOrRemoveReservationsButton);
        Scene scene = new Scene(buttonsBox, 400, 300); // Встановлюємо висоту на 100
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
