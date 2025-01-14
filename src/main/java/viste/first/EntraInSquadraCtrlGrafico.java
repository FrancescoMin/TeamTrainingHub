package viste.first;

import ctrlApplicativo.EntraInSquadraCtrlApplicativo;
import engineering.eccezioni.EccezioneGenerica;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private Label mostraErrore;

    @FXML
    private Button richiediIngressoButton;
    @FXML
    private Button tornaInHomepageGiocatoreButton;

    private EntraInSquadraCtrlApplicativo applicativoController;

    @FXML
    protected void richiediIngresso() {
        //prendiamo dalla text field il nome della squadra
        String nomeSquadra = scriviSquadraTextField.getText();

        //se il nome della squadra non è vuoto
        if (nomeSquadra != null && !nomeSquadra.trim().isEmpty()) {
            try {
                // Invio della richiesta alla squadra tramite il controller applicativo
                EntraInSquadraCtrlApplicativo entraInSquadraCtrlApplicativo = new EntraInSquadraCtrlApplicativo();

                if(entraInSquadraCtrlApplicativo.verificaEsistenzaSquadra(nomeSquadra)) {

                    entraInSquadraCtrlApplicativo.inviaRichiestaAllaSquadra(nomeSquadra);
                    System.out.println("Richiesta inviata");
                }
                else {
                    throw new EccezioneGenerica("Squadra inserita non esiste");
                }

            } catch (EccezioneGenerica e) {
                mostraErrore.setText(e.getMessage());
                mostraErrore.setVisible(true);
            }
        } else {
            mostraErrore.setText("Squadra inserita non valida");
            mostraErrore.setVisible(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*
        applicativoController = new EntraInSquadraCtrlApplicativo();
        richiediIngressoButton.setOnAction(event -> handleRichiediIngressoButtonAction());

         */
        tornaInHomepageGiocatoreButton.setOnAction(event -> handleTornaInHomepageGiocatoreButtonAction());
    }

    /*
    private void mostraMessaggio(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
     */

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