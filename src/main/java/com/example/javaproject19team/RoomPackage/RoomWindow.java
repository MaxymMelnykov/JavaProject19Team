package com.example.javaproject19team.RoomPackage;

import com.example.javaproject19team.RoomPackage.RoomAdd;
import com.example.javaproject19team.RoomPackage.RoomListApp;
import com.example.javaproject19team.СlientPackage.ClientListener;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
public class RoomWindow extends Application{
    private RoomListener roomListener;

    public void setRoomListener(RoomListener roomListener) {
        this.roomListener = roomListener;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Номери");

        HBox buttonsBox = new HBox(); // Використовуємо HBox для розміщення кнопок в ряд
        buttonsBox.setSpacing(10); // Відступи між кнопками
        buttonsBox.setPadding(new Insets(10, 10, 10, 10));

        Button showRoomsButton = new Button("Переглянути список номерів");
        showRoomsButton.setMinWidth(100); // Збільшуємо ширину кнопки
        showRoomsButton.setOnAction(e -> showRooms());

        Button showAddRoomsButton = new Button("Додати номер");
        showAddRoomsButton.setMinWidth(100); // Збільшуємо ширину кнопки
        showAddRoomsButton.setOnAction(e -> showRoomAdd());

        buttonsBox.getChildren().addAll(showRoomsButton, showAddRoomsButton);
        Scene scene = new Scene(buttonsBox, 400, 300); // Встановлюємо висоту на 100
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showRooms() {
        Stage showRooms = new Stage();
        RoomListApp roomListApp = new RoomListApp();
        try {
            roomListApp.start(showRooms);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Showing showRooms");
    }


    private void showRoomAdd() {
        Stage showRooms = new Stage();
        RoomAdd roomAdd = new RoomAdd();
        roomAdd.setRoomListener(roomListener);
        try {
            roomAdd.start(showRooms);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Showing roomAdd");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
