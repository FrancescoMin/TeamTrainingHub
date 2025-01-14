module com.example.teamtraininghub {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.sql;
    requires java.desktop;
    requires com.fasterxml.jackson.databind;
    requires org.junit.jupiter.api;

    opens modelli to javafx.base, com.fasterxml.jackson.databind, com.google.gson;

    exports inizio to javafx.graphics;
    opens inizio to javafx.fxml;
    exports viste.first to javafx.graphics;
    opens viste.first to javafx.fxml;
    opens engineering.bean to com.google.gson;

}
    