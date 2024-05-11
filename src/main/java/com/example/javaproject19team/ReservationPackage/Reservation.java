package com.example.javaproject19team.ReservationPackage;

import com.example.javaproject19team.DatabasePackage.DatabaseHandler;
import com.example.javaproject19team.RoomPackage.Room;
import com.example.javaproject19team.СlientPackage.Client;

import java.time.LocalDate;

public class Reservation {
    private Client client;
    private Room room;
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private boolean status; //Убрать?
    //TODO сделать так чтоб статус менялся когда проходило время сьема номера
    public Reservation(Client client, Room room, LocalDate arrivalDate, LocalDate departureDate, boolean status) {
        this.client = client;
        this.room = room;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.status = status; // True - Активна / False - Не активна
        System.out.println(status);
        switch (room.getType()){
            case "Одномістний":
                DatabaseHandler.UpdateStatusRoomToFalseDB(room.getNumber());
                room.setStatus(false);
            case "Двомістний":
                if((DatabaseHandler.countRoomsReservations(DatabaseHandler.getRoomIDFromDB(room)) == 2)) {
                    DatabaseHandler.UpdateStatusRoomToFalseDB(room.getNumber());
                    room.setStatus(false);
                }
                    break;
            case "Багатовмістний":
                if((DatabaseHandler.countRoomsReservations(DatabaseHandler.getRoomIDFromDB(room)) == 5)) {
                    DatabaseHandler.UpdateStatusRoomToFalseDB(room.getNumber());
                    room.setStatus(false);
                }
                    break;
            default:
                room.setStatus(true);
        }
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }


    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }


    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


}