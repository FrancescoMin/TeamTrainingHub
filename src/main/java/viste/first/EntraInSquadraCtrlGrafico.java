package viste.first;

import ctrl_applicativo.EntraInSquadraCtrlApplicativo;
import engineering.eccezioni.EccezioneSquadraInvalida;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import viste.first.basi.BaseHomePageCtrlGrafico;

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
                EntraInSquadraCtrlApplicativo entrainsquadractrlapplicativo = new EntraInSquadraCtrlApplicativo();

                if(entrainsquadractrlapplicativo.verificaEsistenzaSquadra(nomeSquadra)) {

                    entrainsquadractrlapplicativo.inviaRichiestaAllaSquadra(nomeSquadra);
                    // Show an alert to indicate refusal
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Invio Richiesta");
                    alert.setHeaderText(null);
                    alert.setContentText("Richiesta di partecipazione inviata alla squadra con successo");
                    alert.showAndWait();

                    //torno al login
                    BaseHomePageCtrlGrafico baseHomePageCtrlGrafico = new BaseHomePageCtrlGrafico();
                    baseHomePageCtrlGrafico.tornaAlLogin();
                }
                else {
                    throw new EccezioneSquadraInvalida("Squadra inserita non esiste");
                }

            } catch (EccezioneSquadraInvalida e) {
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
        //inizializzo la pagina
    }

}