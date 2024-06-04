/*
ReservationList:
Клас, який відображає список резервацій та надає можливість додавання нових резервацій.
*/
package com.javaproject19team.ReservationPackage;

import com.javaproject19team.HotelReservationApp;
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

/**
 * Клас ReservationList відповідає за відображення списку резервацій
 * та надає можливість додавання нових резервацій.
 */
public class ReservationList extends Application {
    private static Stage hotelReservationStage;

    private ReservationListener reservationListener;


    /**
     * Встановлює головне вікно програми.
     *
     * @param stage головне вікно програми
     */

    public static void setHotelReservationStage(Stage stage) {
        hotelReservationStage = stage;
    }


    /**
     * Встановлює об'єкт ReservationListener для обробки подій резервацій.
     *
     * @param reservationListener об'єкт ReservationListener
     */

    public void setReservationListener(ReservationListener reservationListener) {
        this.reservationListener = reservationListener;
    }


    /**
     * Метод, що викликається при старті додатку.
     *
     * @param primaryStage головне вікно додатку
     */

    @Override
    public void start(Stage primaryStage) {
        hotelReservationStage.hide();  // Приховуємо головне вікно програми
        primaryStage.setTitle("Список резервацій");
        TableView<Reservation> tableView;
        primaryStage.getIcons().add(new Image("file:src/main/resources/icon.png"));
        ObservableList<Reservation> reservations = FXCollections.observableArrayList(HotelReservationApp.getReservations());

        // Створення таблиці резервацій
        tableView = new TableView<>();
        tableView.setItems(reservations);

        // Встановлення колонок для таблиці
        TableColumn<Reservation, String> clientSurnameColumn = new TableColumn<>("Прізвище гостя");
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

        // Кнопки для керування списком резервацій
        Button addButton = new Button("Додати");
        addButton.setOnAction(e -> showAddOrRemoveReservaions());
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

        // Групування кнопок
        HBox buttonsBox = new HBox(addButton, refreshButton, printButton, onMainMenuButton);

        // Фільтр для пошуку резервацій за прізвищем або номером кімнати
        VBox filterBox = new VBox();
        Label filterText = new Label("Фільтрація за прізвищем клієнта або за номером кімнати");

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
        filterBox.getChildren().addAll(filterText, filterField);

        // Головний контейнер для розміщення всіх елементів у вікні
        VBox root = new VBox(buttonsBox, filterBox, tableView);
        root.setSpacing(10);

        // Створення та налаштування сцени
        Scene scene = new Scene(root, 595, 500);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    /**
     * Метод для додавання або видалення резервацій.
     */

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


    /**
     * Метод друкує інформацію про обрану резервацію.
     *
     * @param reservation обрана резервація для друку
     */

    private void printReservationInfo(Reservation reservation) {
        // Створюємо елементи Label для кожного рядка інформації
        Label clientLabel = new Label("Клієнт: " + reservation.getClient().getName() + " " + reservation.getClient().getSurname());
        Label roomLabel = new Label("Номер: " + reservation.getRoom().getNumber());
        Label arrivalLabel = new Label("Дата заселення: " + reservation.getArrivalDate());
        Label departureLabel = new Label("Дата виселення: " + reservation.getDepartureDate());

        // Створюємо контейнер VBox та додаємо до нього елементи Label
        VBox reservationVBox = new VBox();
        reservationVBox.getChildren().addAll(clientLabel, roomLabel, arrivalLabel, departureLabel);

        // Створюємо об'єкт PrinterJob для друку
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null) {
            boolean printDialogResult = printerJob.showPrintDialog(null); //  Відображення вікна для друку
            if (printDialogResult) {
                // Друкуємо вміст
                boolean printResult = printerJob.printPage(reservationVBox); // Друкуємо об'єкт VBox
                if (printResult) {
                    printerJob.endJob(); // Завершуємо завдання друку
                } else {
                    // Якщо друку не вдалося, показуємо повідомлення про помилку
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Помилка друку");
                    alert.setHeaderText(null);
                    alert.setContentText("Друк не вдався. Будь ласка, спробуйте ще раз.");
                    alert.showAndWait();
                }
            }
        } else {
            // Якщо не вдалося створити об'єкт PrinterJob, показуємо повідомлення про помилку
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка печати");
            alert.setHeaderText(null);
            alert.setContentText("Не вдалося ініціалізувати завдання друку. Будь ласка, спробуйте ще раз.");
            alert.showAndWait();
        }
    }

    // Точка входу у програму
    public static void main(String[] args) {
        launch(args);
    }
}
