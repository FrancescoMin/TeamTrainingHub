module com.example.teamtraininghub {
    requires javafx.controls;
    requires javafx.fxml;
    exports inizio to javafx.graphics;
    opens inizio to javafx.fxml;
}

