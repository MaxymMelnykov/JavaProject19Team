module com.example.javaproject19team {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.sql;

    opens com.javaproject19team to javafx.fxml;
    exports com.javaproject19team;
    exports com.javaproject19team.СlientPackage;
    opens com.javaproject19team.СlientPackage to javafx.fxml;
    exports com.javaproject19team.DatabasePackage;
    opens com.javaproject19team.DatabasePackage to javafx.fxml;
    exports com.javaproject19team.ReservationPackage;
    opens com.javaproject19team.ReservationPackage to javafx.fxml;
    exports com.javaproject19team.RoomPackage;
    opens com.javaproject19team.RoomPackage to javafx.fxml;
}