package viste.first;

import controllerApplicativo.entraInSquadraCtrlApplicativo;
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
import  static viste.first.utils.FxmlFileName.PAGINA_HOME_GIOCATORE;

public class entraInSquadraCtrlGrafico implements Initializable {

    @FXML
    private TextField scriviSquadraTextField;

    @FXML
    private Button richiediIngressoButton;
    @FXML
    private Button tornaInHomepageGiocatoreButton;

    private entraInSquadraCtrlApplicativo applicativoController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        applicativoController = new entraInSquadraCtrlApplicativo();
        richiediIngressoButton.setOnAction(event -> handleRichiediIngressoButtonAction());
        tornaInHomepageGiocatoreButton.setOnAction(event -> handleTornaInHomepageGiocatoreButtonAction());
    }

    private void handleRichiediIngressoButtonAction() {
        String nomeSquadra = scriviSquadraTextField.getText();

        if (nomeSquadra != null && !nomeSquadra.trim().isEmpty()) {
            try {
                // Verifica tramite il controller applicativo
                boolean esiste = applicativoController.verificaEsistenzaSquadra(nomeSquadra);

                if (esiste) {
                    mostraMessaggio("Successo", "La squadra esiste in persistenza.");
                } else {
                    mostraMessaggio("Errore", "La squadra non è presente in persistenza.");
                }
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