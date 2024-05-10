package com.example.javaproject19team.ReservationPackage;

import com.example.javaproject19team.HotelReservationApp;
import com.example.javaproject19team.RoomPackage.Room;
import com.example.javaproject19team.СlientPackage.Client;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
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

import java.time.LocalDate;
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


    public static void main(String[] args) {
        launch(args);
    }
}