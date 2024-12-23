package viste.first;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HomepageGiocatoreCtrlGrafico {

    @FXML
    private Button EntraInSquadraButton;

    @FXML
    private Button consultaAllenamentiButton;

    @FXML
    private Text ciaoText;

    @FXML
    private Label welcomeLabel;

    private String username;

    public void initialize() {
        // Imposta il messaggio di benvenuto
        welcomeLabel.setText("Ciao " + username);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @FXML
    private void handleEntraInSquadraButtonAction() {
        try {
            // Carica il file FXML della finestra di dialogo
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("entraInSquadra.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            // Crea una nuova finestra di dialogo
            Stage stage = new Stage();
            stage.setTitle("Inserisci Squadra");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleConsultaAllenamentiButtonAction() {
        // Logica per il pulsante "Consulta allenamenti"
        System.out.println("Consulta allenamenti cliccato");
    }
}