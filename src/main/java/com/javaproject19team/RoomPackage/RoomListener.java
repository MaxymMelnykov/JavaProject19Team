/*
ReservationListener:
Інтерфейс для номерів кімнат
*/
package com.javaproject19team.RoomPackage;

public interface RoomListener {
    // Метод, який буде викликаний, коли кімната збережена
    void onRoomSaved(Room room);
}
