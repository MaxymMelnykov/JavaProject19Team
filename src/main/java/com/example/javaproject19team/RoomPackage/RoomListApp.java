package com.example.javaproject19team.RoomPackage;

import com.example.javaproject19team.HotelReservationApp;
import com.example.javaproject19team.RoomPackage.Room;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RoomListApp extends Application {
    private RoomListener roomListener;
    private static Stage hotelReservationStage;

    public void setRoomListener(RoomListener roomListener) {
        this.roomListener = roomListener;
    }
    public static void setHotelReservationStage(Stage stage) {
        hotelReservationStage = stage;
    }

    @Override
    public void start(Stage primaryStage) {
        hotelReservationStage.hide();
        //TODO Менять статус комнаты когда по ней создана резервация.

        primaryStage.setTitle("Список доступних номерів");

        ObservableList<Room> rooms = FXCollections.observableArrayList(HotelReservationApp.getRooms());
        TableView<Room> tableView = new TableView<>(rooms);

        TableColumn<Room, String> numberColumn = new TableColumn<>("Номер");
        numberColumn.setCellValueFactory(data -> data.getValue().numberProperty());

        TableColumn<Room, String> typeColumn = new TableColumn<>("Тип");
        typeColumn.setCellValueFactory(data -> data.getValue().typeProperty());

        TableColumn<Room, Number> priceColumn = new TableColumn<>("Ціна");
        priceColumn.setCellValueFactory(data -> data.getValue().priceProperty());

        TableColumn<Room, String> detailsColumn = new TableColumn<>("Детальна інформація");
        detailsColumn.setCellValueFactory(data -> data.getValue().detailsProperty());

        TableColumn<Room, String> statusColumn = new TableColumn<>("Статус");
        statusColumn.setCellValueFactory(data -> {
            boolean status = data.getValue().isStatus();
            String statusText = status ? "Вільний" : "Зайнятий";
            return new SimpleStringProperty(statusText);
        });

        tableView.getColumns().addAll(numberColumn, typeColumn, priceColumn, detailsColumn,statusColumn);


        Button addButton = new Button("Додати");
        addButton.setOnAction(e -> {
            showRoomAdd();
        });


        Button refreshButton = new Button("Оновити");
        refreshButton.setOnAction(e -> {
            rooms.clear();
            rooms.addAll(HotelReservationApp.getRooms());
        });
        Button onMainMenuButton = new Button("До головного меню");
        onMainMenuButton.setOnAction(e -> {
            primaryStage.hide();
            HotelReservationApp.primaryStage.show();
        });
        GridPane.setConstraints(onMainMenuButton, 0, 4);

        HBox buttonsBox = new HBox(addButton, refreshButton,onMainMenuButton);
        buttonsBox.setSpacing(10);
        buttonsBox.setPadding(new Insets(10));

        VBox root = new VBox(buttonsBox, tableView);
        root.setSpacing(10);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    @Override
    public void stop() throws Exception {
        super.stop();
        hotelReservationStage.show();
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
