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
public class NumbersWindow extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Номери");

        HBox buttonsBox = new HBox(); // Використовуємо HBox для розміщення кнопок в ряд
        buttonsBox.setSpacing(10); // Відступи між кнопками
        buttonsBox.setPadding(new Insets(10, 10, 10, 10));

        Button showNumbersButton = new Button("Переглянути список номерів");
        showNumbersButton.setMinWidth(100); // Збільшуємо ширину кнопки

        Button addNumbersButton = new Button("Додати номер");
        addNumbersButton.setMinWidth(100); // Збільшуємо ширину кнопки

        buttonsBox.getChildren().addAll(showNumbersButton, addNumbersButton);
        Scene scene = new Scene(buttonsBox, 400, 300); // Встановлюємо висоту на 100
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
