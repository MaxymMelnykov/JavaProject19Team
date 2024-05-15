/*
ReservationListener:
Інтерфейс для клієнтів
*/
package com.javaproject19team.СlientPackage;

public interface ClientListener {
    // Метод, який буде викликаний, коли клієнт збережений
    void onClientSaved(Client client);
}
