package viste.first.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class CambioScena {
    public void cambioScena(Stage stage, String string) {
        try {

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(string)));
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
