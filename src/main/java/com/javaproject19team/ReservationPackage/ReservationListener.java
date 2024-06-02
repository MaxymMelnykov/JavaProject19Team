/*
ReservationListener:
Інтерфейс для резервації
*/
package com.javaproject19team.ReservationPackage;

/**
 * Інтерфейс ReservationListener для обробки подій, пов'язаних з резерваціями.
 */
public interface ReservationListener {
    /**
     * Метод, який буде викликаний, коли резервація збережена.
     *
     * @param reservation об'єкт резервації, яка була збережена
     */
    void onReservationSaved(Reservation reservation);
}
