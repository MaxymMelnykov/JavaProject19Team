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
    private boolean status;

    public Reservation(Client client, Room room, LocalDate arrivalDate, LocalDate departureDate, boolean status) {
        this.client = client;
        this.room = room;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.status = status; // True - Активна / False - Не активна
        /*
        Такий конструктор потрібен для того, щоб змінювати статус номеру, коли в неї додається людина
        Наприклад: В одномістній кімнаті може жити лише одна людина, тому при створенні резервації, ми змінюємо статус кімнати
         */
        switch (room.getType()) {
            case "Одномістний":
                DatabaseHandler.UpdateStatusRoomToFalseDB(room.getNumber());
                room.setStatus(false);
            case "Двомістний":
                if ((DatabaseHandler.countRoomsReservations(DatabaseHandler.getRoomIDFromDB(room)) == 2)) {
                    DatabaseHandler.UpdateStatusRoomToFalseDB(room.getNumber());
                    room.setStatus(false);
                }
                break;
            case "Багатовмістний":
                if ((DatabaseHandler.countRoomsReservations(DatabaseHandler.getRoomIDFromDB(room)) == 5)) {
                    DatabaseHandler.UpdateStatusRoomToFalseDB(room.getNumber());
                    room.setStatus(false);
                }
                break;
            default:
                room.setStatus(true);
        }
    }


    public Client getClient() {
        return client;
    }

    public Room getRoom() {
        return room;
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