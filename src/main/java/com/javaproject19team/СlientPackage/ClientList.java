/*
ClientList:
Клас, що відображає список клієнтів та надає можливість додавання нових клієнтів.
*/
package com.javaproject19team.СlientPackage;

import com.javaproject19team.HotelReservationApp;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClientList extends Application implements ClientListener {
    private static Stage hotelReservationStage;
    private TableView<Client> tableView;
    private final ObservableList<Client> clientsObs = FXCollections.observableArrayList();
    private ClientListener clientListener;

    // Метод для встановлення головного вікна HotelReservationApp
    public static void setHotelReservationStage(Stage stage) {
        hotelReservationStage = stage;
    }

    // Метод для встановлення ClientListener
    public void setClientListener(ClientListener clientListener) {
        this.clientListener = clientListener;
    }

    // Метод, що викликається при старті додатку
    @Override
    public void start(Stage primaryStage) {
        // Ховаємо головне вікно HotelReservationApp
        hotelReservationStage.hide();

        // Налаштування вікна
        primaryStage.getIcons().add(new Image("file:src/main/resources/icon.png"));
        primaryStage.setTitle("Список клієнтів");

        // Додавання клієнтів з HotelReservationApp до ObservableList
        clientsObs.addAll(HotelReservationApp.getClients());
        tableView = new TableView<>();
        tableView.setItems(clientsObs);

        // Налаштування колонок таблиці
        TableColumn<Client, String> nameColumn = new TableColumn<>("Ім'я");
        nameColumn.setCellValueFactory(data -> data.getValue().nameProperty());

        TableColumn<Client, String> surnameColumn = new TableColumn<>("Прізвище");
        surnameColumn.setCellValueFactory(data -> data.getValue().surnameProperty());

        TableColumn<Client, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(data -> data.getValue().emailProperty());

        TableColumn<Client, String> phoneColumn = new TableColumn<>("Телефон");
        phoneColumn.setCellValueFactory(data -> data.getValue().phoneProperty());

        tableView.getColumns().addAll(nameColumn, surnameColumn, emailColumn, phoneColumn);

        // Кнопка "Додати"
        Button addButton = new Button("Додати");
        addButton.setOnAction(e -> showAddOrRemoveClients());
        addButton.setId("menu-button");
        addButton.setFocusTraversable(false);
        addButton.setMinWidth(163);

        // Кнопка "Оновити"
        Button refreshButton = new Button("Оновити");
        refreshButton.setOnAction(e -> {
            // Оновлення списку клієнтів
            clientsObs.clear();
            clientsObs.addAll(HotelReservationApp.getClients());
        });
        refreshButton.setId("menu-button");
        refreshButton.setFocusTraversable(false);
        refreshButton.setMinWidth(163);

        // Кнопка "До головного меню"
        Button onMainMenuButton = new Button("До головного меню");
        onMainMenuButton.setOnAction(e -> {
            // Повернення до головного вікна HotelReservationApp
            primaryStage.hide();
            HotelReservationApp.primaryStage.show();
        });
        GridPane.setConstraints(onMainMenuButton, 0, 4);
        onMainMenuButton.setId("menu-button");
        onMainMenuButton.setFocusTraversable(false);
        onMainMenuButton.setMinWidth(166);

        // Розміщення кнопок
        HBox buttonsBox = new HBox(addButton, refreshButton, onMainMenuButton);

        // Фільтрація за інформацією про клієнт
        VBox filterBox = new VBox();
        Label filterText = new Label("Фільтрація за інформацією про клієнта");
        TextField filterField = new TextField();
        filterField.setPromptText("Пошук...");
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            String filter = newValue.toLowerCase();
            tableView.setItems(clientsObs.filtered(client ->
                    client.getName().toLowerCase().contains(filter) ||
                            client.getSurname().toLowerCase().contains(filter) ||
                            client.getEmail().toLowerCase().contains(filter) ||
                            client.getPhone().toLowerCase().contains(filter)
            ));
        });
        filterField.setMaxWidth(470);

        filterBox.setAlignment(Pos.CENTER);
        filterBox.getChildren().addAll(filterText, filterField);

        // Головний контейнер VBox
        VBox root = new VBox(buttonsBox, filterBox, tableView);
        root.setSpacing(10);

        // Scene, яка відображається у вікні
        Scene scene = new Scene(root, 490, 500);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Метод, який викликається при збереженні клієнта
    @Override
    public void onClientSaved(Client client) {
        // Додаємо нового клієнта до списку
        clientsObs.add(client);
    }

    // Метод для відображення вікна додавання або видалення клієнта
    private void showAddOrRemoveClients() {
        Stage addOrRemoveClients = new Stage();
        ClientEditor clientEditor = new ClientEditor();
        clientEditor.setClientListener(clientListener);
        try {
            clientEditor.start(addOrRemoveClients);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Showing AddOrRemove");
    }

    // Метод, який викликається при закритті вікна
    @Override
    public void stop() throws Exception {
        super.stop();
        // Показуємо HotelReservationApp при закритті ClientListApp
        hotelReservationStage.show();
    }

    // Точка входу у програму
    public static void main(String[] args) {
        launch(args);
    }
}