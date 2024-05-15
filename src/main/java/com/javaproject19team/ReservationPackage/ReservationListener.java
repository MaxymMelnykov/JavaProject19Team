/*
ReservationListener:
Інтерфейс для резервації
*/
package com.javaproject19team.ReservationPackage;

public interface ReservationListener {
    // Метод, який буде викликаний, коли резервація збережена
    void onReservationSaved(Reservation reservation);
}
