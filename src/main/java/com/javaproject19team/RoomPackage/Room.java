/*
Room:
Цей клас призначений для номерів готелю.
Він містить інформацію про номер, таку як номер кімнати, тип (одномісний, двомісний тощо),
ціна і статус (вільний чи зайнятий).
*/
package com.javaproject19team.RoomPackage;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/**
 * Room:
 * Цей клас призначений для номерів готелю.
 * Він містить інформацію про номер, таку як номер кімнати, тип (одномісний, двомісний тощо),
 * ціна і статус (вільний чи зайнятий).
 */
public class Room {
    private final StringProperty number; // Номер кімнати
    private final StringProperty type; // Тип кімнати (одномісна, двомісна, багатомісна)
    private final IntegerProperty price; // Ціна за ніч
    private final StringProperty details; // Додаткові деталі про кімнату
    private boolean status; // True - Вільний / False - Зайнятий

    /**
     * Конструктор для створення об'єкта Room з вказаними параметрами.
     *
     * @param number  Номер кімнати
     * @param type    Тип кімнати
     * @param price   Ціна за ніч
     * @param details Додаткові деталі про кімнату
     * @param status  Статус кімнати (вільний або зайнятий)
     */
    public Room(StringProperty number, StringProperty type, IntegerProperty price, StringProperty details, boolean status) {
        this.number = number;
        this.type = type;
        this.price = price;
        this.details = details;
        this.status = status;
    }
    // Геттери та сеттери

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getNumber() {
        return number.get();
    }

    public StringProperty numberProperty() {
        return number;
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public int getPrice() {
        return price.get();
    }

    public IntegerProperty priceProperty() {
        return price;
    }

    public String getDetails() {
        return details.get();
    }

    public StringProperty detailsProperty() {
        return details;
    }


    /**
     * Перевизначений метод toString для представлення об'єкта Room у вигляді рядка.
     *
     * @return рядок, що представляє інформацію про кімнату
     */

    @Override
    public String toString() {
        return "Номер "
                + number.get() +
                ", " + type.get() +
                ", " + price.get() +
                ", " + details.get();
    }
}
