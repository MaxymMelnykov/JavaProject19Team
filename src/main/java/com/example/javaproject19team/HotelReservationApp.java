package com.example.javaproject19team;

import com.example.javaproject19team.DatabasePackage.DatabaseHandler;
import com.example.javaproject19team.ReservationPackage.Reservation;
import com.example.javaproject19team.ReservationPackage.ReservationListener;
import com.example.javaproject19team.ReservationPackage.ReservationWindow;
import com.example.javaproject19team.RoomPackage.Room;
import com.example.javaproject19team.RoomPackage.RoomListener;
import com.example.javaproject19team.RoomPackage.RoomWindow;
import com.example.javaproject19team.СlientPackage.Client;
import com.example.javaproject19team.СlientPackage.ClientListener;
import com.example.javaproject19team.СlientPackage.ClientWindow;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class HotelReservationApp extends Application implements ClientListener, RoomListener, ReservationListener {
    static ArrayList<Client> clients = new ArrayList<>();
    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Reservation> reservations = new ArrayList<>();
    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Головний екран");
        VBox mainContainer = new VBox();
        mainContainer.setPadding(new Insets(10));
        mainContainer.setSpacing(10);

        HBox buttonsBox = new HBox();
        buttonsBox.setSpacing(10);
        buttonsBox.setPadding(new Insets(10, 10, 10, 10));

        Button reservationsButton = new Button("Резервації");
        reservationsButton.setMinWidth(100);
        reservationsButton.setOnAction(e -> showReservations());

        Button clientsButton = new Button("Клієнти");
        clientsButton.setMinWidth(100);
        clientsButton.setOnAction(e -> showClients());

        Button roomsButton = new Button("Номери");
        roomsButton.setMinWidth(100);
        roomsButton.setOnAction(e -> showRooms());

        buttonsBox.getChildren().addAll(reservationsButton, clientsButton, roomsButton);


        HBox typeRoomBox = new HBox();
        typeRoomBox.setSpacing(10);
        Label typeRoomLabel = new Label("Виберіть тип номеру ");
        ComboBox<String> roomComboBox = new ComboBox<>();
        roomComboBox.getItems().addAll("Одномісний", "Двохмісний", "Багатовмісний");
        typeRoomBox.getChildren().addAll(typeRoomLabel, roomComboBox);

        HBox reservedRoomsNumberBox = new HBox();
        reservedRoomsNumberBox.setSpacing(10);
        Label reservedRoomsLabel = new Label("Кількість зайнятих номерів :         32");
        reservedRoomsNumberBox.getChildren().addAll(reservedRoomsLabel);


        HBox unreservedRoomsNumberBox = new HBox();
        unreservedRoomsNumberBox.setSpacing(10);
        Label unreservedRoomsLabel = new Label("Кількість доступних номерів :      56");
        unreservedRoomsNumberBox.getChildren().addAll(unreservedRoomsLabel);

        mainContainer.getChildren().addAll(buttonsBox, typeRoomBox, reservedRoomsNumberBox, unreservedRoomsNumberBox);

        Scene scene = new Scene(mainContainer, 360, 150);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void showReservations() {
        Stage reservationStage = new Stage();
        ReservationWindow reservationWindow = new ReservationWindow();
        reservationWindow.setReservationListener(this);
        try {
            reservationWindow.start(reservationStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Showing Reservations");
    }


    private void showClients() {
        Stage clientsStage = new Stage();
        ClientWindow clientWindow = new ClientWindow();
        clientWindow.setClientListener(this);
        try {
            clientWindow.start(clientsStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Showing Clients");
    }


    private void showRooms() {
        Stage roomStage = new Stage();
        RoomWindow roomWindow = new RoomWindow();
        roomWindow.setRoomListener(this);
        try {
            roomWindow.start(roomStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Showing Rooms");
    }

    @Override
    public void onClientSaved(Client client) {
        clients.add(client);
        System.out.println(clients.get(0).getName());
    }
    @Override
    public void onRoomSaved(Room room) {
        rooms.add(room);
        System.out.println(rooms.get(0).getNumber());
    }
    public void onReservationSaved(Reservation reservation){
        reservations.add(reservation);
        System.out.println(reservations.get(0).getArrivalDate());
    }


    public static ArrayList<Client> getClients() {
        return clients;
    }

    public static ArrayList<Room> getRooms() {
        return rooms;
    }

    public static ArrayList<Reservation> getReservations() {
        return reservations;
    }

    public static void main(String[] args) {
        launch(args);

    }
}