// Карпенко А.М., Мельников М.С., Кучер Д.С., Сокульський П.А.,Стебловцев В.Є.
// ООП на мові Java
// Проєкт на тему "Візуалізація графічного інтерфейсу Резервацій в готелю"
// 7 червня 2024
// Години : 150 годин
// Ми визнаємо, що це наша командна робота

/*
Дана програма призначена для управління резерваціями готелю. Основні функціональні можливості програми включають:
    Додавання, редагування та перегляд інформації про клієнтів.
    Додавання, редагування та перегляд інформації про номери.
    Додавання, редагування та перегляд інформації про резервації.
Інструкції з використання
Запуск програми
	Запустіть програму, запустивши клас HotelReservationApp.
	Головний екран програми містить кнопки для переходу до списків клієнтів, номерів та резервацій.
Робота з клієнтами
	Перейдіть до списку клієнтів, натиснувши кнопку "Клієнти".
	У вікні списку клієнтів можна додавати нових клієнтів, натиснувши кнопку "Додати", та заповнивши форму.
	Для оновлення списку клієнтів натисніть кнопку "Оновити".
	Можна фільтрувати клієнтів за введеним текстом у полі пошуку.
Робота з номерами
	Перейдіть до списку номерів, натиснувши кнопку "Номери".
	У вікні списку номерів можна додавати нові номери, натиснувши кнопку "Додати", та заповнивши форму.
	Для оновлення списку номерів натисніть кнопку "Оновити".
	Можна фільтрувати номери за введеним текстом у полі пошуку.
Робота з резерваціями
	Перейдіть до списку резервацій, натиснувши кнопку "Резервації".
	У вікні списку резервацій можна додавати нові резервації, натиснувши кнопку "Додати", та заповнивши форму.
	Для оновлення списку резервацій натисніть кнопку "Оновити".
	Можна фільтрувати резервації за введеним текстом у полі пошуку.
*/

/*
HotelReservationApp:
Цей клас призначений за головний вхід в програму, управління головним екраном
та ініціалізацію інших частин програми, таких як клієнти, номери та резервації.
*/
package com.javaproject19team;

import com.javaproject19team.DatabasePackage.DatabaseHandler;
import com.javaproject19team.ReservationPackage.Reservation;
import com.javaproject19team.ReservationPackage.ReservationList;
import com.javaproject19team.ReservationPackage.ReservationListener;
import com.javaproject19team.RoomPackage.Room;
import com.javaproject19team.RoomPackage.RoomList;
import com.javaproject19team.RoomPackage.RoomListener;
import com.javaproject19team.СlientPackage.Client;
import com.javaproject19team.СlientPackage.ClientList;
import com.javaproject19team.СlientPackage.ClientListener;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Клас HotelReservationApp відповідає за головний вхід в програму, управління головним екраном та ініціалізацію
 * інших частин програми, таких як клієнти, номери та резервації.
 */
public class HotelReservationApp extends Application implements ClientListener, RoomListener, ReservationListener {
    static ArrayList<Client> clients = new ArrayList<>();
    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Reservation> reservations = new ArrayList<>();
    public static Stage primaryStage;

