package com.example.javaproject19team.ReservationPackage;

import com.example.javaproject19team.HotelReservationApp;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;

import java.time.LocalDate;

public class ReservationList extends Application {
    private static Stage hotelReservationStage;

    private ReservationListener reservationListener;

    public static void setHotelReservationStage(Stage stage) {
        hotelReservationStage = stage;
    }

    public void setReservationListener(ReservationListener reservationListener) {
        this.reservationListener = reservationListener;
    }


    @Override
    public void start(Stage primaryStage) {
        hotelReservationStage.hide();
        primaryStage.setTitle("Список резервацій");
        TableView<Reservation> tableView;
        primaryStage.getIcons().add(new Image("file:src/main/resources/icon.png"));
        ObservableList<Reservation> reservations = FXCollections.observableArrayList(HotelReservationApp.getReservations());

        tableView = new TableView<>();
        tableView.setItems(reservations);


        TableColumn<Reservation, String> clientSurnameColumn = new TableColumn<>("Фамілія гостя");
        clientSurnameColumn.setCellValueFactory(data -> data.getValue().getClient().surnameProperty());

        TableColumn<Reservation, String> roomColumn = new TableColumn<>("Номер");
        roomColumn.setCellValueFactory(data -> data.getValue().getRoom().numberProperty());

        TableColumn<Reservation, LocalDate> arrivalDateColumn = new TableColumn<>("Дата початку резервації");
        arrivalDateColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
        arrivalDateColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));

        TableColumn<Reservation, LocalDate> departureDateColumn = new TableColumn<>("Дата кінця резервації");
        departureDateColumn.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
        departureDateColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));

        TableColumn<Reservation, String> statusColumn = new TableColumn<>("Статус");
        statusColumn.setCellValueFactory(data -> {
            boolean status = data.getValue().isStatus();
            String statusText = status ? "Активна" : "Не активна";
            return new SimpleStringProperty(statusText);
        });
        tableView.getColumns().addAll(clientSurnameColumn, roomColumn, arrivalDateColumn, departureDateColumn, statusColumn);

        Button addButton = new Button("Додати");
        addButton.setOnAction(e -> {
            showAddOrRemoveReservaions();
        });
        addButton.setMinWidth(148);
        addButton.setPrefHeight(35);
        addButton.setFocusTraversable(false);
        addButton.setId("menu-button");

        Button refreshButton = new Button("Оновити");
        refreshButton.setOnAction(e -> {
            reservations.clear();
            reservations.addAll(HotelReservationApp.getReservations());
        });
        refreshButton.setMinWidth(148);
        refreshButton.setPrefHeight(35);
        refreshButton.setFocusTraversable(false);
        refreshButton.setId("menu-button");

        Button onMainMenuButton = new Button("До головного меню");
        onMainMenuButton.setOnAction(e -> {
            primaryStage.hide();
            HotelReservationApp.primaryStage.show();
        });
        onMainMenuButton.setMinWidth(148);
        onMainMenuButton.setPrefHeight(35);
        onMainMenuButton.setFocusTraversable(false);
        onMainMenuButton.setId("menu-button");

        Button printButton = new Button("Друк");
        printButton.setOnAction(event -> {
            Reservation selectedReservation = tableView.getSelectionModel().getSelectedItem();
            if (selectedReservation != null) {
                printReservationInfo(selectedReservation);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Друк");
                alert.setHeaderText("Нічого не вибрано");
                alert.setContentText("Будь ласка, виберіть резервацію для друку.");
                alert.showAndWait();
            }
        });
        printButton.setMinWidth(151);
        printButton.setPrefHeight(35);
        printButton.setFocusTraversable(false);
        printButton.setId("menu-button");


        HBox buttonsBox = new HBox(addButton, refreshButton, printButton, onMainMenuButton);

        VBox filterBox = new VBox();
        Label filterText = new Label("Фільтрація за фамілією клієнта або за номером кімнати");

        TextField filterField = new TextField();
        filterField.setPromptText("Пошук...");
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            String filter = newValue.toLowerCase();
            tableView.setItems(reservations.filtered(reservation ->
                    reservation.getClient().getSurname().toLowerCase().contains(filter) ||
                            reservation.getRoom().getNumber().toLowerCase().contains(filter)
            ));
        });
        filterField.setMaxWidth(575);

        filterBox.setAlignment(Pos.CENTER);
        filterBox.getChildren().addAll(filterText,filterField);

        VBox root = new VBox(buttonsBox, filterBox, tableView);
        root.setSpacing(10);

        Scene scene = new Scene(root, 595, 500);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAddOrRemoveReservaions() {
        Stage showAddOrRemoveReservaions = new Stage();
        ReservationEditor reservationEditor = new ReservationEditor();
        reservationEditor.setReservationListener(reservationListener);
        try {
            reservationEditor.start(showAddOrRemoveReservaions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void printReservationInfo(Reservation reservation) {
        // Создание объектов Label для каждой строки информации
        Label clientLabel = new Label("Клієнт: " + reservation.getClient().getName() + " " + reservation.getClient().getSurname());
        Label roomLabel = new Label("Номер: " + reservation.getRoom().getNumber());
        Label arrivalLabel = new Label("Дата заселення: " + reservation.getArrivalDate());
        Label departureLabel = new Label("Дата виселення: " + reservation.getDepartureDate());

        // Создание объекта VBox и добавление в него объектов Label
        VBox reservationVBox = new VBox();
        reservationVBox.getChildren().addAll(clientLabel, roomLabel, arrivalLabel, departureLabel);

        // Создание объекта PrinterJob для печати
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null) {
            boolean printDialogResult = printerJob.showPrintDialog(null); // Отображение диалога печати
            if (printDialogResult) {
                // Печать содержимого
                boolean printResult = printerJob.printPage(reservationVBox); // Печать объекта VBox
                if (printResult) {
                    printerJob.endJob(); // Завершение задания печати
                } else {
                    // Если печать не удалась, отобразите сообщение об ошибке
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Помилка друку");
                    alert.setHeaderText(null);
                    alert.setContentText("Друк не вдався. Будь ласка, спробуйте ще раз.");
                    alert.showAndWait();
                }
            }
        } else {
            // Если не удалось создать объект PrinterJob, выведите сообщение об ошибке
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка печати");
            alert.setHeaderText(null);
            alert.setContentText("Не вдалося ініціалізувати завдання друку. Будь ласка, спробуйте ще раз.");
            alert.showAndWait();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}