package viste.first;

import ctrlApplicativo.EntraInSquadraCtrlApplicativo;
import engineering.eccezioni.EccezioneGenerica;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import viste.first.utils.CambioScena;

import java.net.URL;
import java.util.ResourceBundle;

import static viste.first.utils.FxmlFileName.PAGINA_HOME_GIOCATORE;

public class EntraInSquadraCtrlGrafico implements Initializable {

    @FXML
    private TextField scriviSquadraTextField;

    @FXML
    private Button richiediIngressoButton;
    @FXML
    private Button tornaInHomepageGiocatoreButton;

    private EntraInSquadraCtrlApplicativo applicativoController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        applicativoController = new EntraInSquadraCtrlApplicativo();
        richiediIngressoButton.setOnAction(event -> handleRichiediIngressoButtonAction());
        tornaInHomepageGiocatoreButton.setOnAction(event -> handleTornaInHomepageGiocatoreButtonAction());
    }

    private void handleRichiediIngressoButtonAction() {
        String nomeSquadra = scriviSquadraTextField.getText();

        if (nomeSquadra != null && !nomeSquadra.trim().isEmpty()) {
            try {
                // Invio della richiesta alla squadra tramite il controller applicativo
                applicativoController.inviaRichiestaAllaSquadra(nomeSquadra);
                mostraMessaggio("Successo", "Richiesta inviata con successo all'allenatore della squadra.");
            } catch (EccezioneGenerica e) {
                mostraMessaggio("Errore", e.getMessage());
            }
        } else {
            mostraMessaggio("Errore", "Il campo nome della squadra non può essere vuoto.");
        }
    }

    private void mostraMessaggio(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }

    private void handleTornaInHomepageGiocatoreButtonAction() {
        try {
            Stage stage = (Stage) tornaInHomepageGiocatoreButton.getScene().getWindow();
            CambioScena cambioScena = new CambioScena();
            cambioScena.cambioScena(stage, PAGINA_HOME_GIOCATORE);
        } catch (EccezioneGenerica eccezioneGenerica) {
            System.out.println(eccezioneGenerica.getMessage());
        }
    }
}