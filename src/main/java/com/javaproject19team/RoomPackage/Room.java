/*
Room:
Цей клас призначений для номерів готелю.
Він містить інформацію про номер, таку як номер кімнати, тип (одномісний, двомісний тощо),
ціна і статус (вільний чи зайнятий).
*/
package com.javaproject19team.RoomPackage;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Room {
    private final StringProperty number; // Номер кімнати
    private final StringProperty type; // Тип кімнати (одномісна, двомісна, багатомісна)
    private final IntegerProperty price; // Ціна за ніч
    private final StringProperty details; // Додаткові деталі про кімнату
    private boolean status; // True - Вільний / False - Зайнятий

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

    // Перевизначений метод toString
    @Override
    public String toString() {
        return "Номер "
                + number.get() +
                ", " + type.get() +
                ", " + price.get() +
                ", " + details.get();
    }
}
