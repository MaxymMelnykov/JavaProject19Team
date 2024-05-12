package com.example.javaproject19team.RoomPackage;

import com.example.javaproject19team.HotelReservationApp;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RoomList extends Application {
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
        primaryStage.getIcons().add(new Image("file:src/main/resources/icon.png"));
        primaryStage.setTitle("Список номерів");

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
        addButton.setMinWidth(175);
        addButton.setPrefHeight(35);
        addButton.setFocusTraversable(false);
        addButton.setId("menu-button");

        Button refreshButton = new Button("Оновити");
        refreshButton.setOnAction(e -> {
            rooms.clear();
            rooms.addAll(HotelReservationApp.getRooms());
        });
        refreshButton.setMinWidth(175);
        refreshButton.setPrefHeight(35);
        refreshButton.setFocusTraversable(false);
        refreshButton.setId("menu-button");

        Button onMainMenuButton = new Button("До головного меню");
        onMainMenuButton.setOnAction(e -> {
            primaryStage.hide();
            HotelReservationApp.primaryStage.show();
        });
        onMainMenuButton.setMinWidth(170);
        onMainMenuButton.setPrefHeight(35);
        onMainMenuButton.setFocusTraversable(false);
        onMainMenuButton.setId("menu-button");

        HBox buttonsBox = new HBox(addButton, refreshButton,onMainMenuButton);
        buttonsBox.setSpacing(0);

        VBox filterBox = new VBox();
        Label filterText = new Label("Фільтрація за номером, або типом, або по детальній інформації кімнати");

        TextField filterField = new TextField();
        filterField.setMaxWidth(500);
        filterField.setPromptText("Пошук...");
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            String filter = newValue.toLowerCase();
            tableView.setItems(rooms.filtered(room ->
                    room.getNumber().toLowerCase().contains(filter) ||
                            room.getType().toLowerCase().contains(filter) ||
                            room.getDetails().toLowerCase().contains(filter)
            ));
        });

        filterBox.setAlignment(Pos.CENTER);
        filterBox.getChildren().addAll(filterText,filterField);

        VBox filterPriceVBox = new VBox();

        Label filterPriceLabel = new Label("Фільтрація за ціною");

        TextField minPriceField = new TextField();
        minPriceField.setPromptText("Мінімальна ціна");

        TextField maxPriceField = new TextField();
        maxPriceField.setPromptText("Максимальна ціна");

        Button filterButton = new Button("Фільтрувати");
        filterButton.setMinHeight(31);
        filterButton.setMaxHeight(31);


        filterButton.setOnAction(event -> {
            String filter = filterField.getText().toLowerCase();
            double minPrice = minPriceField.getText().isEmpty() ? 0 : Double.parseDouble(minPriceField.getText());
            double maxPrice = maxPriceField.getText().isEmpty() ? Double.MAX_VALUE : Double.parseDouble(maxPriceField.getText());
            tableView.setItems(rooms.filtered(room ->
                    (room.getNumber().toLowerCase().contains(filter) ||
                            room.getType().toLowerCase().contains(filter) ||
                            room.getDetails().toLowerCase().contains(filter)) &&
                            room.getPrice() >= minPrice && room.getPrice() <= maxPrice
            ));
        });


        HBox priceFilterBox = new HBox(minPriceField,maxPriceField,filterButton);
        priceFilterBox.setSpacing(10);
        priceFilterBox.setAlignment(Pos.CENTER);
        filterPriceVBox.setAlignment(Pos.CENTER);
        filterPriceVBox.getChildren().addAll(filterPriceLabel,priceFilterBox);

        VBox root = new VBox(buttonsBox,filterBox,filterPriceVBox, tableView);
        root.setSpacing(10);

        Scene scene = new Scene(root, 520, 570);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
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
        RoomEditor roomEditor = new RoomEditor();
        roomEditor.setRoomListener(roomListener);
        try {
            roomEditor.start(showRooms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
