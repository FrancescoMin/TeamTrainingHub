package viste.first;

import ctrlApplicativo.EntraInSquadraCtrlApplicativo;
import engineering.eccezioni.EccezioneGenerica;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import viste.first.utils.CambioScena;

import java.net.URL;
import java.util.ResourceBundle;

import static viste.first.utils.FxmlFileName.PAGINA_HOME_GIOCATORE;

public class EntraInSquadraCtrlGrafico{

    @FXML
    private TextField scriviSquadraTextField;

    @FXML
    private Text scriviSquadraText;

    @FXML
    private Label mostraErrore;

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
                    // Show an alert to indicate refusal
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Invio Richiesta");
                    alert.setHeaderText(null);
                    alert.setContentText("Richiesta di partecipazione inviata alla squadra con successo");
                    alert.showAndWait();
                    tornaInHomepage();
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

    @FXML
    public void initialize() {
    }

    @FXML
    private void tornaInHomepage() {
        try {
            Stage stage = (Stage) scriviSquadraText.getScene().getWindow();
            CambioScena cambioScena = new CambioScena();
            cambioScena.cambioScena(stage, PAGINA_HOME_GIOCATORE);
        } catch (EccezioneGenerica eccezioneGenerica) {
            System.out.println(eccezioneGenerica.getMessage());
        }
    }

}