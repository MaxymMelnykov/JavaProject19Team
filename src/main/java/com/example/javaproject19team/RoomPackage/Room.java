package com.example.javaproject19team.RoomPackage;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Room {
    private final StringProperty number;
    private final StringProperty type;
    private final IntegerProperty price;
    private final StringProperty details;

    private boolean status;

    public Room(StringProperty number, StringProperty type, IntegerProperty price, StringProperty details, boolean status) {
        this.number = number;
        this.type = type;
        this.price = price;
        this.details = details;
        this.status = status;
    }
//TODO сделать так чтоб статус менялся когда проходило время сьема номера
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



