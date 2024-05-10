package com.example.javaproject19team.СlientPackage;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Client {
    private final StringProperty name;
    private final StringProperty surname;
    private final StringProperty email;
    private final StringProperty phone;

    public Client(StringProperty name, StringProperty surname, StringProperty email, StringProperty phone) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
    }


    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSurname() {
        return surname.get();
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    @Override
    public String toString() {
        return "Клієнт " +
                name.get() + " " +
                surname.get() +
                ", " + email.get() +
                ", " + phone.get();
    }

}
