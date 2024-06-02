/*
ReservationListener:
Інтерфейс для номерів кімнат
*/
package com.javaproject19team.RoomPackage;

/**
 * ReservationListener:
 * Інтерфейс для номерів кімнат
 */
public interface RoomListener {
    /**
     * Метод, який буде викликаний, коли кімната збережена
     *
     * @param room Збережена кімната
     */
    void onRoomSaved(Room room);
}
