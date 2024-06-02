/*
RoomList:
Клас, який відображає список номерів та надає можливість додавання нових номерів.
*/
package com.javaproject19team.RoomPackage;

import com.javaproject19team.HotelReservationApp;
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

/**
 * RoomList:
 * Клас, який відображає список номерів та надає можливість додавання нових номерів.
 */
public class RoomList extends Application {
    private RoomListener roomListener;
    private static Stage hotelReservationStage;

    /**
     * Метод для встановлення слухача подій для збереження номера кімнати
     *
     * @param roomListener Об'єкт слухача подій
     */
    public void setRoomListener(RoomListener roomListener) {
        this.roomListener = roomListener;
    }

    /**
     * Метод для встановлення посилання на головне вікно додатку
     *
     * @param stage Головне вікно додатку
     */
    public static void setHotelReservationStage(Stage stage) {
        hotelReservationStage = stage;
    }

    /**
     * Метод, що викликається при старті додатку.
     *
     * @param primaryStage головне вікно додатку
     */
    @Override
    public void start(Stage primaryStage) {
        // Ховаємо головне вікно додатку
        hotelReservationStage.hide();
        // Заголовок вікна
        primaryStage.getIcons().add(new Image("file:src/main/resources/icon.png"));
        primaryStage.setTitle("Список номерів");

        // Список кімнат
        ObservableList<Room> rooms = FXCollections.observableArrayList(HotelReservationApp.getRooms());
        TableView<Room> tableView = new TableView<>(rooms);

        // Колонки таблиці
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

        tableView.getColumns().addAll(numberColumn, typeColumn, priceColumn, detailsColumn, statusColumn);

        // Кнопка для додавання нового номера
        Button addButton = new Button("Додати");
        addButton.setOnAction(e -> showRoomAdd());
        addButton.setMinWidth(175);
        addButton.setPrefHeight(35);
        addButton.setFocusTraversable(false);
        addButton.setId("menu-button");

        // Кнопка для оновлення списку номерів
        Button refreshButton = new Button("Оновити");
        refreshButton.setOnAction(e -> {
            rooms.clear();
            rooms.addAll(HotelReservationApp.getRooms());
        });
        refreshButton.setMinWidth(175);
        refreshButton.setPrefHeight(35);
        refreshButton.setFocusTraversable(false);
        refreshButton.setId("menu-button");

        // Кнопка для повернення до головного меню
        Button onMainMenuButton = new Button("До головного меню");
        onMainMenuButton.setOnAction(e -> {
            primaryStage.hide();
            HotelReservationApp.primaryStage.show();
        });
        onMainMenuButton.setMinWidth(170);
        onMainMenuButton.setPrefHeight(35);
        onMainMenuButton.setFocusTraversable(false);
        onMainMenuButton.setId("menu-button");

        // HBox контейнер для кнопок
        HBox buttonsBox = new HBox(addButton, refreshButton, onMainMenuButton);
        buttonsBox.setSpacing(0);

        // VBox контейнер для фільтрів
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
        filterBox.getChildren().addAll(filterText, filterField);

        // Вертикальний контейнер для фільтрації за ціною
        VBox filterPriceVBox = new VBox();
        Label filterPriceLabel = new Label("Фільтрація за ціною");

        TextField minPriceField = new TextField();
        minPriceField.setPromptText("Мінімальна ціна");

        TextField maxPriceField = new TextField();
        maxPriceField.setPromptText("Максимальна ціна");

        Button filterButton = new Button("Фільтрувати");
        filterButton.setMinHeight(31);
        filterButton.setMaxHeight(31);

        // Обробник події для кнопки фільтрації
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

        // HBox контейнер для полів та кнопки фільтрації за ціною
        HBox priceFilterBox = new HBox(minPriceField, maxPriceField, filterButton);
        priceFilterBox.setSpacing(10);
        priceFilterBox.setAlignment(Pos.CENTER);
        filterPriceVBox.setAlignment(Pos.CENTER);
        filterPriceVBox.getChildren().addAll(filterPriceLabel, priceFilterBox);

        // VBox контейнер, що містить всі інші контроли
        VBox root = new VBox(buttonsBox, filterBox, filterPriceVBox, tableView);
        root.setSpacing(10);

        // Scene, яка відображається у вікні
        Scene scene = new Scene(root, 520, 570);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Метод, який викликається при закритті вікна
     *
     * @throws Exception Виняток при закритті вікна
     */
    @Override
    public void stop() throws Exception {
        super.stop();
        // При закритті вікна повертаємо головне вікно додатку
        hotelReservationStage.show();
    }

    /**
     * Метод для відображення вікна додавання нового номера
     */
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
    // Точка входу у програму
    public static void main(String[] args) {
        launch(args);
    }
}
