package com.example.javaproject19team.ReservationPackage;

import com.example.javaproject19team.HotelReservationApp;
import com.example.javaproject19team.RoomPackage.Room;
import com.example.javaproject19team.RoomPackage.RoomListener;
import com.example.javaproject19team.СlientPackage.Client;
import com.example.javaproject19team.СlientPackage.ClientListApp;
import com.example.javaproject19team.СlientPackage.ClientListener;
import com.example.javaproject19team.СlientPackage.ClientWindow;
import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ReservationEditor extends Application {
    ReservationListener reservationListener;
    ObservableList<Room> roomsObservableList = FXCollections.observableArrayList(HotelReservationApp.getRooms());
    ObservableList<Client> clientsObservableList = FXCollections.observableArrayList(HotelReservationApp.getClients());

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Reservation Editor");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label dateLabelArrival = new Label("Дата заселення:");
        GridPane.setConstraints(dateLabelArrival, 0, 2);

        Label dateLabelDeparture = new Label("Дата виселення:");
        GridPane.setConstraints(dateLabelDeparture, 0, 3);

        ComboBox<Client> clientComboBox = new ComboBox<>();
        clientComboBox.setPromptText("Виберіть клієнта");
        clientComboBox.getItems().addAll(clientsObservableList);
        GridPane.setConstraints(clientComboBox, 0, 0);
        GridPane.setColumnSpan(clientComboBox, 2);
        clientComboBox.setMinWidth(280);

        ComboBox<Room> roomComboBox = new ComboBox<>();
        roomComboBox.setPromptText("Виберіть номер");
        roomComboBox.getItems().addAll(roomsObservableList);
        GridPane.setConstraints(roomComboBox, 0, 1);
        GridPane.setColumnSpan(roomComboBox, 2);
        roomComboBox.setMinWidth(280);

        DatePicker datePickerArrival = new DatePicker();
        GridPane.setConstraints(datePickerArrival, 1, 2);

        DatePicker datePickerDeparture = new DatePicker();
        GridPane.setConstraints(datePickerDeparture, 1, 3);

        Button cancelButton = new Button("Скасувати");
        cancelButton.setMinWidth(100);
        cancelButton.setOnAction(e -> primaryStage.close());

        GridPane.setConstraints(cancelButton, 0, 4);

        Button saveButton = new Button("Зберегти");
        saveButton.setMinWidth(100);
        GridPane.setConstraints(saveButton, 1, 4);
        //saveButton.setOnAction(e -> saveReservation();

        grid.getChildren().addAll(dateLabelArrival, dateLabelDeparture, clientComboBox, roomComboBox, datePickerArrival, datePickerDeparture, cancelButton, saveButton);

        Scene scene = new Scene(grid, 300, 190);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    /*
    Дописать
    private void saveReservation(Client client, Room room, StringProperty arrivalDate, StringProperty departureDate, ) {}
     */


    public void setReservationListener(ReservationListener listener) {
        this.reservationListener = listener;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
