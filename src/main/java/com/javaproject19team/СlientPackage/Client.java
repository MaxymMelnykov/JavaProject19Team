/*
Client:
Клас, який призначений для клієнтів готелю.
Він містить основну інформацію про клієнта, таку як ім'я, прізвище, електронну пошту та номер телефону.
*/
package com.javaproject19team.СlientPackage;

import javafx.beans.property.StringProperty;

/**
 * Client:
 * Клас, який призначений для клієнтів готелю.
 * Він містить основну інформацію про клієнта, таку як ім'я, прізвище, електронну пошту та номер телефону.
 */
public class Client {
    private final StringProperty name; // Ім'я
    private final StringProperty surname; // Прізвище
    private final StringProperty email; // Електронна пошта клієнта
    private final StringProperty phone; // Телефон клієнта

    /**
     * Конструктор класу Client.
     *
     * @param name    Ім'я клієнта
     * @param surname Прізвище клієнта
     * @param email   Електронна пошта клієнта
     * @param phone   Телефон клієнта
     */
    public Client(StringProperty name, StringProperty surname, StringProperty email, StringProperty phone) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
    }

    // Геттери та сеттери
    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getSurname() {
        return surname.get();
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }


    /**
     * Перевизначений метод toString.
     *
     * @return Рядок, який містить ім'я, прізвище, електронну пошту та номер телефону клієнта
     */

    @Override
    public String toString() {
        return "Клієнт " +
                name.get() + " " +
                surname.get() +
                ", " + email.get() +
                ", " + phone.get();
    }
}
