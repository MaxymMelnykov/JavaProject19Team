package com.example.javaproject19team;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Reservation {
    private final StringProperty guest;
    private final StringProperty date;
    private final StringProperty status;

    public Reservation(String guest, String date, String status) {
        this.guest = new SimpleStringProperty(guest);
        this.date = new SimpleStringProperty(date);
        this.status = new SimpleStringProperty(status);
    }

    public String getGuest() {
        return guest.get();
    }

    public StringProperty guestProperty() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest.set(guest);
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
}