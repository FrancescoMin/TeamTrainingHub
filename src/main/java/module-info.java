module com.example.teamtraininghub {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.teamtraininghub to javafx.fxml;
    exports com.example.teamtraininghub;
}