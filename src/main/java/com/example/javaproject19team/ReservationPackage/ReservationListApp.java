package com.example.javaproject19team.ReservationPackage;

import com.example.javaproject19team.HotelReservationApp;
import com.example.javaproject19team.RoomPackage.Room;
import com.example.javaproject19team.СlientPackage.Client;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import org.w3c.dom.Text;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ReservationListApp extends Application {
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
        TableView<Reservation> tableView;
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
        tableView.getColumns().addAll(clientSurnameColumn,roomColumn, arrivalDateColumn, departureDateColumn, statusColumn);

        Button addButton = new Button("Додати");
        addButton.setOnAction(e -> {
            showAddOrRemoveReservaions();
        });

        Button refreshButton = new Button("Оновити");
        refreshButton.setOnAction(e -> {
            reservations.clear();
            reservations.addAll(HotelReservationApp.getReservations());
        });
        Button onMainMenuButton = new Button("До головного меню");
        onMainMenuButton.setOnAction(e -> {
            primaryStage.hide();
            HotelReservationApp.primaryStage.show();
        });

        Button printButton = new Button("Печать");
        printButton.setOnAction(event -> {
            Reservation selectedReservation = tableView.getSelectionModel().getSelectedItem();
            if (selectedReservation != null) {
                printReservationInfo(selectedReservation);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Печать");
                alert.setHeaderText("Ничего не выбрано");
                alert.setContentText("Пожалуйста, выберите резервацию для печати.");
                alert.showAndWait();
            }
        });


        GridPane.setConstraints(onMainMenuButton, 0, 4);

        HBox buttonsBox = new HBox(addButton, refreshButton,onMainMenuButton,printButton);
        buttonsBox.setSpacing(10);
        buttonsBox.setPadding(new Insets(10));

        TextField filterField = new TextField();
        filterField.setPromptText("Пошук...");
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            String filter = newValue.toLowerCase();
            tableView.setItems(reservations.filtered(reservation ->
                    reservation.getClient().getSurname().toLowerCase().contains(filter) ||
                            reservation.getRoom().getNumber().toLowerCase().contains(filter)
            ));
        });

        VBox root = new VBox(buttonsBox,filterField, tableView);
        root.setSpacing(10);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 600, 400);
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
        System.out.println("Showing showRooms");
    }



    private void printReservationInfo(Reservation reservation) {
        // Создание объектов Label для каждой строки информации
        Label clientLabel = new Label("Клиент: " + reservation.getClient().getName() + " " + reservation.getClient().getSurname());
        Label roomLabel = new Label("Номер комнаты: " + reservation.getRoom().getNumber());
        Label arrivalLabel = new Label("Дата заезда: " + reservation.getArrivalDate());
        Label departureLabel = new Label("Дата выезда: " + reservation.getDepartureDate());

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
                    alert.setTitle("Ошибка печати");
                    alert.setHeaderText(null);
                    alert.setContentText("Печать не удалась. Пожалуйста, попробуйте еще раз.");
                    alert.showAndWait();
                }
            }
        } else {
            // Если не удалось создать объект PrinterJob, выведите сообщение об ошибке
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка печати");
            alert.setHeaderText(null);
            alert.setContentText("Не удалось инициализировать задание печати. Пожалуйста, попробуйте еще раз.");
            alert.showAndWait();
        }
    }




    public static void main(String[] args) {
        launch(args);
    }
}