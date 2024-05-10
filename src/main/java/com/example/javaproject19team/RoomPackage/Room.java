package com.example.javaproject19team.RoomPackage;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Room {
    private final StringProperty number;
    private final StringProperty type;
    private final IntegerProperty price;
    private final StringProperty details;

    //Добавить занята/не занята

    public Room(StringProperty number, StringProperty type, IntegerProperty price, StringProperty details) {
        this.number = number;
        this.type = type;
        this.price = price;
        this.details = details;
    }

    public String getNumber() {
        return number.get();
    }

    public StringProperty numberProperty() {
        return number;
    }

    public void setNumber(String number) {
        this.number.set(number);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public int getPrice() {
        return price.get();
    }

    public IntegerProperty priceProperty() {
        return price;
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public String getDetails() {
        return details.get();
    }

    public StringProperty detailsProperty() {
        return details;
    }

    @Override
    public String toString() {
        return "Номер "
                + number.get() +
                ", " + type.get() +
                ", " + price.get() +
                ", " + details.get();
    }

    public void setDetails(String details) {
        this.details.set(details);
    }

}



