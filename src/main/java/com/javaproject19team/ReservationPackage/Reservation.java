/*
Reservation:
Клас, що призначений для резервацій номеру.
Він містить інформацію про клієнта, який зробив резервацію, номер кімнати, дати заїзду та виїзду,
а також статус резервації.
*/
package com.javaproject19team.ReservationPackage;

import com.javaproject19team.DatabasePackage.DatabaseHandler;
import com.javaproject19team.RoomPackage.Room;
import com.javaproject19team.СlientPackage.Client;

import java.time.LocalDate;

/**
 * Reservation:
 * Клас, що призначений для резервацій номеру.
 * Він містить інформацію про клієнта, який зробив резервацію, номер кімнати, дати заїзду та виїзду,
 * а також статус резервації.
 */
public class Reservation {
    private Client client;
    private Room room;
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private boolean status;

    /**
     * Конструктор, який ініціалізує об'єкт резервації з вказаними параметрами.
     * @param client клієнт, що зробив резервацію
     * @param room кімната, яку заброньовано
     * @param arrivalDate дата прибуття
     * @param departureDate дата виїзду
     * @param status статус резервації
     */
    public Reservation(Client client, Room room, LocalDate arrivalDate, LocalDate departureDate, boolean status) {
        this.client = client; // Клієнт, що робить резервацію
        this.room = room; // Кімната, яку заброньовано
        this.arrivalDate = arrivalDate; // Дата прибуття
        this.departureDate = departureDate; // Дата виїзду
        this.status = status; // True - Активна / False - Не активна
        /*
        Конструктор, який ініціалізує об'єкт резервації з вказаними параметрами.
        Такий конструктор потрібен для того, щоб змінювати статус номеру, коли в неї додається людина
        Наприклад: В одномістній кімнаті може жити лише одна людина, тому при створенні резервації, ми змінюємо статус кімнати
         */
        switch (room.getType()) {
            case "Одномістний":
                DatabaseHandler.UpdateStatusRoomToFalseDB(room.getNumber());  // Оновлюємо статус кімнати у базі даних на false (неактивний)
                room.setStatus(false);  // Оновлюємо статус кімнати в об'єкті кімнати
            case "Двомістний":
                // Перевіряємо, чи кількість резервацій для даної кімнати досягла максимальної кількості
                if ((DatabaseHandler.countRoomsReservations(DatabaseHandler.getRoomIDFromDB(room)) == 2)) {
                    DatabaseHandler.UpdateStatusRoomToFalseDB(room.getNumber());
                    room.setStatus(false);
                }
                break;
            case "Багатовмістний":
                // Перевіряємо, чи кількість резервацій для даної кімнати досягла максимальної кількості
                if ((DatabaseHandler.countRoomsReservations(DatabaseHandler.getRoomIDFromDB(room)) == 5)) {
                    DatabaseHandler.UpdateStatusRoomToFalseDB(room.getNumber());
                    room.setStatus(false);
                }
                break;
            default:
                room.setStatus(true);  // Якщо тип кімнати не відповідає одному з варіантів, встановлюємо статус кімнати на true (активний)
        }
    }
    // Геттери та сеттери
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
