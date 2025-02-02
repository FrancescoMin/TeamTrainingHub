package viste.first.utils;

import engineering.eccezioni.EccezioneCambioScena;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class CambioScena {
    public void cambioScena(Stage stage, String string) throws EccezioneCambioScena {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(string)));
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new EccezioneCambioScena(e.getMessage());
        }
    }
}
