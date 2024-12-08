package viste.first.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CambioScena {
    public void cambioScena(Stage stage, String string) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource(string));
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
