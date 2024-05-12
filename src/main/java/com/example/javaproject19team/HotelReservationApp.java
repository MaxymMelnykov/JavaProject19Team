package com.example.javaproject19team;

import com.example.javaproject19team.DatabasePackage.DatabaseHandler;
import com.example.javaproject19team.ReservationPackage.Reservation;
import com.example.javaproject19team.ReservationPackage.ReservationList;
import com.example.javaproject19team.ReservationPackage.ReservationListener;
import com.example.javaproject19team.RoomPackage.Room;
import com.example.javaproject19team.RoomPackage.RoomList;
import com.example.javaproject19team.RoomPackage.RoomListener;
import com.example.javaproject19team.СlientPackage.Client;
import com.example.javaproject19team.СlientPackage.ClientList;
import com.example.javaproject19team.СlientPackage.ClientListener;
import javafx.application.Application;
import javafx.geometry.Pos;
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



        Button reservationsButton = new Button("Резервації");
        reservationsButton.setMinWidth(160);
        reservationsButton.setMinHeight(50);
        reservationsButton.setId("menu-button");
        reservationsButton.setFocusTraversable(false);
        reservationsButton.setStyle("-fx-font-size: 24px;");
        reservationsButton.setOnAction(e -> showReservations());

        Button clientsButton = new Button("Клієнти");
        clientsButton.setMinWidth(160);
        clientsButton.setMinHeight(50);
        clientsButton.setFocusTraversable(false);
        clientsButton.setStyle("-fx-font-size: 24px;");
        clientsButton.setId("menu-button");
        clientsButton.setOnAction(e -> showClients());

        Button roomsButton = new Button("Номери");
        roomsButton.setMinWidth(160);
        roomsButton.setMinHeight(50);
        roomsButton.setFocusTraversable(false);
        roomsButton.setStyle("-fx-font-size: 24px;");
        roomsButton.setId("menu-button");
        roomsButton.setOnAction(e -> showRooms());

        HBox buttonHBox = new HBox(clientsButton,reservationsButton,roomsButton);
        buttonHBox.setAlignment(Pos.CENTER);


        Label reservedRoomsLabel = new Label("Кількість зайнятих номерів : "  + (rooms.size() - getFreeRooms().size()));
        Label unreservedRoomsLabel = new Label("Кількість доступних номерів : " + getFreeRooms().size());

        VBox roominfoVBox = new VBox(reservedRoomsLabel,unreservedRoomsLabel);
        roominfoVBox.setAlignment(Pos.CENTER);
        roominfoVBox.setSpacing(10);

        VBox root = new VBox(buttonHBox,roominfoVBox);
        root.setSpacing(10);

        Scene scene = new Scene(root, 480, 120);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void showReservations() {
        Stage reservationStage = new Stage();
        ReservationList reservationList = new ReservationList();
        reservationList.setReservationListener(this);
        ReservationList.setHotelReservationStage(primaryStage);
        try {
            reservationList.start(reservationStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void showClients() {
        Stage clientsStage = new Stage();
        ClientList clientList = new ClientList();
        clientList.setClientListener(this);
        ClientList.setHotelReservationStage(primaryStage); // Передаем ссылку на primaryStage HotelReservationApp
        try {
            clientList.start(clientsStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void showRooms() {
        Stage roomStage = new Stage();
        RoomList roomList = new RoomList();
        roomList.setRoomListener(this);
        RoomList.setHotelReservationStage(primaryStage);
        try {
            roomList.start(roomStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClientSaved(Client client) {
        clients.add(client);
    }
    @Override
    public void onRoomSaved(Room room) {
        rooms.add(room);
    }
    public void onReservationSaved(Reservation reservation){
        reservations.add(reservation);
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
            if (reservation.getDepartureDate().isBefore(LocalDate.now())) {
                reservation.setStatus(false);
                DatabaseHandler.UpdateStatusReservationToFalseDB(DatabaseHandler.getClientIDFromDB(reservation.getClient()));
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