/*TODO снести класс
package com.example.javaproject19team.ReservationPackage;


import com.example.javaproject19team.ReservationPackage.ReservationEditor;
import com.example.javaproject19team.ReservationPackage.ReservationListApp;
import com.example.javaproject19team.RoomPackage.RoomListener;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
public class ReservationWindow extends Application{
    private ReservationListener reservationListener;
    public void setReservationListener(ReservationListener reservationListener) {
        this.reservationListener = reservationListener;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Резервації");

        HBox buttonsBox = new HBox(); // Використовуємо HBox для розміщення кнопок в ряд
        buttonsBox.setSpacing(10); // Відступи між кнопками
        buttonsBox.setPadding(new Insets(10, 10, 10, 10));

        Button showReservationsButton = new Button("Переглянути список резервацій");
        showReservationsButton.setMinWidth(100); // Збільшуємо ширину кнопки
        showReservationsButton.setOnAction(e -> showReservations());

        Button addOrRemoveReservationsButton = new Button("Додати або змінити резервацію");
        addOrRemoveReservationsButton.setMinWidth(100); // Збільшуємо ширину кнопки
        addOrRemoveReservationsButton.setOnAction(e -> showAddOrRemoveReservaions());

        buttonsBox.getChildren().addAll(showReservationsButton, addOrRemoveReservationsButton);
        Scene scene = new Scene(buttonsBox, 400, 300); // Встановлюємо висоту на 100
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAddOrRemoveReservaions() {
        Stage showAddOrRemoveReservaions = new Stage();
        ReservationEditor reservationEditor = new ReservationEditor();
        reservationEditor.setReservationListener(reservationListener);
        try {
            reservationEditor.start(showAddOrRemoveReservaions);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Showing showRooms");
    }

    private void showReservations() {
        Stage showReservations = new Stage();
        ReservationListApp reservationListApp = new ReservationListApp();
        try {
            reservationListApp.start(showReservations);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Showing showRooms");
    }

    public static void main(String[] args) {
        launch(args);
    }
}

 */
