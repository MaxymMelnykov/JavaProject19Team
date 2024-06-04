package com.javaproject19team.СlientPackage;
/**
 * ClientListener:
 * Інтерфейс для клієнтів
 */
public interface ClientListener {
    /**
     * Метод, який буде викликаний, коли клієнт збережений.
     *
     * @param client Об'єкт клієнта, який було збережено
     */
public interface ClientListener {
    // Метод, який буде викликаний, коли клієнт збережений
    void onClientSaved(Client client);
}
