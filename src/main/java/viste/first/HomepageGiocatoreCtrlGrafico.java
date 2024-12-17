package viste.first;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

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
        // Logica per il pulsante "Entra in una squadra"
        System.out.println("Entra in una squadra cliccato");
    }

    @FXML
    private void handleConsultaAllenamentiButtonAction() {
        // Logica per il pulsante "Consulta allenamenti"
        System.out.println("Consulta allenamenti cliccato");
    }
}