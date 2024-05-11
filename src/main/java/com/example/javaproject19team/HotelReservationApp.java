package com.example.javaproject19team;

import com.example.javaproject19team.DatabasePackage.DatabaseHandler;
import com.example.javaproject19team.ReservationPackage.Reservation;
import com.example.javaproject19team.ReservationPackage.ReservationListApp;
import com.example.javaproject19team.ReservationPackage.ReservationListener;
import com.example.javaproject19team.RoomPackage.Room;
import com.example.javaproject19team.RoomPackage.RoomListApp;
import com.example.javaproject19team.RoomPackage.RoomListener;
import com.example.javaproject19team.СlientPackage.Client;
import com.example.javaproject19team.СlientPackage.ClientListApp;
import com.example.javaproject19team.СlientPackage.ClientListener;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class HotelReservationApp extends Application implements ClientListener, RoomListener, ReservationListener {
    static ArrayList<Client> clients = new ArrayList<>();
    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Reservation> reservations = new ArrayList<>();
    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {

        HotelReservationApp.primaryStage = primaryStage;
        HotelReservationApp.primaryStage.getIcons().add(new Image("file:src/main/resources/icon.png"));

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

        HBox reservedRoomsNumberBox = new HBox();
        reservedRoomsNumberBox.setSpacing(10);
        Label reservedRoomsLabel = new Label("Кількість зайнятих номерів :      "  + (rooms.size() - getFreeRooms().size()));
        reservedRoomsNumberBox.getChildren().addAll(reservedRoomsLabel);


        HBox unreservedRoomsNumberBox = new HBox();
        unreservedRoomsNumberBox.setSpacing(10);
        Label unreservedRoomsLabel = new Label("Кількість доступних номерів :      " + getFreeRooms().size());
        unreservedRoomsNumberBox.getChildren().addAll(unreservedRoomsLabel);

        mainContainer.getChildren().addAll(buttonsBox, typeRoomBox, reservedRoomsNumberBox, unreservedRoomsNumberBox);

        Scene scene = new Scene(mainContainer, 360, 150);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void showReservations() {
        Stage reservationStage = new Stage();
        ReservationListApp reservationListApp = new ReservationListApp();
        reservationListApp.setReservationListener(this);
        ReservationListApp.setHotelReservationStage(primaryStage);
        try {
            reservationListApp.start(reservationStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Showing Reservations");
    }


    private void showClients() {
        Stage clientsStage = new Stage();
        ClientListApp clientListApp = new ClientListApp();
        clientListApp.setClientListener(this);
        ClientListApp.setHotelReservationStage(primaryStage); // Передаем ссылку на primaryStage HotelReservationApp
        try {
            clientListApp.start(clientsStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Showing Clients");
    }


    private void showRooms() {
        Stage roomStage = new Stage();
        RoomListApp roomListApp = new RoomListApp();
        roomListApp.setRoomListener(this);
        RoomListApp.setHotelReservationStage(primaryStage);
        try {
            roomListApp.start(roomStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Showing Rooms");
    }

    @Override
    public void onClientSaved(Client client) {
        clients.add(client);
        System.out.println(clients.get(0).getName());
        System.out.println(DatabaseHandler.countClientsFromDB());
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

    public static void getClientsFromDB(){
        int countClientsInDB = DatabaseHandler.countClientsFromDB();
        for (int i = 0; i < countClientsInDB; i++) {
            try {
                clients.add(i,DatabaseHandler.getClientFromDB(i + 1));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void getRoomsFromDB(){
        int countRoomsInDB = DatabaseHandler.countRoomsFromDB();
        for(int i = 0; i < countRoomsInDB; i++){
            try {
                rooms.add(i,DatabaseHandler.getRoomFromDB(i + 1));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void getReservationsFromDB(){
        int countReservaionsInDB = DatabaseHandler.countReservationsFromDB();
        for(int i = 0; i < countReservaionsInDB; i++){
            try {
                reservations.add(i,DatabaseHandler.getReservationFromDB(i + 1));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<Client> getClients() {
        return clients;
    }

    public static ArrayList<Room> getRooms() {
        return rooms;
    }
    public static ArrayList<Room> getFreeSingleRooms() {
        ArrayList<Room> singleRooms = new ArrayList<>();
        for(Room room : rooms){
            if(Objects.equals(room.getType(), "Одномістний") && room.isStatus()){
                singleRooms.add(room);
            }
        }
        return singleRooms;
    }
    public static ArrayList<Room> getFreePairRooms() {
        ArrayList<Room> pairRooms = new ArrayList<>();
        for(Room room : rooms){
            if(Objects.equals(room.getType(), "Двомістний") && room.isStatus()){
                pairRooms.add(room);
            }
        }
        return pairRooms;
    }

    public static ArrayList<Room> getFreeMultiRooms() {
        ArrayList<Room> multiRooms = new ArrayList<>();
        for(Room room : rooms){
            if(Objects.equals(room.getType(), "Багатовмістний") && room.isStatus()){
                multiRooms.add(room);
            }
        }
        return multiRooms;
    }


    public static ArrayList<Room> getFreeRooms() {
        ArrayList<Room> freeRooms = new ArrayList<>();
        for(Room room : rooms){
            if(room.isStatus()){
                freeRooms.add(room);
            }
        }
        return freeRooms;
    }


    public static ArrayList<Reservation> getReservations() {
        return reservations;
    }

    private static void updateReservationStatus() {
        getReservationsFromDB();
        for (Reservation reservation : reservations) {
            System.out.println("ЭТО РЕЗУЛЬТАТ ПРОВЕРКИ: " + reservation.getDepartureDate().isBefore(LocalDate.now()));
            if (reservation.getDepartureDate().isBefore(LocalDate.now())) {
                reservation.setStatus(false);
                DatabaseHandler.UpdateStatusReservationToFalseDB(DatabaseHandler.getClientIDFromDB(reservation.getClient()));
                System.out.println("ЭТО РЕЗУЛЬТАТ ПРОВЕРКИ: " + reservation.getDepartureDate().isBefore(LocalDate.now()));
                System.out.println("ЭТО КЛИЕНТ ID: " +DatabaseHandler.getClientIDFromDB(reservation.getClient()));
                System.out.println("ЭТО СТАТУС: " + reservation.isStatus());
            }
        }
    }

    public static void main(String[] args) {
        updateReservationStatus();
        getClientsFromDB();
        getRoomsFromDB();
        launch(args);
    }

}