    /**
     * Метод start викликається при запуску програми та ініціалізує головний екран програми.
     * @param primaryStage Головний Stage програми.
     */
    @Override
    public void start(Stage primaryStage) {
        // Головне вікно програми
        HotelReservationApp.primaryStage = primaryStage;
        HotelReservationApp.primaryStage.getIcons().add(new Image("file:src/main/resources/icon.png"));

        // Заголовок програми
        primaryStage.setTitle("Головний екран");

        // Реалізація кнопки "Резервації"
        Button reservationsButton = new Button("Резервації");
        reservationsButton.setMinWidth(160);
        reservationsButton.setMinHeight(50);
        reservationsButton.setId("menu-button");
        reservationsButton.setFocusTraversable(false);
        reservationsButton.setStyle("-fx-font-size: 24px;");
        reservationsButton.setOnAction(e -> showReservations());

        // Реалізація кнопки "Клієнти"
        Button clientsButton = new Button("Клієнти");
        clientsButton.setMinWidth(160);
        clientsButton.setMinHeight(50);
        clientsButton.setFocusTraversable(false);
        clientsButton.setStyle("-fx-font-size: 24px;");
        clientsButton.setId("menu-button");
        clientsButton.setOnAction(e -> showClients());

        // Реалізація кнопки "Номери"
        Button roomsButton = new Button("Номери");
        roomsButton.setMinWidth(160);
        roomsButton.setMinHeight(50);
        roomsButton.setFocusTraversable(false);
        roomsButton.setStyle("-fx-font-size: 24px;");
        roomsButton.setId("menu-button");
        roomsButton.setOnAction(e -> showRooms());

        // HBox контейнер для розміщення кнопок
        HBox buttonHBox = new HBox(clientsButton, reservationsButton, roomsButton);
        buttonHBox.setAlignment(Pos.CENTER);

        // Відображення кількості зайнятих та доступних номерів
        Label reservedRoomsLabel = new Label("Кількість зайнятих номерів : " + (rooms.size() - getFreeRooms().size()));
        Label unreservedRoomsLabel = new Label("Кількість доступних номерів : " + getFreeRooms().size());

        // VBox контейнер для відображення інформації про кількість номерів
        VBox roominfoVBox = new VBox(reservedRoomsLabel, unreservedRoomsLabel);
        roominfoVBox.setAlignment(Pos.CENTER);
        roominfoVBox.setSpacing(10);

        // VBox контейнер, що містить кнопки та інформацію про кількість номерів
        VBox root = new VBox(buttonHBox, roominfoVBox);
        root.setSpacing(10);

        // Scene для головного вікна програми
        Scene scene = new Scene(root, 480, 120);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Метод для відображення вікна з резерваціями.
     * Створює новий об'єкт ReservationList та ініціалізує його для відображення списку резервацій.
     */
    private void showReservations() {
        Stage reservationStage = new Stage();
        ReservationList reservationList = new ReservationList();
        reservationList.setReservationListener(this);
        ReservationList.setHotelReservationStage(primaryStage);
        try {
            reservationList.start(reservationStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод для відображення вікна з клієнтами.
     * Створює новий об'єкт ClientList та ініціалізує його для відображення списку клієнтів.
     */
    private void showClients() {
        Stage clientsStage = new Stage();
        ClientList clientList = new ClientList();
        clientList.setClientListener(this);
        ClientList.setHotelReservationStage(primaryStage); // Надаємо посилання на primaryStage HotelReservationApp
        try {
            clientList.start(clientsStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод для відображення вікна з номерами.
     * Створює новий об'єкт RoomList та ініціалізує його для відображення списку номерів.
     */
    private void showRooms() {
        Stage roomStage = new Stage();
        RoomList roomList = new RoomList();
        roomList.setRoomListener(this);
        RoomList.setHotelReservationStage(primaryStage);
        try {
            roomList.start(roomStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Обробник події для збереження клієнта.
     * Додає нового клієнта до списку клієнтів.
     * @param client Новий клієнт.
     */
    @Override
    public void onClientSaved(Client client) {
        clients.add(client);
    }

    /**
     * Обробник події для збереження номера.
     * Додає новий номер до списку номерів.
     * @param room Новий номер.
     */
    @Override
    public void onRoomSaved(Room room) {
        rooms.add(room);
    }

    /**
     * Обробник події для збереження резервації.
     * Додає нову резервацію до списку резервацій.
     * @param reservation Нова резервація.
     */
    @Override
    public void onReservationSaved(Reservation reservation) {
        reservations.add(reservation);
    }

    /**
     * Метод для отримання списку клієнтів з бази даних та їх додавання в локальний список.
     */
    public static void getClientsFromDB() {
        int countClientsInDB = DatabaseHandler.countClientsFromDB();
        for (int i = 0; i < countClientsInDB; i++) {
            try {
                clients.add(i, DatabaseHandler.getClientFromDB(i + 1));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Метод для отримання списку номерів з бази даних та їх додавання в локальний список.
     */
    public static void getRoomsFromDB() {
        int countRoomsInDB = DatabaseHandler.countRoomsFromDB();
        for (int i = 0; i < countRoomsInDB; i++) {
            try {
                rooms.add(i, DatabaseHandler.getRoomFromDB(i + 1));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Метод для отримання списку резервацій з бази даних та їх додавання в локальний список.
     */
    public static void getReservationsFromDB() {
        int countReservaionsInDB = DatabaseHandler.countReservationsFromDB();
        for (int i = 0; i < countReservaionsInDB; i++) {
            try {
                reservations.add(i, DatabaseHandler.getReservationFromDB(i + 1));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Метод для отримання списку клієнтів.
     * @return Список клієнтів.
     */
    public static ArrayList<Client> getClients() {
        return clients;
    }

    /**
     * Метод для отримання списку номерів.
     * @return Список номерів.
     */
    public static ArrayList<Room> getRooms() {
        return rooms;
    }

    /**
     * Метод для отримання списку вільних одномісних номерів.
     * @return Список вільних одномісних номерів.
     */
    public static ArrayList<Room> getFreeSingleRooms() {
        ArrayList<Room> singleRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (Objects.equals(room.getType(), "Одномістний") && room.isStatus()) {
                singleRooms.add(room);
            }
        }
        return singleRooms;
    }

    /**
     * Метод для отримання списку вільних двомісних номерів.
     * @return Список вільних двомісних номерів.
     */
    public static ArrayList<Room> getFreePairRooms() {
        ArrayList<Room> pairRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (Objects.equals(room.getType(), "Двомістний") && room.isStatus()) {
                pairRooms.add(room);
            }
        }
        return pairRooms;
    }

    /**
     * Метод для отримання списку вільних багатомісних номерів.
     * @return Список вільних багатомісних номерів.
     */
    public static ArrayList<Room> getFreeMultiRooms() {
        ArrayList<Room> multiRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (Objects.equals(room.getType(), "Багатовмістний") && room.isStatus()) {
                multiRooms.add(room);
            }
        }
        return multiRooms;
    }

    /**
     * Метод для отримання списку вільних номерів.
     * @return Список вільних номерів.
     */
    public static ArrayList<Room> getFreeRooms() {
        ArrayList<Room> freeRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isStatus()) {
                freeRooms.add(room);
            }
        }
        return freeRooms;
    }

    /**
     * Метод для отримання списку резервацій.
     * @return Список резервацій.
     */
    public static ArrayList<Reservation> getReservations() {
        return reservations;
    }

    /**
     * Метод для оновлення статусу резервацій.
     * Позначає резервації як недійсні, якщо дата виїзду вже минула.
     */
    private static void updateReservationStatus() {
        getReservationsFromDB();
        for (Reservation reservation : reservations) {
            if (reservation.getDepartureDate().isBefore(LocalDate.now())) {
                reservation.setStatus(false);
                DatabaseHandler.UpdateStatusReservationToFalseDB(DatabaseHandler.getClientIDFromDB(reservation.getClient()));
            }
        }
    }

    // Точка входу у програму
    public static void main(String[] args)
    {
        updateReservationStatus();
        getClientsFromDB();
        getRoomsFromDB();
        launch(args);
    }
}
