module com.example.teamtraininghub {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    exports inizio to javafx.graphics;
    opens inizio to javafx.fxml;
    exports viste.first to javafx.graphics;
    opens viste.first to javafx.fxml;
}

